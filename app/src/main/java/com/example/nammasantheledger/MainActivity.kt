package com.example.nammasantheledger

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nammasantheledger.ui.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel: MainViewModel = viewModel()
            val transactions by viewModel.transactions.collectAsState()
            val customers by viewModel.customers.collectAsState()
            val totalCredit by viewModel.totalCredit.collectAsState()
            val totalPayment by viewModel.totalPayment.collectAsState()
            val currentUser by viewModel.currentUser.collectAsState()

            var screen by remember { mutableStateOf("splash") }
            var isRegistering by remember { mutableStateOf(false) }
            
            var selectedCustomerName by remember { mutableStateOf("") }
            var selectedCustomerPhone by remember { mutableStateOf("") }
            var initialTransactionType by remember { mutableStateOf("Credit") }
            
            val scope = rememberCoroutineScope()

            LaunchedEffect(screen) {
                if (screen == "splash") {
                    delay(2000)
                    screen = "login"
                }
            }

            when (screen) {

                "splash" -> SplashScreen()

                "login" -> LoginScreen(
                    isRegistering = isRegistering,
                    onModeChange = { isRegistering = it },
                    onLogin = { user, pass ->
                        scope.launch {
                            when (viewModel.login(user, pass)) {
                                MainViewModel.LoginResult.Success -> {
                                    screen = "home"
                                }
                                MainViewModel.LoginResult.UserNotFound -> {
                                    Toast.makeText(this@MainActivity, "Account does not exist. Please Sign Up.", Toast.LENGTH_LONG).show()
                                }
                                MainViewModel.LoginResult.WrongPassword -> {
                                    Toast.makeText(this@MainActivity, "Incorrect password.", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    },
                    onRegister = { user ->
                        scope.launch {
                            if (viewModel.register(user)) {
                                Toast.makeText(this@MainActivity, "Registration Successful! Please Login.", Toast.LENGTH_LONG).show()
                                isRegistering = false // Return to login mode
                            } else {
                                Toast.makeText(this@MainActivity, "Registration Failed (Username may be taken)", Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    onForgotPassword = { user, newPass ->
                        scope.launch {
                            viewModel.updatePassword(user, newPass)
                            Toast.makeText(this@MainActivity, "Password Reset Successful", Toast.LENGTH_SHORT).show()
                        }
                    }
                )

                "home" -> HomeScreen(
                    totalCredit = totalCredit,
                    totalPayment = totalPayment,
                    onAdd = { 
                        selectedCustomerName = ""
                        selectedCustomerPhone = ""
                        initialTransactionType = "Credit"
                        screen = "add" 
                    },
                    onCustomers = { screen = "customers" },
                    onViewTransactions = { screen = "list" },
                    onSettings = { screen = "profile" }
                )

                "profile" -> ProfileScreen(
                    user = currentUser,
                    onBack = { screen = "home" },
                    onEdit = { screen = "edit_profile" },
                    onLogout = {
                        viewModel.logout()
                        screen = "login"
                    }
                )

                "edit_profile" -> {
                    currentUser?.let { user ->
                        EditProfileScreen(
                            user = user,
                            onSave = { updatedUser ->
                                scope.launch {
                                    viewModel.updateUser(updatedUser)
                                    screen = "profile"
                                }
                            },
                            onBack = { screen = "profile" }
                        )
                    }
                }

                "add" -> AddTransactionScreen(
                    initialName = selectedCustomerName,
                    initialPhone = selectedCustomerPhone,
                    initialType = initialTransactionType,
                    onSave = { transaction ->
                        viewModel.addTransaction(transaction)
                        screen = "home"
                    },
                    onBack = { screen = "home" }
                )

                "list" -> TransactionListScreen(
                    transactions = transactions,
                    onBack = { screen = "home" }
                )

                "customers" -> CustomerScreen(
                    customers = customers,
                    onBack = { screen = "home" },
                    onClick = { name ->
                        val customer = customers.find { it.name == name }
                        selectedCustomerName = name
                        selectedCustomerPhone = customer?.phone ?: ""
                        screen = "detail"
                    }
                )

                "detail" -> {
                    val customer = customers.find { it.name == selectedCustomerName }
                    val balance = customer?.balance ?: 0.0
                    CustomerDetailScreen(
                        name = selectedCustomerName,
                        phone = selectedCustomerPhone,
                        balance = balance,
                        onBack = { screen = "customers" },
                        onEdit = { screen = "edit" },
                        onUpdate = { 
                            initialTransactionType = "Payment"
                            screen = "add" 
                        },
                        onWhatsApp = {
                            val message = "Hi $selectedCustomerName, your balance is ₹$balance"
                            val url = "https://wa.me/$selectedCustomerPhone?text=${Uri.encode(message)}"
                            val intent = Intent(Intent.ACTION_VIEW)
                            intent.data = Uri.parse(url)
                            startActivity(intent)
                        }
                    )
                }

                "edit" -> {
                    EditCustomerScreen(
                        currentName = selectedCustomerName,
                        currentPhone = selectedCustomerPhone,
                        onSave = { newName, newPhone ->
                            viewModel.updateCustomerDetails(selectedCustomerPhone, newName, newPhone)
                            selectedCustomerName = newName
                            selectedCustomerPhone = newPhone
                            screen = "detail"
                        },
                        onBack = { screen = "detail" }
                    )
                }
            }
        }
    }
}

package com.example.nammasantheledger.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nammasantheledger.data.User

@Composable
fun LoginScreen(
    isRegistering: Boolean,
    onModeChange: (Boolean) -> Unit,
    onLogin: (String, String) -> Unit,
    onRegister: (User) -> Unit,
    onForgotPassword: (String, String) -> Unit
) {
    var isForgotPassword by remember { mutableStateOf(false) }

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }
    var tell by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var postalCode by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }

    AppBackground {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(vertical = 40.dp)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(bottom = 16.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 24.dp, vertical = 36.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = if (isForgotPassword) "Reset" else if (isRegistering) "Register" else "Login",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF9191FF)
                        )

                        OutlinedTextField(
                            value = username,
                            onValueChange = { username = it },
                            placeholder = { Text("Username") },
                            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(50.dp)
                        )

                        if (isRegistering || isForgotPassword) {
                            OutlinedTextField(
                                value = email,
                                onValueChange = { email = it },
                                placeholder = { Text("Email") },
                                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(50.dp)
                            )
                        }

                        if (isRegistering) {
                            OutlinedTextField(
                                value = mobile,
                                onValueChange = { mobile = it },
                                placeholder = { Text("Mobile") },
                                leadingIcon = { Icon(Icons.Default.PhoneAndroid, contentDescription = null) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(50.dp)
                            )
                            OutlinedTextField(
                                value = tell,
                                onValueChange = { tell = it },
                                placeholder = { Text("Tell") },
                                leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(50.dp)
                            )
                            OutlinedTextField(
                                value = address,
                                onValueChange = { address = it },
                                placeholder = { Text("Address") },
                                leadingIcon = { Icon(Icons.Default.Home, contentDescription = null) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(50.dp)
                            )
                            OutlinedTextField(
                                value = postalCode,
                                onValueChange = { postalCode = it },
                                placeholder = { Text("Postal Code") },
                                leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(50.dp)
                            )
                        }

                        if (!isForgotPassword) {
                            OutlinedTextField(
                                value = password,
                                onValueChange = { password = it },
                                placeholder = { Text("Password") },
                                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(50.dp),
                                visualTransformation = PasswordVisualTransformation()
                            )
                        } else {
                            OutlinedTextField(
                                value = newPassword,
                                onValueChange = { newPassword = it },
                                placeholder = { Text("New Password") },
                                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(50.dp),
                                visualTransformation = PasswordVisualTransformation()
                            )
                        }

                        Button(
                            onClick = {
                                if (isForgotPassword) {
                                    onForgotPassword(username, newPassword)
                                    isForgotPassword = false
                                } else if (isRegistering) {
                                    onRegister(User(username, password, email, mobile, tell, address, postalCode))
                                } else {
                                    onLogin(username, password)
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            shape = RoundedCornerShape(50.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9191FF))
                        ) {
                            Text(
                                if (isForgotPassword) "RESET PASSWORD" else if (isRegistering) "REGISTER" else "LOGIN",
                                fontWeight = FontWeight.Bold
                            )
                        }

                        if (!isRegistering && !isForgotPassword) {
                            TextButton(onClick = { isForgotPassword = true }) {
                                Text("Forgot Password?", color = Color(0xFF9191FF))
                            }
                        }

                        TextButton(onClick = {
                            if (isForgotPassword) {
                                isForgotPassword = false
                            } else {
                                onModeChange(!isRegistering)
                            }
                        }) {
                            Text(
                                if (isForgotPassword) "Back to Login"
                                else if (isRegistering) "Already have an account? Login"
                                else "Not yet registered? SignUp Now",
                                color = Color(0xFF9191FF),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun LoginPreview() {
    LoginScreen(isRegistering = false, onModeChange = {}, onLogin = {_, _ ->}, onRegister = {}, onForgotPassword = {_, _ ->})
}

package com.example.nammasantheledger

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.example.nammasantheledger.data.AppDatabase
import com.example.nammasantheledger.ui.CustomerDetailScreen
import kotlinx.coroutines.flow.collectLatest

class CustomerDetailActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val name = intent.getStringExtra("name") ?: ""
        val dao = AppDatabase.getDatabase(this).transactionDao()

        setContent {

            var phone by remember { mutableStateOf("") }
            var balance by remember { mutableStateOf(0.0) }

            LaunchedEffect(Unit) {
                dao.getAllTransactions().collectLatest { list ->

                    val customerTxns = list.filter { it.customerName == name }

                    phone = customerTxns.firstOrNull()?.phone ?: ""

                    balance = customerTxns.sumOf {
                        if (it.type == "Credit") it.amount else -it.amount
                    }
                }
            }

            CustomerDetailScreen(
                name = name,
                phone = phone,
                balance = balance,
                onBack = { finish() },
                onEdit = { },
                onUpdate = { },
                onWhatsApp = {
                    val message = "Hi $name, your balance is ₹$balance"

                    val url = "https://wa.me/$phone?text=${Uri.encode(message)}"

                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(url)

                    startActivity(intent)
                }
            )
        }
    }
}
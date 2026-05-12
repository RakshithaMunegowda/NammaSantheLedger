package com.example.nammasantheledger

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.example.nammasantheledger.data.AppDatabase
import com.example.nammasantheledger.ui.CustomerLedgerUI
import com.example.nammasantheledger.ui.CustomerScreen
import kotlinx.coroutines.flow.collectLatest

class CustomerActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dao = AppDatabase.getDatabase(this).transactionDao()

        setContent {

            var customers by remember { mutableStateOf(listOf<CustomerLedgerUI>()) }

            LaunchedEffect(Unit) {
                dao.getAllTransactions().collectLatest { list ->

                    val grouped = list.groupBy { it.customerName }

                    customers = grouped.map { (name, transactions) ->

                        val balance = transactions.sumOf {
                            if (it.type == "Credit") it.amount else -it.amount
                        }

                        CustomerLedgerUI(
                            name = name,
                            phone = transactions.first().phone, // ✅ take phone
                            balance = balance,
                            totalOrders = transactions.size
                        )
                    }
                }
            }

            CustomerScreen(
                customers = customers,
                onBack = { finish() },
                onClick = { name ->
                    val intent = Intent(this, CustomerDetailActivity::class.java)
                    intent.putExtra("name", name)
                    startActivity(intent)
                }
            )
        }
    }
}
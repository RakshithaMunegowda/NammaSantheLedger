package com.example.nammasantheledger

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.example.nammasantheledger.data.AppDatabase
import com.example.nammasantheledger.ui.HomeScreen
import kotlinx.coroutines.launch

class HomeActivity : ComponentActivity() {

    private var totalCredit = 0.0
    private var totalPayment = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dao = AppDatabase.getDatabase(this).transactionDao()

        lifecycleScope.launch {
            dao.getAllTransactions().collect { transactions ->
                totalCredit = transactions
                    .filter { it.type == "Credit" }
                    .sumOf { it.amount }

                totalPayment = transactions
                    .filter { it.type == "Payment" }
                    .sumOf { it.amount }

                setContent {
                    HomeScreen(
                        totalCredit = totalCredit,
                        totalPayment = totalPayment,
                        onAdd = {
                            // startActivity(Intent(this@HomeActivity, AddTransactionActivity::class.java))
                        },
                        onCustomers = {
                            startActivity(Intent(this@HomeActivity, CustomerActivity::class.java))
                        },
                        onViewTransactions = {
                            startActivity(Intent(this@HomeActivity, TransactionListActivity::class.java))
                        },
                        onSettings = {}
                    )
                }
            }
        }
    }
}
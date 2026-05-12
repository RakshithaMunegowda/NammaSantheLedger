package com.example.nammasantheledger

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.lifecycleScope
import com.example.nammasantheledger.data.AppDatabase
import com.example.nammasantheledger.ui.TransactionListScreen
import com.example.nammasantheledger.ui.TransactionUI
import kotlinx.coroutines.flow.map

class TransactionListActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dao = AppDatabase.getDatabase(this).transactionDao()

        setContent {
            val transactions by dao.getAllTransactions()
                .map { list ->
                    list.map {
                        TransactionUI(
                            id = it.id,
                            name = it.customerName,
                            amount = it.amount,
                            type = it.type
                        )
                    }
                }
                .collectAsState(initial = emptyList())

            TransactionListScreen(
                transactions = transactions,
                onBack = { finish() }
            )
        }
    }
}

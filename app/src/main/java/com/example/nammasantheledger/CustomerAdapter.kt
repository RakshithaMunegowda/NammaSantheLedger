package com.example.nammasantheledger

import androidx.compose.foundation.lazy.*
import androidx.compose.runtime.Composable
import com.example.nammasantheledger.ui.CustomerItem

@Composable
fun CustomerList(customers: List<Customer>) {
    LazyColumn {
        items(customers) { customer ->
            CustomerItem(customer)
        }
    }
}
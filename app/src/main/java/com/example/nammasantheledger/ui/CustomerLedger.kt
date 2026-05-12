package com.example.nammasantheledger.ui

data class CustomerLedger(
    val name: String,
    val phone: String, // ✅ NEW
    val balance: Double,
    val totalOrders: Int
)
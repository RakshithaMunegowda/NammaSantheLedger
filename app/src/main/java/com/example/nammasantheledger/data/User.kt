package com.example.nammasantheledger.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val username: String,
    val password: String,
    val email: String,
    val mobile: String = "",
    val tell: String = "",
    val address: String = "",
    val postalCode: String = ""
)

package com.example.nammasantheledger.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Insert
    suspend fun insert(transaction: Transaction)

    @Query("SELECT * FROM transactions")
    fun getAllTransactions(): Flow<List<Transaction>>   // ✅ IMPORTANT

    @Query("SELECT * FROM transactions WHERE phone = :phone")
    fun getTransactionsByPhone(phone: String): Flow<List<Transaction>>

    @Query("UPDATE transactions SET customerName = :newName, phone = :newPhone WHERE phone = :oldPhone")
    suspend fun updateCustomerDetails(oldPhone: String, newName: String, newPhone: String)
}
package com.example.nammasantheledger

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.nammasantheledger.data.AppDatabase
import com.example.nammasantheledger.data.Transaction
import com.example.nammasantheledger.data.User
import com.example.nammasantheledger.ui.CustomerLedgerUI
import com.example.nammasantheledger.ui.TransactionUI
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getDatabase(application)
    private val dao = database.transactionDao()
    private val userDao = database.userDao()

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    sealed class LoginResult {
        object Success : LoginResult()
        object UserNotFound : LoginResult()
        object WrongPassword : LoginResult()
    }

    suspend fun login(username: String, password: String): LoginResult {
        val user = userDao.getUserByUsername(username)
        return when {
            user == null -> LoginResult.UserNotFound
            user.password == password -> {
                _currentUser.value = user
                LoginResult.Success
            }
            else -> LoginResult.WrongPassword
        }
    }

    suspend fun register(user: User): Boolean {
        return try {
            userDao.registerUser(user)
            // Removed auto-login so user must log in manually after registration
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun updatePassword(username: String, newPassword: String) {
        userDao.updatePassword(username, newPassword)
    }

    suspend fun updateUser(user: User) {
        userDao.updateUser(user)
        _currentUser.value = user
    }

    fun logout() {
        _currentUser.value = null
    }

    val transactions: StateFlow<List<TransactionUI>> = dao.getAllTransactions()
        .map { list ->
            list.map {
                TransactionUI(it.id, it.customerName, it.amount, it.type)
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val customers: StateFlow<List<CustomerLedgerUI>> = dao.getAllTransactions()
        .map { list ->
            list.groupBy { it.phone }
                .map { (phone, txns) ->
                    val name = txns.first().customerName
                    val balance = txns.sumOf { if (it.type == "Credit") it.amount else -it.amount }
                    CustomerLedgerUI(name, phone, balance, txns.size)
                }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val totalCredit: StateFlow<Double> = dao.getAllTransactions()
        .map { list ->
            list.groupBy { it.phone }
                .map { (_, txns) ->
                    txns.sumOf { if (it.type == "Credit") it.amount else -it.amount }
                }
                .filter { it > 0 }
                .sum()
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    val totalPayment: StateFlow<Double> = dao.getAllTransactions()
        .map { list ->
            list.groupBy { it.phone }
                .map { (_, txns) ->
                    txns.sumOf { if (it.type == "Credit") it.amount else -it.amount }
                }
                .filter { it < 0 }
                .sumOf { kotlin.math.abs(it) }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    fun addTransaction(transaction: Transaction) {
        viewModelScope.launch {
            dao.insert(transaction)
        }
    }

    fun updateCustomerDetails(oldPhone: String, newName: String, newPhone: String) {
        viewModelScope.launch {
            dao.updateCustomerDetails(oldPhone, newName, newPhone)
        }
    }
}

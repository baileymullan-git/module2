package com.example.expenselogger.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.expenselogger.data.ReceiptEntity
import com.example.expenselogger.data.ReceiptRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: ReceiptRepository) : ViewModel() {
    val receipts: LiveData<List<ReceiptEntity>> = repository.receipts
    val totalAmount: LiveData<Double> = repository.totalAmount

    fun saveReceipt(imageUri: String, timestamp: Long, amount: Double) {
        viewModelScope.launch {
            repository.saveReceipt(imageUri, timestamp, amount)
        }
    }
}

class MainViewModelFactory(private val repository: ReceiptRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

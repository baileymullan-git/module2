package com.example.expenselogger.data

import androidx.lifecycle.LiveData

class ReceiptRepository(private val receiptDao: ReceiptDao) {
    val receipts: LiveData<List<ReceiptEntity>> = receiptDao.getAllReceipts()
    val totalAmount: LiveData<Double> = receiptDao.getTotalAmount()

    suspend fun saveReceipt(imageUri: String, timestamp: Long, amount: Double) {
        receiptDao.insert(
            ReceiptEntity(
                imageUri = imageUri,
                timestamp = timestamp,
                amount = amount
            )
        )
    }
}

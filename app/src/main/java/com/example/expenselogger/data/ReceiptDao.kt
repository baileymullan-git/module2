package com.example.expenselogger.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ReceiptDao {
    @Insert
    suspend fun insert(receipt: ReceiptEntity)

    @Query("SELECT * FROM receipts ORDER BY timestamp DESC")
    fun getAllReceipts(): LiveData<List<ReceiptEntity>>

    @Query("SELECT COALESCE(SUM(amount), 0) FROM receipts")
    fun getTotalAmount(): LiveData<Double>
}

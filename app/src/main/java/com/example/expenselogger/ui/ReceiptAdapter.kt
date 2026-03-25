package com.example.expenselogger.ui

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.expenselogger.data.ReceiptEntity
import com.example.expenselogger.databinding.ItemReceiptBinding
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ReceiptAdapter : ListAdapter<ReceiptEntity, ReceiptAdapter.ReceiptViewHolder>(DiffCallback) {
    private val dateFormat = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
    private val currencyFormatter = NumberFormat.getCurrencyInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReceiptViewHolder {
        val binding = ItemReceiptBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReceiptViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReceiptViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ReceiptViewHolder(private val binding: ItemReceiptBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ReceiptEntity) {
            binding.receiptImage.setImageURI(Uri.parse(item.imageUri))
            binding.amountText.text = currencyFormatter.format(item.amount)
            binding.timestampText.text = dateFormat.format(Date(item.timestamp))
        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<ReceiptEntity>() {
        override fun areItemsTheSame(oldItem: ReceiptEntity, newItem: ReceiptEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ReceiptEntity, newItem: ReceiptEntity): Boolean {
            return oldItem == newItem
        }
    }
}

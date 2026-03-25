package com.example.expenselogger.ui

import android.net.Uri
import android.os.Bundle
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expenselogger.R
import com.example.expenselogger.data.AppDatabase
import com.example.expenselogger.data.ReceiptRepository
import com.example.expenselogger.databinding.ActivityMainBinding
import java.io.File
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var pendingImageUri: Uri
    private lateinit var pendingImageFile: File
    private val receiptAdapter = ReceiptAdapter()
    private val currencyFormatter = NumberFormat.getCurrencyInstance(Locale.getDefault())

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            ReceiptRepository(
                AppDatabase.getInstance(applicationContext).receiptDao()
            )
        )
    }

    private val takePicture = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            promptForAmountAndSave(pendingImageUri)
        } else if (::pendingImageFile.isInitialized) {
            pendingImageFile.delete()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupList()
        observeData()

        binding.captureReceiptButton.setOnClickListener {
            launchCamera()
        }
    }

    private fun setupList() {
        binding.receiptsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = receiptAdapter
        }
    }

    private fun observeData() {
        viewModel.receipts.observe(this) { receipts ->
            receiptAdapter.submitList(receipts)
            binding.emptyStateText.visibility = if (receipts.isEmpty()) android.view.View.VISIBLE else android.view.View.GONE
        }

        viewModel.totalAmount.observe(this) { total ->
            binding.totalText.text = getString(R.string.total_claim_value, currencyFormatter.format(total))
        }
    }

    private fun launchCamera() {
        val receiptDir = File(filesDir, "receipts").apply { mkdirs() }
        pendingImageFile = File(receiptDir, "receipt_${System.currentTimeMillis()}.jpg")
        pendingImageUri = FileProvider.getUriForFile(
            this,
            "$packageName.fileprovider",
            pendingImageFile
        )
        takePicture.launch(pendingImageUri)
    }

    private fun promptForAmountAndSave(imageUri: Uri) {
        val amountInput = EditText(this).apply {
            hint = getString(R.string.amount_hint)
            inputType = android.text.InputType.TYPE_CLASS_NUMBER or
                android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL
        }

        AlertDialog.Builder(this)
            .setTitle(R.string.enter_amount_title)
            .setView(amountInput)
            .setCancelable(false)
            .setPositiveButton(R.string.save) { _, _ ->
                val value = amountInput.text.toString().trim().toDoubleOrNull()
                if (value == null || value < 0.0) {
                    pendingImageFile.delete()
                    return@setPositiveButton
                }
                viewModel.saveReceipt(
                    imageUri = imageUri.toString(),
                    timestamp = System.currentTimeMillis(),
                    amount = value
                )
            }
            .setNegativeButton(R.string.cancel) { _, _ ->
                pendingImageFile.delete()
            }
            .show()
    }
}

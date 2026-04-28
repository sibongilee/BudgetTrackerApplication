package com.example.budgettrackerapplication

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.budgettrackerapplication.data.BudgetRepository
import com.example.budgettrackerapplication.data.entity.Category
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class AddExpenseActivity : AppCompatActivity() {
    
    private lateinit var repository: BudgetRepository
    private var userId: Long = 0
    private var username: String = ""
    private var selectedCategoryId: Long = 0
    private var categories: List<Category> = emptyList()
    private var currentPhotoPath: String? = null
    
    private lateinit var etAmount: EditText
    private lateinit var etDescription: EditText
    private lateinit var etDate: EditText
    private lateinit var etStartTime: EditText
    private lateinit var etEndTime: EditText
    private lateinit var spCategory: Spinner
    private lateinit var imgReceipt: ImageView
    private lateinit var btnAddPhoto: Button
    private lateinit var btnSaveExpense: Button
    private lateinit var btnCancel: Button
    
    // Camera intent launcher
    private val takePictureLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            // Photo was taken successfully
            currentPhotoPath?.let { path ->
                val bitmap = BitmapFactory.decodeFile(path)
                imgReceipt.setImageBitmap(bitmap)
                imgReceipt.visibility = android.view.View.VISIBLE
                Toast.makeText(this, "Receipt photo captured!", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    // Gallery intent launcher
    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val imageUri = result.data?.data
            if (imageUri != null) {
                imgReceipt.setImageURI(imageUri)
                imgReceipt.visibility = android.view.View.VISIBLE
                currentPhotoPath = imageUri.toString()
                Toast.makeText(this, "Receipt attached from gallery!", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)
        
        // Retrieve data from Intent (passed from MainActivity)
        userId = intent.getLongExtra("USER_ID", 0)
        username = intent.getStringExtra("USERNAME") ?: "User"
        val action = intent.getStringExtra("ACTION")
        
        if (userId == 0L) {
            Toast.makeText(this, "Error: User not found", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        
        repository = BudgetRepository(this)
        
        initializeViews()
        setupClickListeners()
        loadCategories()
        setDefaultDateTime()
        
        // Display welcome message with username from intent
        Toast.makeText(this, "Adding expense for $username", Toast.LENGTH_SHORT).show()
    }
    
    private fun initializeViews() {
        etAmount = findViewById(R.id.etAmount)
        etDescription = findViewById(R.id.etDescription)
        etDate = findViewById(R.id.etDate)
        etStartTime = findViewById(R.id.etStartTime)
        etEndTime = findViewById(R.id.etEndTime)
        spCategory = findViewById(R.id.spCategory)
        imgReceipt = findViewById(R.id.imgReceipt)
        btnAddPhoto = findViewById(R.id.btnAddPhoto)
        btnSaveExpense = findViewById(R.id.btnSaveExpense)
        btnCancel = findViewById(R.id.btnCancel)
    }
    
    private fun setupClickListeners() {
        etDate.setOnClickListener { showDatePicker() }
        etStartTime.setOnClickListener { showTimePicker(etStartTime) }
        etEndTime.setOnClickListener { showTimePicker(etEndTime) }
        
        // Button to open photo selection dialog
        btnAddPhoto.setOnClickListener { showPhotoSelectionDialog() }
        
        btnSaveExpense.setOnClickListener { saveExpense() }
        
        // Cancel button with intent to go back
        btnCancel.setOnClickListener {
            finish() // Just finish, no result needed
        }
    }
    
    private fun showPhotoSelectionDialog() {
        val options = arrayOf("Take Photo with Camera", "Choose from Gallery", "Remove Photo")
        
        AlertDialog.Builder(this)
            .setTitle("Add Receipt Photo")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> dispatchTakePictureIntent()
                    1 -> openGallery()
                    2 -> removePhoto()
                }
            }
            .show()
    }
    
    // IMPLICIT INTENT: Launch Camera app
    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    Toast.makeText(this, "Error creating file", Toast.LENGTH_SHORT).show()
                    null
                }
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "${packageName}.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    takePictureLauncher.launch(takePictureIntent)
                }
            }
        }
    }
    
    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "JPEG_${timeStamp}_"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val imageFile = File.createTempFile(imageFileName, ".jpg", storageDir)
        currentPhotoPath = imageFile.absolutePath
        return imageFile
    }
    
    // IMPLICIT INTENT: Open Gallery app
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickImageLauncher.launch(intent)
    }
    
    private fun removePhoto() {
        imgReceipt.setImageResource(android.R.drawable.ic_menu_camera)
        imgReceipt.visibility = android.view.View.VISIBLE
        currentPhotoPath = null
        Toast.makeText(this, "Photo removed", Toast.LENGTH_SHORT).show()
    }
    
    private fun setDefaultDateTime() {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        
        etDate.setText(dateFormat.format(calendar.time))
        etStartTime.setText(timeFormat.format(calendar.time))
        calendar.add(Calendar.HOUR_OF_DAY, 1)
        etEndTime.setText(timeFormat.format(calendar.time))
    }
    
    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            this,
            { _, year, month, day ->
                val date = String.format("%04d-%02d-%02d", year, month + 1, day)
                etDate.setText(date)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }
    
    private fun showTimePicker(editText: EditText) {
        val calendar = Calendar.getInstance()
        TimePickerDialog(
            this,
            { _, hour, minute ->
                val time = String.format("%02d:%02d", hour, minute)
                editText.setText(time)
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        ).show()
    }
    
    private fun loadCategories() {
        lifecycleScope.launch {
            repository.getCategories(userId).collect { categoryList ->
                categories = categoryList
                setupCategorySpinner()
            }
        }
    }
    
    private fun setupCategorySpinner() {
        val categoryNames = categories.map { it.name }.toTypedArray()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categoryNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spCategory.adapter = adapter
        
        if (categories.isNotEmpty()) {
            selectedCategoryId = categories[0].id
        }
        
        spCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                selectedCategoryId = categories[position].id
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
    
    private fun saveExpense() {
        val amountStr = etAmount.text.toString().trim()
        val description = etDescription.text.toString().trim()
        val date = etDate.text.toString().trim()
        val startTime = etStartTime.text.toString().trim()
        val endTime = etEndTime.text.toString().trim()
        
        if (amountStr.isEmpty()) {
            Toast.makeText(this, "Please enter amount", Toast.LENGTH_SHORT).show()
            return
        }
        
        if (description.isEmpty()) {
            Toast.makeText(this, "Please enter description", Toast.LENGTH_SHORT).show()
            return
        }
        
        val amount = amountStr.toDoubleOrNull()
        if (amount == null || amount <= 0) {
            Toast.makeText(this, "Please enter valid amount", Toast.LENGTH_SHORT).show()
            return
        }
        
        lifecycleScope.launch {
            try {
                val expenseId = repository.addExpense(
                    amount = amount,
                    description = description,
                    date = date,
                    startTime = startTime,
                    endTime = endTime,
                    categoryId = selectedCategoryId,
                    userId = userId,
                    photoPath = currentPhotoPath
                )
                
                Toast.makeText(this@AddExpenseActivity, "Expense saved! ID: $expenseId", Toast.LENGTH_SHORT).show()
                
                // Create result intent to send back to MainActivity
                val resultIntent = Intent().apply {
                    putExtra("RESULT_MESSAGE", "Expense added: $description - R$amount")
                    putExtra("EXPENSE_ID", expenseId)
                }
                setResult(RESULT_OK, resultIntent)
                finish()
            } catch (e: Exception) {
                Toast.makeText(this@AddExpenseActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}

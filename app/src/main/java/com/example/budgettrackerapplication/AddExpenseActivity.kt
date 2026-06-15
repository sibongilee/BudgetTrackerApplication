package com.example.budgettrackerapplication

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class AddExpenseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

        // Setup toolbar with back button
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.title = "Back"

        // Use DatabaseHelper - NOT Room
        val db = DatabaseHelper(this)

        val etAmount = findViewById<EditText>(R.id.etAmount)
        val etDescription = findViewById<EditText>(R.id.etDescription)
        val etDate = findViewById<EditText>(R.id.etDate)
        val etStartTime = findViewById<EditText>(R.id.etStartTime)
        val etEndTime = findViewById<EditText>(R.id.etEndTime)
        val spCategory = findViewById<Spinner>(R.id.spCategory)
        val btnSave = findViewById<Button>(R.id.btnSaveExpense)

        // Load categories into spinner
        val categories = db.getAllCategories()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spCategory.adapter = adapter

        btnSave.setOnClickListener {
            val amountText = etAmount.text.toString().trim()
            val description = etDescription.text.toString().trim()
            val date = etDate.text.toString().trim()
            val startTime = etStartTime.text.toString().trim()
            val endTime = etEndTime.text.toString().trim()
            val category = spCategory.selectedItem.toString()

            when {
                amountText.isEmpty() -> Toast.makeText(this, "Enter amount", Toast.LENGTH_SHORT).show()
                description.isEmpty() -> Toast.makeText(this, "Enter description", Toast.LENGTH_SHORT).show()
                date.isEmpty() -> Toast.makeText(this, "Enter date", Toast.LENGTH_SHORT).show()
                startTime.isEmpty() -> Toast.makeText(this, "Enter start time", Toast.LENGTH_SHORT).show()
                endTime.isEmpty() -> Toast.makeText(this, "Enter end time", Toast.LENGTH_SHORT).show()
                else -> {
                    val amount = amountText.toDoubleOrNull()
                    if (amount == null) {
                        Toast.makeText(this, "Enter a valid amount", Toast.LENGTH_SHORT).show()
                    } else {
                        db.addExpense(amount, description, category, date, startTime, endTime, null)
                        Toast.makeText(this, "Expense saved", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
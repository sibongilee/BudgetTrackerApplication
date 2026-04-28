package com.example.budgettrackerapplication

import android.os.Bundle
import android.widget.EditText
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddExpenseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // expense form screen
        setContentView(R.layout.activity_add_expense)
        // Calling the database helper class

        val db = DatabaseHelper(this)
        // Initialize the EditText fields

        val etAmount = findViewById<EditText>(R.id.etAmount)
        val etDescription = findViewById<EditText>(R.id.etDescription)
        val btnSave = findViewById<Button>(R.id.btnSaveExpense)

        btnSave.setOnClickListener {
            val amountText = etAmount.text.toString().trim()
            val description = etDescription.text.toString().trim()

            if (amountText.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val amount = amountText.toDoubleOrNull()

            if (amount == null) {
                Toast.makeText(this, "Enter a valid amount", Toast.LENGTH_SHORT).show()
            } else {
                db.addExpense(
                    amount,
                    description,
                    "Food",
                    "2026-04-28",
                    "10:00",
                    "11:00",
                    null
                )

                Toast.makeText(this, "Expense saved", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
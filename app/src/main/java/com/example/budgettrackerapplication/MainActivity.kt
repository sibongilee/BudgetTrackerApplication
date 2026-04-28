package com.example.budgettrackerapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.content.Intent
import android.widget.Toast


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // dashboard layout
        setContentView(R.layout.activity_main)
        // Student-written: linking buttons
        val btnAddExpense = findViewById<Button>(R.id.btnAddExpense)
        val btnViewExpenses = findViewById<Button>(R.id.btnViewExpenses)
        val btnCategory = findViewById<Button>(R.id.btnAddCategory)
        val btnBudget = findViewById<Button>(R.id.btnBudget)

        // Navigation with safety Toast (debug)
        btnAddExpense.setOnClickListener {
            Toast.makeText(this, "Opening Add Expense", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, AddExpenseActivity::class.java))
        }

        btnViewExpenses.setOnClickListener {
            Toast.makeText(this, "Opening View Expenses", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, ViewExpensesActivity::class.java))
        }

        btnCategory.setOnClickListener {
            Toast.makeText(this, "Opening Categories", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, AddCategoryActivity::class.java))
        }

        btnBudget.setOnClickListener {
            Toast.makeText(this, "Opening Budget", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, BudgetActivity::class.java))
        }


    }
}
package com.example.budgettrackerapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.content.Intent
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.example.budgettrackerapplication.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Setup toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.title = "Dashboard"

        // Get username from intent
        val tvWelcome = findViewById<TextView>(R.id.tvWelcome)
        val username = intent.getStringExtra("USERNAME") ?: "User"
        tvWelcome.text = "Welcome, $username! 👋"

        // Linking buttons
        val btnAddExpense = findViewById<Button>(R.id.btnAddExpense)
        val btnViewExpenses = findViewById<Button>(R.id.btnViewExpenses)
        val btnCategory = findViewById<Button>(R.id.btnAddCategory)
        val btnBudget = findViewById<Button>(R.id.btnBudget)

        btnAddExpense.setOnClickListener {
            startActivity(Intent(this, AddExpenseActivity::class.java))
        }

        btnViewExpenses.setOnClickListener {
            startActivity(Intent(this, ViewExpensesActivity::class.java))
        }

        btnCategory.setOnClickListener {
            startActivity(Intent(this, AddCategoryActivity::class.java))
        }

        btnBudget.setOnClickListener {
            startActivity(Intent(this, BudgetActivity::class.java))
        }
    }
}
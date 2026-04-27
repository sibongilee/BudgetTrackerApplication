package com.example.budgettrackerapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        findViewById<Button>(R.id.btnAddExpense).setOnClickListener {
            startActivity(Intent(this, AddExpenseActivity::class.java))
        }

        findViewById<Button>(R.id.btnViewExpenses).setOnClickListener {
            startActivity(Intent(this, ViewExpensesActivity::class.java))
        }

        findViewById<Button>(R.id.btnAddCategory).setOnClickListener {
            startActivity(Intent(this, AddCategoryActivity::class.java))
        }

        findViewById<Button>(R.id.btnBudget).setOnClickListener {
            startActivity(Intent(this, BudgetActivity::class.java))
        }
    }
}
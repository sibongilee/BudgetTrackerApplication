package com.example.budgettrackerapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.content.Intent

class MainActivity : AppCompatActivity() {
    private var userId: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        // get user id from intent
        userId = intent.getLongExtra("USER_ID", 1)

        // Student-written: navigation buttons
        findViewById<Button>(R.id.btnAddExpense).setOnClickListener {
            startActivity(Intent(this, AddExpenseActivity::class.java).putExtra("USER_ID", userId))
        }

        findViewById<Button>(R.id.btnAddCategory).setOnClickListener {
            startActivity(Intent(this, AddCategoryActivity::class.java).putExtra("USER_ID", userId))
        }

        findViewById<Button>(R.id.btnViewExpenses).setOnClickListener {
            startActivity(Intent(this, ViewExpensesActivity::class.java).putExtra("USER_ID", userId))
        }

        findViewById<Button>(R.id.btnBudget).setOnClickListener {
            startActivity(Intent(this, BudgetActivity::class.java).putExtra("USER_ID", userId))
        }

        findViewById<Button>(R.id.btnCategoryTotals).setOnClickListener {
            startActivity(Intent(this, CategoryTotalsActivity::class.java).putExtra("USER_ID", userId))
            }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
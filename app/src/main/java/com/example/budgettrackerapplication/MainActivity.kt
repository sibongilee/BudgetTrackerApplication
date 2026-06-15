package com.example.budgettrackerapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.ProgressBar

import android.content.Intent
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.example.budgettrackerapplication.R
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("MainActivity", "Dashboard opened for user: $username")
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
        progressBudget = findViewById(R.id.progressBudget)
        txtProgresStatus = findViewById(R.id.txtProgresStatus)
        updateProgress()


        // Linking buttons
        val btnAddExpense = findViewById<Button>(R.id.btnAddExpense)
        val btnViewExpenses = findViewById<Button>(R.id.btnViewExpenses)
        val btnCategory = findViewById<Button>(R.id.btnAddCategory)
        val btnBudget = findViewById<Button>(R.id.btnBudget)
        val btnRewards = findViewById<Button>(R.id.btnRewards)
        val btnGraph = findViewById<Button>(R.id.btnGraph)

        btnAddExpense.setOnClickListener {
            Log.d("MainActivity", "Navigating to Add Expense")
            startActivity(Intent(this, AddExpenseActivity::class.java))
        }

        btnViewExpenses.setOnClickListener {
            Log.d("MainActivity", "Navigating to View Expenses")
            startActivity(Intent(this, ViewExpensesActivity::class.java))
        }

        btnCategory.setOnClickListener {
            startActivity(Intent(this, AddCategoryActivity::class.java))
        }

        btnBudget.setOnClickListener {
            Log.d("MainActivity", "Navigating to Budget Goals")
            startActivity(Intent(this, BudgetActivity::class.java))
        }
        btnRewards.setOnClickListener {
            startActivity(Intent(this, RewardsActivity::class.java))
        }
        btnGraph.setOnClickListener {
            startActivity(Intent(this, GraphActivity::class.java))
        }
    }
        private fun updateProgress(){
            val totalBudget = 3000.0
            val currentSpent = 1200.0
            val percentage = ((currentSpent / totalBudget) * 100).toInt()

            progressBudget.progress = percentage
            if (currentSpent <= totalBudget){
                txtProgresStatus.text = "✅ You're on track!"
            }else{
                txtProgresStatus.text = "❌ You're not on track!"
            }
        }
}

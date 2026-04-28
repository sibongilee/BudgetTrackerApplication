package com.example.budgettrackerapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.budgettrackerapplication.data.BudgetRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    
    private lateinit var repository: BudgetRepository
    private var userId: Long = 0
    private var username: String = ""
    private var loginTime: Long = 0
    
    private lateinit var tvWelcome: TextView
    private lateinit var tvLoginInfo: TextView
    private lateinit var btnAddExpense: Button
    private lateinit var btnAddCategory: Button
    private lateinit var btnViewExpenses: Button
    private lateinit var btnBudget: Button
    private lateinit var btnLogout: Button
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        // Retrieve data passed from LoginActivity using Intent
        userId = intent.getLongExtra("USER_ID", 0)
        username = intent.getStringExtra("USERNAME") ?: "User"
        loginTime = intent.getLongExtra("LOGIN_TIME", 0)
        
        if (userId == 0L) {
            Toast.makeText(this, "Please login first", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        
        repository = BudgetRepository(this)
        
        initializeViews()
        setupClickListeners()
        updateWelcomeMessage()
        displayLoginInfo()
    }
    
    private fun initializeViews() {
        tvWelcome = findViewById(R.id.tvWelcome)
        tvLoginInfo = findViewById(R.id.tvLoginInfo)
        btnAddExpense = findViewById(R.id.btnAddExpense)
        btnAddCategory = findViewById(R.id.btnAddCategory)
        btnViewExpenses = findViewById(R.id.btnViewExpenses)
        btnBudget = findViewById(R.id.btnBudget)
        btnLogout = findViewById(R.id.btnLogout)
    }
    
    private fun setupClickListeners() {
        // EXPLICIT INTENT: Navigate to AddExpenseActivity with data
        btnAddExpense.setOnClickListener {
            val intent = Intent(this, AddExpenseActivity::class.java).apply {
                putExtra("USER_ID", userId)
                putExtra("USERNAME", username)
                putExtra("ACTION", "ADD_EXPENSE")
            }
            startActivity(intent)
        }
        
        // EXPLICIT INTENT: Navigate to AddCategoryActivity
        btnAddCategory.setOnClickListener {
            val intent = Intent(this, AddCategoryActivity::class.java).apply {
                putExtra("USER_ID", userId)
                putExtra("USERNAME", username)
            }
            startActivity(intent)
        }
        
        // EXPLICIT INTENT: Navigate to ViewExpensesActivity
        btnViewExpenses.setOnClickListener {
            val intent = Intent(this, ViewExpensesActivity::class.java).apply {
                putExtra("USER_ID", userId)
                putExtra("USERNAME", username)
            }
            startActivity(intent)
        }
        
        // EXPLICIT INTENT: Navigate to BudgetActivity
        btnBudget.setOnClickListener {
            val intent = Intent(this, BudgetActivity::class.java).apply {
                putExtra("USER_ID", userId)
                putExtra("USERNAME", username)
            }
            startActivity(intent)
        }
        
        // IMPLICIT INTENT: Logout and go back to Login
        btnLogout.setOnClickListener {
            // Clear any saved session data
            val intent = Intent(this, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                putExtra("LOGGED_OUT", true)
            }
            startActivity(intent)
            finish()
        }
    }
    
    private fun updateWelcomeMessage() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        
        val greeting = when (hour) {
            in 0..11 -> "Good Morning"
            in 12..16 -> "Good Afternoon"
            else -> "Good Evening"
        }
        
        tvWelcome.text = "$greeting, $username 👋"
    }
    
    private fun displayLoginInfo() {
        if (loginTime > 0) {
            val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
            val timeStr = dateFormat.format(Date(loginTime))
            tvLoginInfo.text = "Logged in at: $timeStr"
        }
    }
    
    // Handle results coming back from child activities
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null) {
            when (requestCode) {
                100 -> {
                    val message = data.getStringExtra("RESULT_MESSAGE")
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    
    override fun onResume() {
        super.onResume()
        // Refresh dashboard data when returning from other activities
        lifecycleScope.launch {
            // Refresh any data if needed
        }
    }
}

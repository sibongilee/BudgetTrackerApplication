package com.example.budgettrackerapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.budgettrackerapplication.data.BudgetRepository
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    
    private lateinit var repository: BudgetRepository
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        
        repository = BudgetRepository(this)
        
        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        
        btnLogin.setOnClickListener { performLogin() }
    }
    
    private fun performLogin() {
        val username = etUsername.text.toString().trim()
        val password = etPassword.text.toString()
        
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show()
            return
        }
        
        lifecycleScope.launch {
            val user = repository.loginUser(username, password)
            
            if (user != null) {
                Toast.makeText(this@LoginActivity, "Login successful!", Toast.LENGTH_SHORT).show()
                
                // EXPLICIT INTENT: Navigate from LoginActivity to MainActivity
                // Passing data using putExtra()
                val intent = Intent(this@LoginActivity, MainActivity::class.java).apply {
                    putExtra("USER_ID", user.id)
                    putExtra("USERNAME", user.username)
                    putExtra("LOGIN_TIME", System.currentTimeMillis())
                }
                startActivity(intent)
                finish() // Close LoginActivity so user can't go back
            } else {
                showRegistrationDialog(username, password)
            }
        }
    }
    
    private fun showRegistrationDialog(username: String, password: String) {
        AlertDialog.Builder(this)
            .setTitle("New User?")
            .setMessage("Username '$username' not found. Would you like to create a new account?")
            .setPositiveButton("Register") { _, _ ->
                lifecycleScope.launch {
                    try {
                        val userId = repository.registerUser(username, password)
                        Toast.makeText(
                            this@LoginActivity,
                            "Account created! Please login.",
                            Toast.LENGTH_SHORT
                        ).show()
                    } catch (e: Exception) {
                        Toast.makeText(
                            this@LoginActivity,
                            "Registration failed: ${e.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}

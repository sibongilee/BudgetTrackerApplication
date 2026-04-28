package com.example.budgettrackerapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import android.widget.EditText
import android.widget.Button
import android.widget.Toast
import android.content.Intent


class LoginActivity : AppCompatActivity() {
    // setup for repository and coroutines
    private lateinit var repository: BudgetRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        repository = BudgetRepository(this)
        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        // login button click listener
        btnLogin.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()
            // launch lifecycle coroutine
            lifecycleScope.launch {
                val user = repository.loginUser(username, password)
                if (user != null) {
                    // navigate to main activity
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.putExtra("User_ID", user.id)
                    startActivity(intent)
                    finish()
                } else {
                    // show error message
                    Toast.makeText(this@LoginActivity, "Invalid username or password", Toast.LENGTH_SHORT).show()
                }
            }
            }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
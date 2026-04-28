package com.example.budgettrackerapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.EditText
import android.widget.Button
import android.widget.Toast
import com.example.budgettrackerapplication.DatabaseHelper
import com.example.budgettrackerapplication.R

class AddCategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_category)
        // Calling the database helper class
        val db = DatabaseHelper(this)
        val etCategory = findViewById<EditText>(R.id.etCategoryName)
        val btnSave = findViewById<Button>(R.id.btnSaveCategory)
        // Save button

        btnSave.setOnClickListener {
            val category = etCategory.text.toString().trim()

            if (category.isEmpty()) {
                Toast.makeText(this, "Enter category name", Toast.LENGTH_SHORT).show()
            } else {
                db.addCategory(category)
                Toast.makeText(this, "Category saved", Toast.LENGTH_SHORT).show()
                etCategory.text.clear()
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}

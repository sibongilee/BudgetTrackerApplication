package com.example.budgettrackerapplication
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.budgettrackerapplication.DatabaseHelper
import com.example.budgettrackerapplication.R



class ViewExpensesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_expenses)
        // Calling the database helper class

        val db = DatabaseHelper(this)
        val tvPlaceholder = findViewById<TextView>(R.id.tvPlaceholder)// Initialize the TextView

        tvPlaceholder.text = db.getAllExpenses()
    }

}


    


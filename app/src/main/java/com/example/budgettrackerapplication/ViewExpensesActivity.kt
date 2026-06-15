package com.example.budgettrackerapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.budgettrackerapplication.DatabaseHelper
import com.example.budgettrackerapplication.R

class ViewExpensesActivity : AppCompatActivity() {

    private lateinit var db: DatabaseHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var tvTotal: TextView
    private lateinit var etStartDate: EditText
    private lateinit var etEndDate: EditText
    private lateinit var btnFilter: Button
    private lateinit var btnReset: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_expenses)

        // Setup toolbar with back button
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.title = "Back"

        db = DatabaseHelper(this)

        // Initialize views
        recyclerView = findViewById(R.id.recyclerViewExpenses)
        tvTotal = findViewById(R.id.tvTotalAmount)
        etStartDate = findViewById(R.id.etStartDate)
        etEndDate = findViewById(R.id.etEndDate)
        btnFilter = findViewById(R.id.btnFilter)
        btnReset = findViewById(R.id.btnReset)

        recyclerView.layoutManager = LinearLayoutManager(this)

        // Load all expenses initially
        loadExpenses(null, null)

        // Filter button click
        btnFilter.setOnClickListener {
            val startDate = etStartDate.text.toString().trim()
            val endDate = etEndDate.text.toString().trim()

            if (startDate.isEmpty() || endDate.isEmpty()) {
                Toast.makeText(this, "Please enter both start and end dates", Toast.LENGTH_SHORT).show()
            } else {
                loadExpenses(startDate, endDate)
            }
        }

        // Reset button click
        btnReset.setOnClickListener {
            etStartDate.text.clear()
            etEndDate.text.clear()
            loadExpenses(null, null)
        }
    }

    private fun loadExpenses(startDate: String?, endDate: String?) {
        val expenses = db.getFilteredExpenses(startDate, endDate)
        val total = db.getTotalExpenses(startDate, endDate)

        tvTotal.text = String.format("Total: R%.2f", total)

        if (expenses.isEmpty()) {
            // Show empty state
            val emptyAdapter = ExpenseAdapter(emptyList())
            recyclerView.adapter = emptyAdapter
            Toast.makeText(this, "No expenses found", Toast.LENGTH_SHORT).show()
        } else {
            val adapter = ExpenseAdapter(expenses)
            recyclerView.adapter = adapter
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

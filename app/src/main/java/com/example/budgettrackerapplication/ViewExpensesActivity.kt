package com.example.budgettrackerapplication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.budgettrackerapplication.data.BudgetRepository
import com.example.budgettrackerapplication.data.entity.Expense
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.*

class ViewExpensesActivity : AppCompatActivity() {
    
    private lateinit var repository: BudgetRepository
    private var userId: Long = 0
    private var username: String = ""
    private var expensesList: List<Expense> = emptyList()
    
    private lateinit var recyclerView: RecyclerView
    private lateinit var etStartDate: EditText
    private lateinit var etEndDate: EditText
    private lateinit var btnFilter: Button
    private lateinit var btnBack: Button
    private lateinit var tvTotalSpent: TextView
    private lateinit var adapter: ExpenseAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_expenses)
        
        // Retrieve data from Intent (passed from MainActivity)
        userId = intent.getLongExtra("USER_ID", 0)
        username = intent.getStringExtra("USERNAME") ?: "User"
        
        if (userId == 0L) {
            Toast.makeText(this, "Error: User not found", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        
        repository = BudgetRepository(this)
        
        initializeViews()
        setupRecyclerView()
        setupFilterButton()
        setupBackButton()
        loadAllExpenses()
        updateTotalSpent()
        
        // Display welcome message
        supportActionBar?.title = "$username's Expenses"
    }
    
    private fun initializeViews() {
        recyclerView = findViewById(R.id.recyclerViewExpenses)
        etStartDate = findViewById(R.id.etStartDate)
        etEndDate = findViewById(R.id.etEndDate)
        btnFilter = findViewById(R.id.btnFilter)
        btnBack = findViewById(R.id.btnBack)
        tvTotalSpent = findViewById(R.id.tvTotalSpent)
        
        // Set default date range (current month)
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        etStartDate.setText(dateFormat.format(calendar.time))
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        etEndDate.setText(dateFormat.format(calendar.time))
    }
    
    private fun setupRecyclerView() {
        adapter = ExpenseAdapter(emptyList()) { expense ->
            // EXPLICIT INTENT: Navigate to ExpenseDetailActivity when expense is clicked
            val intent = Intent(this, ExpenseDetailActivity::class.java).apply {
                putExtra("EXPENSE_ID", expense.id)
                putExtra("EXPENSE_AMOUNT", expense.amount)
                putExtra("EXPENSE_DESCRIPTION", expense.description)
                putExtra("EXPENSE_DATE", expense.date)
                putExtra("EXPENSE_START_TIME", expense.startTime)
                putExtra("EXPENSE_END_TIME", expense.endTime)
                putExtra("EXPENSE_PHOTO_PATH", expense.photoPath)
                putExtra("USER_ID", userId)
            }
            startActivityForResult(intent, 200)
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
    
    private fun setupFilterButton() {
        btnFilter.setOnClickListener {
            val startDate = etStartDate.text.toString().trim()
            val endDate = etEndDate.text.toString().trim()
            
            if (startDate.isNotEmpty() && endDate.isNotEmpty()) {
                loadExpensesByDateRange(startDate, endDate)
                updateTotalSpentWithRange(startDate, endDate)
            } else {
                Toast.makeText(this, "Please enter both dates", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun setupBackButton() {
        btnBack.setOnClickListener {
            // EXPLICIT INTENT: Go back to MainActivity with result
            val intent = Intent().apply {
                putExtra("RESULT_MESSAGE", "Returned from ViewExpenses")
            }
            setResult(RESULT_OK, intent)
            finish()
        }
    }
    
    private fun loadAllExpenses() {
        lifecycleScope.launch {
            repository.getAllExpenses(userId).collect { expenses ->
                expensesList = expenses
                adapter.updateData(expensesList)
            }
        }
    }
    
    private fun loadExpensesByDateRange(startDate: String, endDate: String) {
        lifecycleScope.launch {
            repository.getExpensesByDateRange(userId, startDate, endDate).collect { expenses ->
                expensesList = expenses
                adapter.updateData(expensesList)
                
                if (expenses.isEmpty()) {
                    Toast.makeText(this@ViewExpensesActivity, "No expenses found for this period", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    
    private fun updateTotalSpent() {
        lifecycleScope.launch {
            val startDate = etStartDate.text.toString().trim()
            val endDate = etEndDate.text.toString().trim()
            if (startDate.isNotEmpty() && endDate.isNotEmpty()) {
                updateTotalSpentWithRange(startDate, endDate)
            }
        }
    }
    
    private fun updateTotalSpentWithRange(startDate: String, endDate: String) {
        lifecycleScope.launch {
            val total = repository.getTotalSpent(userId, startDate, endDate)
            val format = NumberFormat.getCurrencyInstance(Locale("en", "ZA"))
            tvTotalSpent.text = "Total Spent: ${format.format(total)}"
        }
    }
    
    private fun showDeleteConfirmation(expense: Expense) {
        AlertDialog.Builder(this)
            .setTitle("Delete Expense")
            .setMessage("Delete '${expense.description}'?")
            .setPositiveButton("Delete") { _, _ ->
                lifecycleScope.launch {
                    repository.deleteExpense(expense)
                    Toast.makeText(this@ViewExpensesActivity, "Expense deleted", Toast.LENGTH_SHORT).show()
                    loadAllExpenses()
                    updateTotalSpent()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 200 && resultCode == RESULT_OK) {
            // Refresh when returning from detail activity
            loadAllExpenses()
            updateTotalSpent()
        }
    }
    
    inner class ExpenseAdapter(
        private var expenses: List<Expense>,
        private val onItemClick: (Expense) -> Unit
    ) : RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {
        
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(android.R.layout.simple_list_item_2, parent, false)
            return ExpenseViewHolder(view)
        }
        
        override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
            val expense = expenses[position]
            holder.bind(expense) {
                onItemClick(expense)
            }
        }
        
        override fun getItemCount(): Int = expenses.size
        
        fun updateData(newExpenses: List<Expense>) {
            expenses = newExpenses
            notifyDataSetChanged()
        }
        
        inner class ExpenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind(expense: Expense, onClick: () -> Unit) {
                val format = NumberFormat.getCurrencyInstance(Locale("en", "ZA"))
                val text1 = itemView.findViewById<TextView>(android.R.id.text1)
                val text2 = itemView.findViewById<TextView>(android.R.id.text2)
                
                text1.text = "${expense.description} - ${format.format(expense.amount)}"
                text2.text = "Date: ${expense.date} | Time: ${expense.startTime} - ${expense.endTime}"
                
                // Click to view details
                itemView.setOnClickListener { onClick() }
                
                // Long press to delete
                itemView.setOnLongClickListener {
                    AlertDialog.Builder(itemView.context)
                        .setTitle("Delete Expense")
                        .setMessage("Delete '${expense.description}'?")
                        .setPositiveButton("Delete") { _, _ ->
                            lifecycleScope.launch {
                                repository.deleteExpense(expense)
                                updateData(expenses.filter { it.id != expense.id })
                                updateTotalSpent()
                            }
                        }
                        .setNegativeButton("Cancel", null)
                        .show()
                    true
                }
            }
        }
    }
}

package com.example.budgettrackerapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, "BudgetDB", null, 2) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT,
                password TEXT
            )
        """)

        db.execSQL("""
            CREATE TABLE categories (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT
            )
        """)

        db.execSQL("""
            CREATE TABLE expenses (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                amount REAL,
                description TEXT,
                category TEXT,
                date TEXT,
                startTime TEXT,
                endTime TEXT,
                photoPath TEXT
            )
        """)

        db.execSQL("""
            CREATE TABLE budgets (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                minBudget INTEGER,
                maxBudget INTEGER
            )
        """)

        // Insert default categories
        val defaultCategories = listOf("Food", "Transport", "Shopping", "Entertainment", "Bills", "Healthcare", "Education")
        for (category in defaultCategories) {
            val values = ContentValues()
            values.put("name", category)
            db.insert("categories", null, values)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS users")
        db.execSQL("DROP TABLE IF EXISTS categories")
        db.execSQL("DROP TABLE IF EXISTS expenses")
        db.execSQL("DROP TABLE IF EXISTS budgets")
        onCreate(db)
    }

    fun registerUser(username: String, password: String): Boolean {
        val values = ContentValues()
        values.put("username", username)
        values.put("password", password)
        return writableDatabase.insert("users", null, values) != -1L
    }

    fun loginUser(username: String, password: String): Boolean {
        val cursor = readableDatabase.rawQuery(
            "SELECT * FROM users WHERE username=? AND password=?",
            arrayOf(username, password)
        )
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }

    fun addCategory(name: String): Boolean {
        val values = ContentValues()
        values.put("name", name)
        return writableDatabase.insert("categories", null, values) != -1L
    }

    fun getAllCategories(): MutableList<String> {
        val categories = mutableListOf<String>()
        val cursor = readableDatabase.rawQuery("SELECT name FROM categories", null)
        while (cursor.moveToNext()) {
            categories.add(cursor.getString(0))
        }
        cursor.close()
        return categories
    }

    fun addExpense(
        amount: Double,
        description: String,
        category: String,
        date: String,
        startTime: String,
        endTime: String,
        photoPath: String?
    ): Boolean {
        val values = ContentValues()
        values.put("amount", amount)
        values.put("description", description)
        values.put("category", category)
        values.put("date", date)
        values.put("startTime", startTime)
        values.put("endTime", endTime)
        values.put("photoPath", photoPath)
        return writableDatabase.insert("expenses", null, values) != -1L
    }

    fun getFilteredExpenses(startDate: String?, endDate: String?): List<Expense> {
        val expenses = mutableListOf<Expense>()
        val query = if (startDate != null && endDate != null) {
            "SELECT * FROM expenses WHERE date BETWEEN ? AND ? ORDER BY date DESC"
        } else {
            "SELECT * FROM expenses ORDER BY date DESC"
        }

        val cursor = if (startDate != null && endDate != null) {
            readableDatabase.rawQuery(query, arrayOf(startDate, endDate))
        } else {
            readableDatabase.rawQuery(query, null)
        }

        while (cursor.moveToNext()) {
            val expense = Expense(
                id = cursor.getInt(0),
                amount = cursor.getDouble(1),
                description = cursor.getString(2),
                category = cursor.getString(3),
                date = cursor.getString(4),
                startTime = cursor.getString(5),
                endTime = cursor.getString(6),
                photoPath = cursor.getString(7)
            )
            expenses.add(expense)
        }
        cursor.close()
        return expenses
    }

    fun getTotalExpenses(startDate: String?, endDate: String?): Double {
        val query = if (startDate != null && endDate != null) {
            "SELECT SUM(amount) FROM expenses WHERE date BETWEEN ? AND ?"
        } else {
            "SELECT SUM(amount) FROM expenses"
        }

        val cursor = if (startDate != null && endDate != null) {
            readableDatabase.rawQuery(query, arrayOf(startDate, endDate))
        } else {
            readableDatabase.rawQuery(query, null)
        }

        var total = 0.0
        if (cursor.moveToFirst() && cursor.getDouble(0) != null) {
            total = cursor.getDouble(0)
        }
        cursor.close()
        return total
    }

    fun saveBudget(minBudget: Int, maxBudget: Int): Boolean {
        // Clear old budgets first
        writableDatabase.execSQL("DELETE FROM budgets")
        val values = ContentValues()
        values.put("minBudget", minBudget)
        values.put("maxBudget", maxBudget)
        return writableDatabase.insert("budgets", null, values) != -1L
    }

    fun getLatestBudget(): Pair<Int, Int>? {
        val cursor = readableDatabase.rawQuery("SELECT minBudget, maxBudget FROM budgets ORDER BY id DESC LIMIT 1", null)
        if (cursor.moveToFirst()) {
            val min = cursor.getInt(0)
            val max = cursor.getInt(1)
            cursor.close()
            return Pair(min, max)
        }
        cursor.close()
        return null
    }
}
package com.example.budgettrackerapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// this class manages the database the local SQLite database
class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context,"BudgetDB", null, 1) {
    // override onCreate method
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

    fun getAllExpenses(): String {
        val cursor = readableDatabase.rawQuery("SELECT * FROM expenses", null)
        var result = ""

        while (cursor.moveToNext()) {
            result += "R${cursor.getDouble(1)} - ${cursor.getString(2)} - ${cursor.getString(3)} - ${cursor.getString(4)}\n\n"
        }

        cursor.close()
        return result.ifEmpty { "No expenses found" }
    }

    fun saveBudget(minBudget: Int, maxBudget: Int): Boolean {
        val values = ContentValues()
        values.put("minBudget", minBudget)
        values.put("maxBudget", maxBudget)
        return writableDatabase.insert("budgets", null, values) != -1L
    }
}
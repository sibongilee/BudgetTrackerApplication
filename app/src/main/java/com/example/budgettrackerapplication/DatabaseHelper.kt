package com.example.budgettrackerapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        const val DB_NAME = "expenses.db"
        const val DB_VERSION = 1

        const val TABLE_NAME = "expenses"
        const val COL_ID = "id"
        const val COL_AMOUNT = "amount"
        const val COL_CATEGORY = "category"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE $TABLE_NAME (" +
                "$COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COL_AMOUNT REAL, " +
                "$COL_CATEGORY TEXT)"

        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertExpense(amount: Double, category: String): Long {
        val db = writableDatabase
        val values = ContentValues()

        values.put(COL_AMOUNT, amount)
        values.put(COL_CATEGORY, category)

        return db.insert(TABLE_NAME, null, values)
    }

    fun getAllExpenses(): List<String> {
        val list = mutableListOf<String>()
        val db = readableDatabase

        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        if (cursor.moveToFirst()) {
            do {
                val amount = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_AMOUNT))
                val category = cursor.getString(cursor.getColumnIndexOrThrow(COL_CATEGORY))

                list.add("R$amount - $category")

            } while (cursor.moveToNext())
        }

        cursor.close()
        return list
    }

    fun updateExpense(id: Int, amount: Double): Int {
        val db = writableDatabase
        val values = ContentValues()

        values.put(COL_AMOUNT, amount)

        return db.update(
            TABLE_NAME,
            values,
            "$COL_ID=?",
            arrayOf(id.toString())
        )
    }

    fun deleteExpense(id: Int): Int {
        val db = writableDatabase

        return db.delete(
            TABLE_NAME,
            "$COL_ID=?",
            arrayOf(id.toString())
        )
    }
}
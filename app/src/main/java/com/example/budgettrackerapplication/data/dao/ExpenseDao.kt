package com.example.budgettrackerapplication.data.dao
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.budgettrackerapplication.data.entity.Expense


interface ExpenseDao {
    @Insert
    suspend fun insertExpense(expense: Expense)

    @Query("SELECT * FROM expenses WHERE userId = :userId")
    suspend fun getExpenses(userId: Long): List<Expense>
}
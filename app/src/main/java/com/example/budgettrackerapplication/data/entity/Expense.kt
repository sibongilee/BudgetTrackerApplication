package com.example.budgettrackerapplication.data.entity
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "expenses")
data class Expense(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val amount: Double,
    val description: String,
    val date: String,
    val startTime: String,
    val endTime: String,
    val categoryId: Long,
    val userId: Long,
    val photoPath: String? = null
)

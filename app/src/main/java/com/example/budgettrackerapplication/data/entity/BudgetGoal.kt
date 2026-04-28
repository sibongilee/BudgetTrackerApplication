package com.example.budgettrackerapplication.data.entity
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "budget_goals")
data class BudgetGoal(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: Long,
    val minBudget: Int,
    val maxBudget: Int
)

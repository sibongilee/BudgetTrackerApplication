package com.example.budgettrackerapplication.data.dao
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.budgettrackerapplication.data.entity.BudgetGoal



interface BudgetGoalDao {

    @Insert
    suspend fun insertGoal(goal: BudgetGoal)

    @Query("SELECT * FROM budget_goals WHERE userId = :userId")
    suspend fun getGoal(userId: Long): BudgetGoal?
}
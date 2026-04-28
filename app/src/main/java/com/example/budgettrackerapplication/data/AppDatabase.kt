package com.example.budgettrackerapplication.data
import androidx.room.Database
import com.example.budgettrackerapplication.data.dao.*
import com.example.budgettrackerapplication.data.entity.*

@Database(
    entities = [
        User::class,
        Category::class,
        Expense::class,
        BudgetGoal::class
    ],
    version = 1
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun categoryDao(): CategoryDao
    abstract fun expenseDao(): ExpenseDao
    abstract fun budgetGoalDao(): BudgetGoalDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "budget_db"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}

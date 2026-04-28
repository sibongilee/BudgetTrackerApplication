package com.example.budgettrackerapplication.data.dao
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.budgettrackerapplication.data.entity.Category
// category dao interface
interface CategoryDao {
    @Insert
    suspend fun insertCategory(category: Category)

    @Query("SELECT * FROM categories WHERE userId = :userId")
    suspend fun getCategories(userId: Long): List<Category>

}
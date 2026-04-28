package com.example.budgettrackerapplication.data.dao
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.budgettrackerapplication.data.entity.User

interface UserDao {

    @Insert
    suspend fun insertUser(user: User): Long

    @Query("SELECT * FROM users WHERE username = :username AND passwordHash = :password")
    suspend fun login(username: String, password: String): User?
}
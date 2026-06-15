package com.example.budgettrackerapplication

data class Expense(
    val id: Int,
    val amount: Double,
    val description: String,
    val category: String,
    val date: String,
    val startTime: String,
    val endTime: String,
    val photoPath: String?
)
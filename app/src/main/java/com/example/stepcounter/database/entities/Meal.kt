package com.example.stepcounter.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity
data class Meal(
    @PrimaryKey(autoGenerate = true)
    val mealId: Long,
    val productId: Long,
    val grams: Double,
    val date: Date,
)

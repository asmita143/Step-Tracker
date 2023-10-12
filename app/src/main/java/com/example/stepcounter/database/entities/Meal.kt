package com.example.stepcounter.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Meal_Today")
data class Meal (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val calories: Int
)
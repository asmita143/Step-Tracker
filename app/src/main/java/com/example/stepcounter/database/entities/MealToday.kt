package com.example.stepcounter.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Meal_Today")
data class MealToday (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val mass : Int,
    val calories: Int,
    val carbohydrate : Double,
    val salt : Double,
    val protein : Double,
    val fats : Double,
    val sugar : Double,
    val date: String,
)
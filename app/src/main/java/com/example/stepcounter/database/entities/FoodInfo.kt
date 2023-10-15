package com.example.stepcounter.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a single table named food_items in the database
 * Each property corresponds to a column
 */

@Entity(tableName = "food_items")
data class FoodInfo (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val calories: Int,
    val carbohydrate : Double,
    val salt : Double,
    val protein : Double,
    val fats : Double,
    val sugar : Double
)

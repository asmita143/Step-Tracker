package com.example.stepcounter.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food_items")
data class FoodInfo (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String?,
    val calories: Int
)

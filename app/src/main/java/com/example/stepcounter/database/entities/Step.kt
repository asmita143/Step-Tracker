package com.example.stepcounter.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

/**
 * Represents a single table in the database
 * Each property corresponds to a column
 */
@Entity
data class Step(
    @PrimaryKey(autoGenerate = true)
    val stepId: Long,
    val date: String,
    val stepAmount: Long,
//    val caloriesBurnt: Long
)
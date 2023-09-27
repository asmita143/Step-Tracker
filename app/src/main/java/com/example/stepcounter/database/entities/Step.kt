package com.example.stepcounter.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity
data class Step(
    @PrimaryKey(autoGenerate = true)
    val stepId: Long,
    val date: Date,
    val stepAmount: Long,
)
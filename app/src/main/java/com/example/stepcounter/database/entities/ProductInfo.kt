package com.example.stepcounter.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProductInfo(
    @PrimaryKey(autoGenerate = true)
    val productId: Long = 0,
    val barcode: String,
    val productName: String,
    val calories: Int,
    val fat: Double,
    val carbohydrate: Double,
    val sugars: Double,
    val protein: Double,
    val salt: Double
)

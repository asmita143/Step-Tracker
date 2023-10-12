package com.example.stepcounter.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProductInfo(
    @PrimaryKey(autoGenerate = true)
    val productId: Long,
    val barcode: String?,
    val productName: String,
    val calories: Double?,
    val fat: Double?,
    val saturatedFat: Double?,
    val carbs: Double?,
    val sugars: Double?,
    val proteins: Double?,
    val salt: Double?
)

package com.example.stepcounter.api

data class ProductInfo(
    val code: String?,
    val product: Product?,
    // If status 0 - product not found, 1 - product found
    val status: Int?,
)
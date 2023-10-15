package com.example.stepcounter.api

import com.google.gson.annotations.SerializedName

data class Product(
    val nutriments: NutrimentsBarcode?,
    @SerializedName("product_name") val productName: String?
)
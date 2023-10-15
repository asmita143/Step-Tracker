package com.example.stepcounter.api

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import java.lang.Exception

data class NutrimentsBarcode(
    @SerializedName("carbohydrates") val carbohydrates: String?,
    @SerializedName("energy-kcal") val calories: String?,
    @SerializedName("fat") val fat: String?,
    @SerializedName("saturated-fat") val saturatedFat: String?,
    @SerializedName("proteins") val proteins: String?,
    @SerializedName("sugars") val sugars: String?,
    @SerializedName("salt") val salt: String?,
)
package com.example.stepcounter.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class NutrimentsFineli(
    @SerialName("carbohydrate, available (g)") val carbohydrate: Double?,
    @SerialName("energy,calculated (kJ)") val energykJ: Double?,
    @SerialName("fat, total (g)") val fat: Double?,
    @SerialName("fatty acids, total saturated (g)") val fatSaturated: Double?,
    @SerialName("name") val productName: Double?,
    @SerialName("protein, total (g)") val protein: Double?,
    @SerialName("salt (mg)") val salt: Double?,
    @SerialName("sugars, total (g)") val sugars: Double?,
)
package com.example.stepcounter.api

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


//@Serializable
data class NutrimentsFineli(
    @SerializedName("carbohydrate, available (g)") val carbohydrate: String,
    @SerializedName("energy,calculated (kJ)") val energykJ: String,
    @SerializedName("fat, total (g)") val fat: String,
    @SerializedName("name") val productName: String,
    @SerializedName("protein, total (g)") val protein: String,
    @SerializedName("salt (mg)") val salt: String,
    @SerializedName("sugars, total (g)") val sugars: String,
)

//data class NutrimentsFineli(
//    val name: String?,
//)
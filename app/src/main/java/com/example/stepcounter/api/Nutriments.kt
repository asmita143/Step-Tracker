package com.example.stepcounter.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.lang.Exception

// Code based on https://www.youtube.com/watch?v=cAJChWxHvuc&list=PLzxawGXQRFsyVnwtFGoLlwdPr039g6WLe&index=2
@Serializable
data class Nutriments(
    @SerialName("carbohydrates") val carbohydrates: StringDouble = StringDouble(),
    @SerialName("carbohydrates_serving") val carbohydrates_serving: StringDouble = StringDouble(),
    @SerialName("energy-kcal_serving") val calories: StringDouble = StringDouble(),
    @SerialName("fat") val fat: StringDouble = StringDouble(),
    @SerialName("fat_serving") val fat_serving: StringDouble = StringDouble(),
    @SerialName("proteins") val proteins: StringDouble = StringDouble(),
    @SerialName("proteins_serving") val proteins_serving: StringDouble = StringDouble(),
    @SerialName("sugars") val sugars: StringDouble = StringDouble(),
    @SerialName("sugars_serving") val sugars_serving: StringDouble = StringDouble(),
    @SerialName("salt") val salt: StringDouble = StringDouble(),
    @SerialName("salt_serving") val salt_serving: StringDouble = StringDouble()
)

@Serializable
@JvmInline
value class StringDouble(private val jsonValue: String = "0.00") {
    val asDouble: Double
        get() = try {
            jsonValue.toDouble()
        } catch (e: Exception) {
            0.00
        }
}

fun StringDouble?.formatToGrams(): String = "%.1f".format(this?.asDouble ?: 0.00)
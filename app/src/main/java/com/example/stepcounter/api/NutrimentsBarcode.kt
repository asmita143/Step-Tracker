package com.example.stepcounter.api

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.lang.Exception

// Code based on https://www.youtube.com/watch?v=cAJChWxHvuc&list=PLzxawGXQRFsyVnwtFGoLlwdPr039g6WLe&index=2
//@Serializable
//data class NutrimentsBarcode(
//    @SerialName("carbohydrates") val carbohydrates: StringDouble = StringDouble(),
////    @SerialName("carbohydrates_serving") val carbohydrates_serving: StringDouble = StringDouble(),
//    @SerialName("energy-kcal") val calories: StringDouble = StringDouble(),
//    @SerialName("fat") val fat: StringDouble = StringDouble(),
//    @SerialName("saturated-fat") val saturatedFat: StringDouble = StringDouble(),
////    @SerialName("fat_serving") val fat_serving: StringDouble = StringDouble(),
//    @SerialName("proteins") val proteins: StringDouble = StringDouble(),
////    @SerialName("proteins_serving") val proteins_serving: StringDouble = StringDouble(),
//    @SerialName("sugars") val sugars: StringDouble = StringDouble(),
////    @SerialName("sugars_serving") val sugars_serving: StringDouble = StringDouble(),
//    @SerialName("salt") val salt: StringDouble = StringDouble(),
////    @SerialName("salt_serving") val salt_serving: StringDouble = StringDouble()
//)


data class NutrimentsBarcode(
    @SerializedName("carbohydrates") val carbohydrates: String?,
    @SerializedName("energy-kcal") val calories: String?,
    @SerializedName("fat") val fat: String?,
    @SerializedName("saturated-fat") val saturatedFat: String?,
    @SerializedName("proteins") val proteins: String?,
    @SerializedName("sugars") val sugars: String?,
    @SerializedName("salt") val salt: String?,
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
package com.example.stepcounter.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// Code based on https://www.youtube.com/watch?v=cAJChWxHvuc&list=PLzxawGXQRFsyVnwtFGoLlwdPr039g6WLe&index=2
object ProductApi {
    private const val URL_BARCODE = "https://us.openfoodfacts.org/api/v2/"
    private const val URL_FINELI = "https://users.metropolia.fi/~darjapo/"

    // API call for the barcode product retrieve
    interface BarcodeProduct {
        @GET("product/{barCode}")
        suspend fun getInfoByBarCode(
            @Path("barCode") barCode: String,
            @Query("fields") fields: String): ScannedProduct
    }

    private val retrofitBarcode = Retrofit.Builder()
        .baseUrl(URL_BARCODE)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val barcodeProduct: BarcodeProduct by lazy {
        retrofitBarcode.create(BarcodeProduct::class.java)
    }

    // API call for retrieving the Fineli database with products
    interface FineliProducts {
        @GET("jsonminifier.json")
        suspend fun getProducts(): List<NutrimentsFineli>
    }

    private val retrofitFineli = Retrofit.Builder()
        .baseUrl(URL_FINELI)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val fineliProduct: FineliProducts by lazy {
        retrofitFineli.create(FineliProducts::class.java)
    }
}
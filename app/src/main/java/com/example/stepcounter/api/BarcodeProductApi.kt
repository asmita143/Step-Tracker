package com.example.stepcounter.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

object BarcodeProductApi {
    private const val URL = "https://us.openfoodfacts.org/api/v2/"

    interface Service {
        @GET("product/{barCode}")
        suspend fun getInfoByBarCode(
            @Path("barCode") barCode: String,
            @Query("fields") fields: String): ProductInfo
    }


    private val retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: Service by lazy {
        retrofit.create(Service::class.java)
    }
}
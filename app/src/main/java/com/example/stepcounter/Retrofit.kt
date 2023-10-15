package com.example.stepcounter

import com.example.stepcounter.database.entities.FoodInfo
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

object API {
    const val URL = "https://users.metropolia.fi/~prabind/Extra/"

    interface Service {
        @GET("food_items.json")
        suspend fun getList(): List<FoodInfo>
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: Service by lazy {
        retrofit.create(Service::class.java)
    }
}

class WebServiceRepository() {
    private val call = API.service
    suspend fun getListOfFood() = call.getList()
}
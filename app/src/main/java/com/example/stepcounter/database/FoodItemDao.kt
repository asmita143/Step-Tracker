package com.example.stepcounter.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.stepcounter.database.entities.FoodInfo

@Dao
interface FoodItemDao {
    @Insert
    suspend fun insert(foodItem: List<FoodInfo>)

    @Query("SELECT * FROM food_items")
     fun getAllFoodItems(): LiveData<List<FoodInfo>>
}
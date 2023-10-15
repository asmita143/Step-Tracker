package com.example.stepcounter.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.stepcounter.database.entities.FoodInfo

/**
 * Provides access to read/write operation on [FoodInfo] food_items table
 * Used by the view models to format the query results for the use in UI
 */

@Dao
interface FoodItemDao {
    @Insert
    suspend fun insert(foodItem: List<FoodInfo>)

    @Query("SELECT * FROM food_items")
     fun getAllFoodItems(): LiveData<List<FoodInfo>>
}
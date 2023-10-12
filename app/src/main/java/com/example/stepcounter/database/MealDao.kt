package com.example.stepcounter.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.stepcounter.database.entities.FoodInfo
import com.example.stepcounter.database.entities.Meal


@Dao
interface MealDao {
    @Insert
    suspend fun insert(meal: Meal)

    @Query("select * from food_items")
    fun getMeal() : List<FoodInfo>

}
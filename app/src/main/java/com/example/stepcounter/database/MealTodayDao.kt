package com.example.stepcounter.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.stepcounter.database.entities.FoodInfo
import com.example.stepcounter.database.entities.MealToday


@Dao
interface MealTodayDao {
    @Insert
    suspend fun insert(mealToday: MealToday)

    @Query("select * from meal_today")
    fun getMeal() : List<MealToday>

    @Query("SELECT * FROM meal_today WHERE date = :date")
    fun getEatenTodayByDate(date: String): LiveData<List<MealToday>>

    @Query("SELECT * FROM meal_today WHERE id =:id ")
    fun getMealById(id : Int) : LiveData<List<MealToday>>

}
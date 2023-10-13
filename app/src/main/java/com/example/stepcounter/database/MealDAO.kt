package com.example.stepcounter.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.stepcounter.database.entities.Meal
import java.sql.Date


@Dao
interface MealDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMeal(meal: Meal)

    @Query("SELECT * FROM meal WHERE date = :date")
    fun getMealsByDate(date: Date) : LiveData<List<Meal>>
}
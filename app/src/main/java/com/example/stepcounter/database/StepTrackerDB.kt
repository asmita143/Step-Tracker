package com.example.stepcounter.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.stepcounter.database.entities.FoodInfo
import com.example.stepcounter.database.entities.Meal
import com.example.stepcounter.database.entities.Step

@Database(entities = [Step::class, FoodInfo::class, Meal::class], version = 2 , exportSchema = false)
@TypeConverters(Converters::class)
abstract class StepTrackerDB: RoomDatabase() {
    abstract val stepDAO: StepDAO
    abstract val foodItemDao: FoodItemDao
    abstract val mealDao : MealDao
    companion object {
        @Volatile
        private var INSTANCE: StepTrackerDB? = null
        fun getInstance(context: Context): StepTrackerDB {
            synchronized(this) {
                var instance = INSTANCE
                if(instance == null) {
                    instance = Room.databaseBuilder(context,
                        StepTrackerDB::class.java, "recipe_db")
                        .fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
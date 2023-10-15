package com.example.stepcounter.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.stepcounter.database.entities.MealToday
import com.example.stepcounter.App.Companion.appContext
import com.example.stepcounter.database.entities.FoodInfo
import com.example.stepcounter.database.entities.ProductInfo
import com.example.stepcounter.database.entities.Step

@Database(entities = [Step::class, ProductInfo::class, MealToday::class, FoodInfo:: class], version = 2 , exportSchema = false)
@TypeConverters(Converters::class)
abstract class StepTrackerDB: RoomDatabase() {
    abstract val stepDAO: StepDAO
    abstract val productDAO: ProductDAO
    abstract val mealTodayDao: MealTodayDao
    abstract val foodItemDao: FoodItemDao
    companion object {
        @Volatile
        private var INSTANCE: StepTrackerDB? = null
        fun getInstance(): StepTrackerDB {
            synchronized(this) {
                var instance = INSTANCE
                if(instance == null) {
                    instance = Room.databaseBuilder(appContext,
                        StepTrackerDB::class.java, "recipe_db")
                        .fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
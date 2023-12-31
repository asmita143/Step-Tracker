package com.example.stepcounter.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.stepcounter.database.entities.FoodInfo
import com.example.stepcounter.database.entities.MealToday
import com.example.stepcounter.database.entities.ProductInfo
import com.example.stepcounter.database.entities.Step
import kotlinx.coroutines.launch

class StepTrackerViewModel(application: Application) : AndroidViewModel(application) {
    private val db = StepTrackerDB.getInstance()

    //get all the steps  that are stored in the database
    fun getAllSteps(): LiveData<List<Step>> {
        return db.stepDAO.getAllSteps()
    }

    fun addSteps(step: Step) {
        viewModelScope.launch {
            db.stepDAO.addSteps(step)
        }
    }

    //get all meals that are eaten by the users in the current date
    fun getMealEatenTodayByDate(date: String): LiveData<List<MealToday>> {
        return db.mealTodayDao.getEatenTodayByDate(date)
    }

    fun getMealById(id : Int) : LiveData<List<MealToday>>{
        return db.mealTodayDao.getMealById(id)
    }

    //add meals that the user eats in the current date
     fun addMeal(mealToday : MealToday) {
         viewModelScope.launch {
             db.mealTodayDao.insert(mealToday)
         }
     }

    fun getAllProducts(): LiveData<List<ProductInfo>> {
        return db.productDAO.getAllProducts()
    }

    fun addProduct(productInfo: ProductInfo) {
        viewModelScope.launch {
            db.productDAO.addProduct(productInfo)
        }
    }

    fun getAllMeals() : LiveData<List<FoodInfo>>{
        return db.foodItemDao.getAllFoodItems()
    }

}
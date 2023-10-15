package com.example.stepcounter.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.stepcounter.WebServiceRepository
import com.example.stepcounter.database.entities.FoodInfo
import com.example.stepcounter.database.entities.MealToday
import com.example.stepcounter.database.entities.Meal
import com.example.stepcounter.database.entities.ProductInfo
import com.example.stepcounter.database.entities.Step
import kotlinx.coroutines.launch

class StepTrackerViewModel(application: Application) : AndroidViewModel(application) {
    private val db = StepTrackerDB.getInstance()

    fun getAllSteps(): LiveData<List<Step>> {
        return db.stepDAO.getAllSteps()
    }

    fun addSteps(step: Step) {
        viewModelScope.launch {
            db.stepDAO.addSteps(step)
        }
    }

    fun getMealEatenTodayByDate(date: String): LiveData<List<MealToday>> {
        return db.mealTodayDao.getEatenTodayByDate(date)
    }

    fun getMealById(id : Int) : LiveData<List<MealToday>>{
        return db.mealTodayDao.getMealById(id)
    }

     fun addMeal(mealToday : MealToday) {
         viewModelScope.launch {
             db.mealTodayDao.insert(mealToday)
         }
    fun getAllProducts(): LiveData<List<ProductInfo>> {
        return db.productDAO.getAllProducts()
    }

    fun getProductsByBarcode(barcode: String): LiveData<ProductInfo> {
        return db.productDAO.getScannedProductByBarcode(barcode)
    }

    fun getProductsByName(productName: String): LiveData<List<ProductInfo>> {
        return db.productDAO.getProductsByName(productName)
    }

    fun addProduct(productInfo: ProductInfo) {
        viewModelScope.launch {
            db.productDAO.addProduct(productInfo)
        }
    }

    //function to fetch food list from internet and save it to room database
    fun fetchAndSaveItems() {
        viewModelScope.launch {
            val response = repository.getListOfFood()
            db.foodItemDao.insert(response)
        }
    }

      fun getAllMeals() : LiveData<List<FoodInfo>>{
        return db.foodItemDao.getAllFoodItems()
    }

}
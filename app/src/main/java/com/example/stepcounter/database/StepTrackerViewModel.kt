package com.example.stepcounter.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.stepcounter.WebServiceRepository
import com.example.stepcounter.database.entities.FoodInfo
import com.example.stepcounter.database.entities.Meal
import com.example.stepcounter.database.entities.Step
import kotlinx.coroutines.launch
import java.sql.Date

class StepTrackerViewModel(application: Application) : AndroidViewModel(application) {
    private val db = StepTrackerDB.getInstance(application)
    private val repository: WebServiceRepository = WebServiceRepository()

    fun getAllSteps(): LiveData<List<Step>> {
        return db.stepDAO.getAllSteps()
    }

    fun getStepsByDate(date: Date): LiveData<Step> {
        return db.stepDAO.getStepsByDate(date)
    }

    fun addSteps(step: Step) {
        viewModelScope.launch {
            db.stepDAO.addSteps(step)
        }
    }

     fun addMeal(meal : Meal) {
         viewModelScope.launch {
             db.mealDao.insert(meal)
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
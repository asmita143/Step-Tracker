package com.example.stepcounter.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.stepcounter.api.WebServiceRepository
import com.example.stepcounter.database.entities.Meal
import com.example.stepcounter.database.entities.ProductInfo
import com.example.stepcounter.database.entities.Step
import kotlinx.coroutines.launch
import java.sql.Date
import kotlin.math.roundToInt

class StepTrackerViewModel(application: Application) : AndroidViewModel(application) {
    private val db = StepTrackerDB.getInstance(application)
    private val repository: WebServiceRepository = WebServiceRepository()

    fun getAllSteps(): LiveData<List<Step>> {
        return db.stepDAO.getAllSteps()
    }

    fun getStepsByDate(date: Date): LiveData<Step> {
        return db.stepDAO.getStepsByDate(date)
    }

    fun getStepsSevenDays(date: Date): LiveData<Step> {
        return db.stepDAO.getStepSevenDays(date)
    }

    fun addSteps(step: Step) {
        viewModelScope.launch {
            db.stepDAO.addSteps(step)
        }
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

    fun addProducts(productInfo: ProductInfo) {
        viewModelScope.launch {
            db.productDAO.addProduct(productInfo)
        }
    }

    fun addProductToInternalDb() {
        viewModelScope.launch {
            val response = repository.getFineliList()

            response.forEach {
                db.productDAO.addProduct(ProductInfo(
                    barcode = "NO BARCODE",
                    calories = it.energykJ.toDouble().div(4.184).roundToInt(),
                    carbohydrate = it.carbohydrate.toDoubleOrNull() ?: 0.00,
                    fat = it.fat.toDoubleOrNull() ?: 0.00,
                    sugars = it.sugars.toDoubleOrNull() ?: 0.00,
                    protein = it.protein.toDoubleOrNull() ?: 0.00,
                    salt = it.salt.toDoubleOrNull() ?: 0.00,
                    productName = it.productName
                ))
            }
        }
    }

    fun addMeal(meal: Meal) {
        viewModelScope.launch {
            db.mealDAO.addMeal(meal)
        }
    }

    fun getMealsByDate(date: Date) {
        viewModelScope.launch{
            db.mealDAO.getMealsByDate(date)
        }
    }
}
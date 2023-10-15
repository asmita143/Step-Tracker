package com.example.stepcounter.work

import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.stepcounter.api.ProductApi
import com.example.stepcounter.database.StepTrackerDB
import com.example.stepcounter.database.entities.ProductInfo
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

// Description of the work to do with work manager
class RefreshDataWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {
    companion object{
        const val WORK_NAME = "com.example.stepcounter.work.RefreshDataWorker"
    }

    @OptIn(DelicateCoroutinesApi::class)
    override suspend fun doWork(): Result {
        Log.d("WORKER", "Start of the work")
        return try {
            GlobalScope.launch(Dispatchers.IO, CoroutineStart.DEFAULT){
                val allProducts = ProductApi.fineliProduct.getProducts()
                Log.d("FINELI", allProducts.toString())

                allProducts.forEach {
                    StepTrackerDB.getInstance().productDAO.addProduct(ProductInfo(
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
            Log.d("Worker", "Success")
            return Result.success()
        } catch (throwable: Throwable) {
            Log.e(ContentValues.TAG, "Error")
            Result.failure()
        }
    }
}

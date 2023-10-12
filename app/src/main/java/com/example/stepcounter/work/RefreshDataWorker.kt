//package com.example.stepcounter.work
//
//import android.content.ContentValues
//import android.content.Context
//import android.util.Log
//import androidx.work.CoroutineWorker
//import androidx.work.WorkerParameters
//import com.example.stepcounter.App.Companion.appContext
//import com.example.stepcounter.api.ProductApi
//import com.example.stepcounter.database.StepTrackerDB
//import com.example.stepcounter.database.entities.ProductInfo
//import kotlinx.coroutines.CoroutineStart
//import kotlinx.coroutines.DelicateCoroutinesApi
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.GlobalScope
//import kotlinx.coroutines.launch
//
//class RefreshDataWorker(appContext: Context, params: WorkerParameters) :
//    CoroutineWorker(appContext, params) {
//    companion object{
//        const val WORK_NAME = "com.example.stepcounter.work.RefreshDataWorker"
//    }
//
//    @OptIn(DelicateCoroutinesApi::class)
//    override suspend fun doWork(): Result {
//        Log.d("WORKER", "Start of the work")
//        return try {
//            GlobalScope.launch(Dispatchers.IO, CoroutineStart.DEFAULT){
//                val allProducts = ProductApi.fineliProduct.getProducts()
//                Log.d("FINELI", allProducts.toString())
//
//                allProducts.forEach{
//                    StepTrackerDB.getInstance(appContext).productDAO.addProduct(ProductInfo(
//                        productName = it.productName ?: "No name",
//                        barcode = "NO BARCODE",
//                        // Div function to convert kJ into kCal
//                        calories = it.energykJ?.div(4.184),
//                        fat = it.fat,
//                        carbohydrate = it.carbohydrate,
//                        sugars = it.sugars,
//                        protein = it.protein,
//                        salt = it.salt
//                    ))
//                }
//            }
//            Log.d("Worker", "Success")
//            return Result.success()
//        } catch (throwable: Throwable) {
//            Log.e(ContentValues.TAG, "Error")
//            Result.failure()
//        }
//    }
//}

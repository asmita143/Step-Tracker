package com.example.stepcounter.barcodeScanner

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stepcounter.App
import com.example.stepcounter.R
import com.example.stepcounter.api.ProductApi
import com.example.stepcounter.api.ScannedProduct
import com.example.stepcounter.database.StepTrackerDB
import com.example.stepcounter.database.entities.ProductInfo
import kotlinx.coroutines.launch

class BarcodeViewModel : ViewModel() {
    private val _liveData = MutableLiveData("Initial Value")
    val liveData: LiveData<String> = _liveData
    private val _product = MutableLiveData<ScannedProduct>(null)
    val product: LiveData<ScannedProduct> = _product
    private val _isScanned = MutableLiveData<Boolean>()
    val isScanned: LiveData<Boolean> = _isScanned

    private val db = StepTrackerDB.getInstance()

    // Get the product info from API
    suspend fun getScannedProduct(barcode: String) {
        viewModelScope.launch {
            try {
                _product.value = barcode.let {
                    ProductApi.barcodeProduct.getInfoByBarCode(
                        barCode = it,
                        fields = "product_name,nutriments"
                    )
                }
                product.value?.let { addScannedProduct(it) }
                itemScanned()
                Log.d("Product found", product.value.toString())
                Log.d("Scanned", isScanned.value.toString())
            } catch (e: Exception) {
                Toast.makeText(App.appContext, R.string.product_not_found, Toast.LENGTH_SHORT).show()
                Log.d("PRDCT", "No product found")
            }
        }
    }

    private suspend fun addScannedProduct(scannedProduct: ScannedProduct) {
        viewModelScope.launch {
            try {
                // Checks whether the product is already in internal db
                if (scannedProduct.code?.let { db.productDAO.productIsInDb(it) } == false) {
                // Adds the product in internal db
                    db.productDAO.addProduct(
                        ProductInfo(
                            barcode = scannedProduct.code,
                            calories = scannedProduct.product?.nutriments?.calories?.toIntOrNull() ?: 0,
                            carbohydrate = scannedProduct.product?.nutriments?.carbohydrates?.toDoubleOrNull()
                                ?: 0.0,
                            fat = scannedProduct.product?.nutriments?.fat?.toDoubleOrNull() ?: 0.0,
                            sugars = scannedProduct.product?.nutriments?.sugars?.toDoubleOrNull() ?: 0.0,
                            protein = scannedProduct.product?.nutriments?.proteins?.toDoubleOrNull() ?: 0.0,
                            salt = scannedProduct.product?.nutriments?.salt?.toDoubleOrNull() ?: 0.0,
                            productName = scannedProduct.product?.productName ?: "No name"))
                    Toast.makeText(App.appContext, R.string.product_found, Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(App.appContext, R.string.product_already_added, Toast.LENGTH_SHORT).show()
                Log.d("PRDCT", "Unable to add the product")
            }
        }
    }

    // Update the barcode value
    fun updateData(newData: String) {
        _liveData.value = newData
        Log.d("LIVE DATA in update", newData)
    }

    private fun itemScanned() {
        _isScanned.value = true
    }
}
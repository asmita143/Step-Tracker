package com.example.stepcounter.barcodeScanner

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.stepcounter.App.Companion.appContext
import com.example.stepcounter.R
import com.example.stepcounter.api.ProductApi
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking

// Code based on https://developers.google.com/ml-kit/vision/barcode-scanning/code-scanner
// and https://www.youtube.com/watch?v=keiuMUX1k0k

class BarcodeScanner {

    // Scans all possible barcode formats, can be reduced to only those needed in our application
    private val options = GmsBarcodeScannerOptions.Builder()
        .setBarcodeFormats(
            Barcode.FORMAT_ALL_FORMATS
        )
        .enableAutoZoom()
        .build()

    // Get an instance of barcode scanner and save it into a variable
    private val scanner = GmsBarcodeScanning.getClient(appContext, options)
//    val barCodeResults = mutableStateOf<String?>(null)
    val barCodeResults = MutableStateFlow<String?>(null)
    val barCodeResult = MutableLiveData<String?>(null)


    suspend fun startScan() {
        try {
            val result = scanner.startScan().result
            barCodeResults.value = result.rawValue
            barCodeResults.value?.let { Log.d("ScannerDBG", it) }
        } catch (e: Exception) {
            Log.d("ScannerDBG", "scan error: $e")
        }
    }

    suspend fun startScanning() {
        scanner.startScan()
            .addOnSuccessListener { barcode ->
                // Task completed successfully
                val result = barcode.rawValue
                Log.d("SCANNER", "initial value: $result")
                runBlocking {
                    try {
                        val productInfo = ProductApi.barcodeProduct.getInfoByBarCode(
                            barCode = result.toString(),
                            fields = "product_name,nutriments"
                        )

                        Toast.makeText(appContext, R.string.product_scan_success, Toast.LENGTH_SHORT).show()
                        Log.d("PRDCT", productInfo.toString())
                    } catch (e: Exception) {
                        Toast.makeText(appContext, R.string.product_scan_error, Toast.LENGTH_SHORT).show()
                        Log.d("PRDCT", "No product found")
                    }

                }

                when (barcode.valueType) {
                    Barcode.TYPE_URL -> {
                        Log.d("SCANNER", "initiateScanner: ${barcode.valueType}")
                    }

                    else -> {
                        Log.d("SCANNER", "initiateScanner: ${barcode.valueType}")
                    }
                }

                Log.d("SCANNER", "initiateScanner: Display value ${barcode.displayValue}")
                Log.d("SCANNER", "initiateScanner: Display value ${barcode.format}")
                barCodeResult.value = barcode.displayValue
                barCodeResult.value?.let { Log.d("wtf", it) }
            }
            .addOnCanceledListener {
                // Task canceled
            }
            .addOnFailureListener { e ->
                Log.d("SCANNER", "Failed to scan")
            }
    }
}
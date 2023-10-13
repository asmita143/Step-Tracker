package com.example.stepcounter.barcodeScanner

import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stepcounter.App
import com.example.stepcounter.App.Companion.appContext
import com.example.stepcounter.R
import com.example.stepcounter.api.Product
import com.example.stepcounter.api.ProductApi
import com.example.stepcounter.api.ProductInfo
import com.example.stepcounter.database.StepTrackerViewModel
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
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
//    val barCodeResults = MutableStateFlow<String?>(null)
//    val barCodeResult = MutableLiveData<String?>(null)



//    suspend fun startScan() {
//        try {
//            val result = scanner.startScan().result
//            barCodeResults.value = result.rawValue
//            barCodeResults.value?.let { Log.d("ScannerDBG", it) }
//        } catch (e: Exception) {
//            Log.d("ScannerDBG", "scan error: $e")
//        }
//    }

//    runBlocking {
//        try {
//            val product = result?.let {
//                ProductApi.barcodeProduct.getInfoByBarCode(
//                    barCode = it,
//                    fields = "product_name,nutriments"
//                )
//            }
//        } catch (e: Exception) {
//            Toast.makeText(App.appContext, R.string.product_scan_error, Toast.LENGTH_SHORT).show()
//            Log.d("PRDCT", "No product found")
//        }
//
//    }

    suspend fun startScanning(viewModel: BarcodeViewModel = BarcodeViewModel()) {
        scanner.startScan()
            .addOnSuccessListener { barcode ->
                // Task completed successfully
                val result = barcode.rawValue



                Log.d("SCANNER", "initial value: $result")
                if (result != null) {
                    viewModel.updateData(result)
                    Log.d("LIVE DATA in scan", result)
                }
                Toast.makeText(appContext, R.string.product_scan_success, Toast.LENGTH_SHORT).show()
//                Log.d("PRDCT", product.toString())

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
            }
            .addOnCanceledListener {
                // Task canceled
            }
            .addOnFailureListener { e ->
                Log.d("SCANNER", "Failed to scan")
            }
    }
}

class BarcodeViewModel : ViewModel() {
    private val _liveData = MutableLiveData("Initial Value")
    val liveData: LiveData<String> = _liveData

    fun updateData(newData: String) {
        _liveData.value = newData
        Log.d("LIVE DATA in update", newData)
    }
//    fun updateData(newData: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//        liveData.postValue(newData) // This is safe from a background thread
//    }
////        _liveData.value = newData
////        Log.d("LIVE DATA in update", newData)
//    }
}
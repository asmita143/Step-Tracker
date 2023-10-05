package com.example.stepcounter.barcodeScanner

import android.content.Context
import android.util.Log
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import kotlinx.coroutines.flow.MutableStateFlow

// Code based on https://developers.google.com/ml-kit/vision/barcode-scanning/code-scanner
// and https://www.youtube.com/watch?v=keiuMUX1k0k

class BarcodeScanner ( appContext: Context) {

    // Scans all possible barcode formats, can be reduced to only those needed in our application
    private val options = GmsBarcodeScannerOptions.Builder()
        .setBarcodeFormats(
            Barcode.FORMAT_ALL_FORMATS
        )
        .enableAutoZoom()
        .build()

    // Get an instance of barcode scanner and save it into a variable
    private val scanner = GmsBarcodeScanning.getClient(appContext, options)
    val barCodeResults = MutableStateFlow<String?>(null)

    suspend fun startScan() {
        try {
            val result = scanner.startScan().result
            barCodeResults.value = result.rawValue
            barCodeResults.value?.let { Log.d("ScannerDBG", it) }
        } catch (e: Exception) {
            Log.d("ScannerDBG", "scan error: $e")
        }
    }
}
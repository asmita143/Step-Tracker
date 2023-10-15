package com.example.stepcounter.barcodeScanner

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.rememberCoroutineScope
import com.example.stepcounter.App.Companion.appContext
import com.example.stepcounter.R
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// Code based on https://developers.google.com/ml-kit/vision/barcode-scanning/code-scanner
// and https://www.youtube.com/watch?v=keiuMUX1k0k

// Barcode scanner class
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

    // Start the scanning
    fun startScanning(barcodeViewModel: BarcodeViewModel) {
        scanner.startScan()
            .addOnSuccessListener { barcode ->
                // Task completed successfully
                val result = barcode.rawValue

                Log.d("SCANNER", "initial value: $result")

                if (result != null) {
                    barcodeViewModel.updateData(result)
                    Log.d("LIVE DATA in scan", result)
                }

                CoroutineScope(Dispatchers.IO).launch { barcode?.let {
                    if (result != null) {
                        barcodeViewModel.getScannedProduct(result)
                    }
                } }
            }
            .addOnCanceledListener {
                Toast.makeText(appContext, R.string.scan_cancelled, Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(appContext, R.string.scan_failure, Toast.LENGTH_SHORT).show()
                Log.d("SCANNER", "Failed to scan")
            }
    }
}
package com.example.stepcounter
//
//import android.content.Context
//import android.hardware.Sensor
//import android.hardware.SensorEvent
//import android.hardware.SensorEventListener
//import android.hardware.SensorManager
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.animation.core.animateFloatAsState
//import androidx.compose.animation.core.tween
//import androidx.compose.foundation.Canvas
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.ProgressIndicatorDefaults
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Switch
//import androidx.compose.material3.SwitchDefaults
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.State
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.shadow
//import androidx.compose.ui.geometry.Offset
//import androidx.compose.ui.geometry.Size
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.RectangleShape
//import androidx.compose.ui.graphics.StrokeCap
//import androidx.compose.ui.graphics.drawscope.Stroke
//import androidx.compose.ui.platform.LocalConfiguration
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.Dp
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.compose.ui.viewinterop.AndroidView
//import com.example.stepcounter.foodScreen.CaloriesPerProduct
//import com.example.stepcounter.ui.theme.StepCounterTheme
//import com.example.stepcounter.ui.theme.md_theme_light_background
//import com.example.stepcounter.ui.theme.md_theme_light_onSecondary
//import com.example.stepcounter.ui.theme.md_theme_light_tertiary
//import com.github.mikephil.charting.animation.Easing
//import com.github.mikephil.charting.charts.BarChart
//import com.github.mikephil.charting.components.XAxis
//import com.github.mikephil.charting.data.BarData
//import com.github.mikephil.charting.data.BarDataSet
//import com.github.mikephil.charting.data.BarEntry
//import com.github.mikephil.charting.formatter.ValueFormatter
//
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            StepCounterTheme {
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                   CaloriesPerProduct()
//                }
//            }
//        }
//    }
//}
// /*stepCounter.initialize(this)
//    }
//
//    override fun onResume() {
//        super.onResume()
//        stepCounter.startListening()
//    }
//
//    override fun onPause() {
//        super.onPause()
//        stepCounter.stopListening()
//    }
//}*/
//
//class StepCounter {
//    private var sensorManager: SensorManager? = null
//    private var stepCounterSensor: Sensor? = null
//    private var listener: SensorEventListener? = null
//
//    fun initialize(context: Context) {
//        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
//        stepCounterSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
//
//        listener = object : SensorEventListener {
//            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
//
//            }
//
//            override fun onSensorChanged(event: SensorEvent?) {
//                if (event?.sensor?.type == Sensor.TYPE_STEP_COUNTER) {
//                    val steps = event.values[0].toInt()
//
//                }
//            }
//        }
//    }
//
//    fun startListening() {
//        sensorManager?.registerListener(listener, stepCounterSensor, SensorManager.SENSOR_DELAY_NORMAL)
//    }
//
//    fun stopListening() {
//        sensorManager?.unregisterListener(listener)
//    }
//}
//
//
//    // short overview
////Initialized the step counter in onCreate of MainActivity.
//// Started listening to the step counter in onResume() and stopped in onPause().
//// then we have a callback function onAccuracyChange() incase the sensor accurcy changed.
//// please add whatever you think is relevant.
//
//@Preview(showBackground = true)
//@Composable
//fun Home(
//    size: Dp = 150.dp,
//    target: Int = 13100,
//    foregroundIndicatorColor: Color = Color(0xFF35898f),
//    shadowColor: Color = Color.LightGray,
//    indicatorThickness: Dp = 8.dp,
//    stepsTaken: Float = 6550f,
//    animationDuration: Int = 1000,
//    ) {
//    val configuration = LocalConfiguration.current
//    val screenHeight = configuration.screenHeightDp.dp
//    val screenWidth = configuration.screenWidthDp.dp
//    var progress by remember { mutableStateOf(0.1f) }
//    var checked by remember { mutableStateOf(true) }
//    val animatedProgress = animateFloatAsState(
//        targetValue = progress,
//        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec, label = ""
//    ).value
//    var stepsTakenRemember by remember {
//        mutableStateOf(-1f)
//    }
//
//    // This is to animate the foreground indicator
//    val stepsTakenAnimate = animateFloatAsState(
//        targetValue = stepsTakenRemember,
//        animationSpec = tween(
//            durationMillis = animationDuration
//        ), label = ""
//    )
//
//    //Hard coded values for testing the BarGraph
//    val entries = ArrayList<BarEntry>()
//
//    entries.add(BarEntry(0F,3000F))
//    entries.add(BarEntry(1F, 4050F))
//    entries.add(BarEntry(2F, 4500F))
//    entries.add(BarEntry(3F, 6110F))
//    entries.add(BarEntry(4F, 5500F))
//    entries.add(BarEntry(5F, 3550F))
//    entries.add(BarEntry(6F, 3300F))
//
//    // This is to start the animation when the activity is opened
//    LaunchedEffect(Unit) {
//        stepsTakenRemember = stepsTaken
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(20.dp),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    )
//    {
//
//        Card(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(screenHeight / 2)
//                .padding(20.dp)
//                .shadow(
//                    elevation = 20.dp,
//                    shape = RectangleShape,
//                    spotColor = Color.Black
//                ),
//            colors = CardDefaults.cardColors(
//                containerColor = Color.White,
//            ),
//        ) {
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(20.dp),
//                verticalArrangement = Arrangement.SpaceEvenly,
//                horizontalAlignment = Alignment.CenterHorizontally
//            )
//            {
//                Box(
//                    modifier = Modifier
//                        .size(size)
//                        .background(Color.White),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Canvas(
//                        modifier = Modifier
//                            .size(size)
//                    ) {
//                        // For shadow
//                        drawCircle(
//                            brush = Brush.radialGradient(
//                                colors = listOf(shadowColor, Color.White),
//                                center = Offset(x = this.size.width / 2, y = this.size.height / 2),
//                                radius = this.size.height / 2
//                            ),
//                            radius = this.size.height / 2,
//                            center = Offset(x = this.size.width / 2, y = this.size.height / 2)
//                        )
//
//                        // This is the white circle that appears on the top of the shadow circle
//                        drawCircle(
//                            color = md_theme_light_background,
//                            radius = (size / 2 - indicatorThickness).toPx(),
//                            center = Offset(x = this.size.width / 2, y = this.size.height / 2)
//                        )
//
//                        // Convert the stepsTaken to angle
//                        val sweepAngle = (stepsTakenAnimate.value) * 360 / target
//
//                        // Foreground indicator
//                        drawArc(
//                            color = md_theme_light_tertiary,
//                            startAngle = -90f,
//                            sweepAngle = sweepAngle,
//                            useCenter = false,
//                            style = Stroke(
//                                width = indicatorThickness.toPx(),
//                                cap = StrokeCap.Round
//                            ),
//                            size = Size(
//                                width = (size - indicatorThickness).toPx(),
//                                height = (size - indicatorThickness).toPx()
//                            ),
//                            topLeft = Offset(
//                                x = (indicatorThickness / 2).toPx(),
//                                y = (indicatorThickness / 2).toPx()
//                            )
//                        )
//                    }
//
//                    // Display the data usage value
//                    DisplayText(stepsTakenAnimate)
//                }
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .background(md_theme_light_onSecondary)
//                ) {
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(15.dp),
//                        horizontalArrangement = Arrangement.SpaceBetween,
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//
//                        Column(
//                            modifier = Modifier
//                                .padding(10.dp),
//                        ) {
//                            Text(text = "Remaining", fontSize = 14.sp)
//                            Text(text = (target - stepsTaken.toInt()).toString(), fontSize = 18.sp)
//                        }
//                        Column(
//                            modifier = Modifier
//                                .padding(10.dp)
//                        ) {
//                            Text(text = "Target", fontSize = 14.sp)
//                            Text(text = target.toString(), fontSize = 18.sp)
//                        }
//                    }
//                }
//            }
//        }
//
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.End,
//            verticalAlignment = Alignment.CenterVertically,
//        ) {
//            Text(text = "Day", Modifier.padding(end = 10.dp))
//            Switch(
//                checked = checked,
//                onCheckedChange = {
//                    checked = it
//                },
//                colors = SwitchDefaults.colors(
//                    checkedTrackColor = Color.Gray,
//                ),
//            )
//            Text(text = "Week", Modifier.padding(start = 10.dp))
//        }
//
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(screenHeight / 2)
//                .padding(20.dp)
//                .background(MaterialTheme.colorScheme.primary)
//        ) {
//            AndroidView(
//                modifier = Modifier.fillMaxSize(),
//                factory = { context: Context ->
//                    val view = BarChart(context)
//                    view.legend.isEnabled = false
//                    val data = BarData(BarDataSet( entries, "Label 1"))
//                    view.data = data
//                    view
//                },
//                update = { view ->
//                    // Update the view
//                    val xAxisFormatter: ValueFormatter = DayAxisValueFormatter()
//                    var dataSet: BarDataSet = BarDataSet( entries, "Label 1")
//                    val data = BarData(dataSet)
//                    view.description.isEnabled = false
//                    view.data = data
//                    view.axisRight.isEnabled = false
//                    view.axisLeft.isEnabled = false
//                    view.xAxis.position = XAxis.XAxisPosition.BOTTOM
//                    view.xAxis.setDrawGridLines(false)
//                    view.animateXY(2000, 3000, Easing.EaseInSine)
//                    view.setTouchEnabled(true)
//                    view.xAxis.valueFormatter = xAxisFormatter
//                    view.invalidate()
//                }
//            )
//        }
//
//    }
//}
//
////Class to convert the float values to String for X-axis Label in Bar graph
//class DayAxisValueFormatter() : ValueFormatter() {
//    private val labels = arrayOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
//    override fun getFormattedValue(value: Float): String {
//        var label = ""
//        when (value) {
//            0.0F -> label = labels[0]
//            1.0F -> label = labels[1]
//            2.0F -> label = labels[2]
//            3.0F -> label = labels[3]
//            4.0F -> label = labels[4]
//            5.0F -> label = labels[5]
//            6.0F -> label = labels[6]
//        }
//        return label
//    }
//}
//
//@Composable
//private fun DisplayText(
//    animateNumber: State<Float>
//) {
//    Column(
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        // Text that shows the number inside the circle
//        Text(
//            text = (animateNumber.value).toInt().toString(),
//            fontSize = 18.sp,
//        )
//        Spacer(modifier = Modifier.height(2.dp))
//        Text(
//            text = "Steps",
//            fontSize = 16.sp,
//        )
//    }
//}
//
//

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.stepcounter.api.BarcodeProductApi
import com.example.stepcounter.api.ProductInfo
import com.example.stepcounter.barcodeScanner.BarcodeScanner
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        val gmsScannerOptions = configureScannerOption()
//        val instance = getBarcodeScannerInstance(gmsScannerOptions)
//        setContent {
//            ScanBarcode { initiateScanner(instance) }
//        }
//    }
//
//    private fun configureScannerOption(): GmsBarcodeScannerOptions {
//        return GmsBarcodeScannerOptions.Builder()
//            .setBarcodeFormats(
//                Barcode.FORMAT_ALL_FORMATS
//            )
//            .build()
//    }
//
//    private fun getBarcodeScannerInstance(gmsBarcodeScannerOptions: GmsBarcodeScannerOptions): GmsBarcodeScanner {
//        return GmsBarcodeScanning.getClient(this, gmsBarcodeScannerOptions)
//// Or with a configured options
//// val scanner = GmsBarcodeScanning.getClient(this, options)
//    }
//
//    private fun initiateScanner(gmsBarcodeScanner: GmsBarcodeScanner) {
//        gmsBarcodeScanner.startScan()
//            .addOnSuccessListener { barcode ->
//                // Task completed successfully
//                val result = barcode.rawValue
//                Log.d(TAG, "initiateScanner: $result")
//
//                when (barcode.valueType) {
//                    Barcode.TYPE_URL -> {
//                        Log.d(TAG, "initiateScanner: ${barcode.valueType}")
//                    }
//
//                    else -> {
//                        Log.d(TAG, "initiateScanner: ${barcode.valueType}")
//                    }
//                }
//
//                Log.d(TAG, "initiateScanner: Display value ${barcode.displayValue}")
//                Log.d(TAG, "initiateScanner: Display value ${barcode.format}")
//            }
//            .addOnCanceledListener {
//                // Task canceled
//            }
//            .addOnFailureListener { e ->
//                // Task failed with an exception
//            }
//    }
//
//    companion object {
//        private const val TAG = "MyActivity"
//    }
//}
//
//@Composable
//private fun ScanBarcode(
//    onClick: () -> Unit = {}
//) {
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//
//        Button(
//            modifier = Modifier
//                .fillMaxWidth(.85f),
//            colors = ButtonDefaults.buttonColors(
//                containerColor = Color.Black
//            ),
//            onClick = {
//                onClick()
//            }) {
//            Text(
//                text = "Scan Barcode",
//                textAlign = TextAlign.Center,
//                style = MaterialTheme.typography.displayMedium,
//                color = Color.Gray,
//                //style = TextStyle(fontWeight = FontWeight.Bold)
//            )
//        }
//
//        Spacer(modifier = Modifier.height(20.dp))
//
//    }
//}
//
//@Preview
//@Composable
//fun PreviewScanBarcode() {
//    ScanBarcode  {}
//}

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
//            val barcodeResults =
//                BarcodeScanner().barCodeResults.collectAsStateWithLifecycle()
            val barcodeResults by BarcodeScanner().barCodeResult.observeAsState(null)

            ScanBarcode(
//                { BarcodeScanner().startScan() },
                { BarcodeScanner().startScanning() },
                barcodeResults
            )

            Log.d("wtf in mainactivity", "$barcodeResults")
        }
    }
}

@Composable
private fun ScanBarcode(
    onScanBarcode: suspend () -> Unit,
    barcodeValue: String?
) {
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Button(
            modifier = Modifier
                .fillMaxWidth(.85f),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black
            ),
            onClick = {
                scope.launch {
                    onScanBarcode()
                }
            }) {
            Text(
                text = "Scan Barcode",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displayMedium,
                color = Color.Gray,
                //style = TextStyle(fontWeight = FontWeight.Bold)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = barcodeValue ?: "0000000000",
            style = MaterialTheme.typography.displayMedium
        )

        if (barcodeValue != null) {
            Log.d("wtf in main", barcodeValue)
        }

    }
}

@Preview
@Composable
fun PreviewScanBarcode() {

            ScanBarcode({}, null)
}
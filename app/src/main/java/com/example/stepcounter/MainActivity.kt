package com.example.stepcounter

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.stepcounter.Homepage.StatisticsGraph
import com.example.stepcounter.Homepage.StepInfoTop
import com.example.stepcounter.ui.theme.StepCounterTheme
import com.example.stepcounter.ui.theme.md_theme_light_background
import com.example.stepcounter.ui.theme.md_theme_light_onSecondary
import com.example.stepcounter.ui.theme.md_theme_light_tertiary
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter

class MainActivity : ComponentActivity() {
    val stepCounter = StepCounter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            StepCounterTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Home(navController)
                }
            }
        }
        stepCounter.initialize(this)
        stepCounter.startListening()
    }

    override fun onResume() {
        super.onResume()
        stepCounter.startListening()
    }

    override fun onPause() {
        super.onPause()
        stepCounter.stopListening()
    }
}

class StepCounter {
    private var sensorManager: SensorManager? = null
    private var stepCounterSensor: Sensor? = null
    private var listener: SensorEventListener? = null

    fun initialize(context: Context) {
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        stepCounterSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        listener = object : SensorEventListener {
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                
            }

            override fun onSensorChanged(event: SensorEvent?) {
                if (event?.sensor?.type == Sensor.TYPE_STEP_COUNTER) {
                    val steps = event.values[0].toInt()
                    Log.d("MSG", "$steps")
                }
            }
        }
    }

    fun startListening() {
        sensorManager?.registerListener(listener, stepCounterSensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    fun stopListening() {
        sensorManager?.unregisterListener(listener)
    }
}

      
    // short overview   
//Initialized the step counter in onCreate of MainActivity.
// Started listening to the step counter in onResume() and stopped in onPause().
// then we have a callback function onAccuracyChange() incase the sensor accurcy changed. 
// please add whatever you think is relevant.

@Composable
fun Home(navController: NavHostController) {
    var checked by remember { mutableStateOf(true) }
    val bargraph = StatisticsGraph()
    val stepInfo = StepInfoTop()


    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        stepInfo.StepsInfoSection()

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = "Day", Modifier.padding(end = 10.dp))
            Switch(
                checked = checked,
                onCheckedChange = {
                    checked = it
                },
                colors = SwitchDefaults.colors(
                    checkedTrackColor = Color.Gray,
                ),
            )
            Text(text = "Week", Modifier.padding(start = 10.dp, end = 20.dp))
        }

        bargraph.BarGraph()
        BottomAppBar(navController)
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomAppBar(navController: NavHostController) {
    Scaffold(
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .border(
                        border = BorderStroke(
                            width = 0.1.dp, // Border width
                            color = Color.LightGray // Border color
                        ),
                    )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp), // Adjust horizontal padding as needed
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        NavigationItem(navController , Screen.Home, Icons.Default.Home, "Home")
                        NavigationItem(navController, Screen.Notifications, Icons.Default.Notifications, "Notification")
                        NavigationItem(navController, Screen.Add, Icons.Default.Add, "Add")
                        NavigationItem(navController, Screen.History, Icons.Default.Refresh, "History")
                        NavigationItem(navController, Screen.Profile, Icons.Default.AccountCircle, "Profile")
                    }
                }
            }
        },
        content = {

        }
    )
}

@Composable
fun NavigationItem(
    navController: NavHostController,
    screen: Screen,
    icon: ImageVector,
    label: String
) {
    IconButton(
        onClick = { navController.navigate(screen.route) }
    ) {
        Icon(imageVector = icon, contentDescription = label)
    }
}

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Profile : Screen("profile")
    object Notifications : Screen("profile")
    object History : Screen("profile")
    object Add : Screen("Add")
}




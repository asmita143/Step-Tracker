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
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.stepcounter.Homepage.StatisticsGraph
import com.example.stepcounter.Homepage.StepInfoTop
import com.example.stepcounter.barcodeScanner.BarcodeScanner
import com.example.stepcounter.barcodeScanner.BarcodeViewModel
import com.example.stepcounter.database.StepTrackerViewModel
import com.example.stepcounter.firstScreen.DisplayDataScreen
import com.example.stepcounter.firstScreen.InputDataPage
import com.example.stepcounter.foodScreen.AddNewMeal
import com.example.stepcounter.foodScreen.CaloriesPerProduct
import com.example.stepcounter.foodScreen.CaloriesScreen
import com.example.stepcounter.foodScreen.ManualInput
import com.example.stepcounter.ui.theme.StepCounterTheme
import java.time.DayOfWeek
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.math.sqrt

class MainActivity : ComponentActivity() {
    private val stepCounter = StepCounter()
    private val viewModel: StepTrackerViewModel by viewModels()
    private val foodViewModel: StepTrackerViewModel by viewModels()
    private val barcodeViewModel: BarcodeViewModel by viewModels()

    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadData()
        setContent {
            val navController = rememberNavController()
            val currentTimeMillis  = System.currentTimeMillis()
            val instant = Instant.ofEpochMilli(currentTimeMillis)
            val zoneId = ZoneId.of("UTC")
            val formattedTime = instant.atZone(zoneId)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            val currentDate = instant.atZone(zoneId).toLocalDate()
            val dayOfWeek = currentDate.dayOfWeek

            StepCounterTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    NavHost(navController = navController, startDestination = Screen.Home.route) {
                        composable(route = Screen.Home.route) {
                            //Create and display the content for the Home screen
                            Home(navController, stepCounter.getTotalStepsTaken(), viewModel, dayOfWeek)
                        }
                        composable(route = Screen.Profile.route) {
                            // Create and display the content for the Profile screen
                            DisplayDataScreen(navController = navController ,this@MainActivity)
                        }
                        composable(route = Screen.Menu.route) {
                            // Create and display the content for the Profile screen
                           CaloriesScreen(navController, currentDate, foodViewModel)
                        }
                        composable("CaloriesPerProduct/{item}") {navBackStackEntry ->
                            navBackStackEntry.arguments?.getString("item")
                                ?.let { CaloriesPerProduct(navController, it, foodViewModal) }
                        }
                        composable("InputDataPage") {
                            InputDataPage(navController,this@MainActivity)
                        }
                        composable("MealOfDay"){
                            AddNewMeal(navController,
                                barcodeViewModel,
                                foodViewModel
                                )
                        }
                        composable("ManualInput"){
                            ManualInput(navController, foodViewModel)
                        }
                    }
                    }
                    //viewModel.addSteps(Step(0, "$formattedTime-$dayOfWeek", 165))
                }
            }
            stepCounter.initialize(this)
        }

    override fun onResume() {
        super.onResume()
        stepCounter.startListening()
    }

    override fun onPause() {
        super.onPause()
        saveData()
        stepCounter.stopListening()
    }

    private fun saveData() {

        val sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)

        val editor = sharedPreferences.edit()
        editor.putFloat("key1", stepCounter.getTotalStepsTaken())
        Log.d("MSGSaved", editor.putFloat("key1", stepCounter.getTotalStepsTaken()).toString())
        editor.apply()
    }

    private fun loadData() {
        // In this function we will retrieve data
        val sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        Log.d("MSGLoad" , sharedPreferences.getFloat("key1", 0f).toString())
        val savedNumber = sharedPreferences.getFloat("key1", 0f)
        stepCounter.setTotalStepsTaken(savedNumber)
    }
}

class StepCounter: SensorEventListener {
    private var sensorManager: SensorManager? = null
    private var accelerometer: Sensor? = null
    private var magnitudePreviousStep = 0f
    private var totalSteps : MutableState<Float> = mutableFloatStateOf(0f)
    private var previousTotalSteps : MutableState<Float> = mutableFloatStateOf(0f)

    fun initialize(context: Context) {
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            val x : Float = event.values[0]
            val y : Float = event.values[1]
            val z :Float = event.values[2]
            val magnitude : Float = sqrt(x*x + y*y + z*z)

            var magnitudeDelta : Float = magnitude - magnitudePreviousStep

            magnitudePreviousStep = magnitude

            if(magnitudeDelta > 6) {
                totalSteps.value++
            }
        }
    }

    fun startListening() {
        sensorManager?.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }

    fun stopListening() {
        previousTotalSteps = totalSteps
        sensorManager?.unregisterListener(this)
    }

    fun getTotalStepsTaken(): Float {
        return totalSteps.value
    }

    fun setTotalStepsTaken(steps : Float) {
        this.totalSteps.value = steps
        Log.d("MSGSet", totalSteps.value.toString())
    }

}


@Composable
fun Home(
    navController: NavHostController,
    totalStepsTaken: Float,
    viewModel: StepTrackerViewModel,
    dayOfWeek: DayOfWeek
) {
    var checked by remember { mutableStateOf(true) }
    val bargraph = StatisticsGraph()
    val stepInfo = StepInfoTop()
    val step = viewModel.getAllSteps().observeAsState(listOf())
    Log.d("MSGHome", step.value.size.toString())
val caloriesBurned = totalStepsTaken * 0.04f // steps * stride length * weight * constant(0.0002) so 0.4 is the average excluding the steps 
        val targetCalories = 500f
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        stepInfo.StepsInfoSection(totalStepsTaken)

         if (caloriesBurned < targetCalories) {
                Text(
                    text = "You should burn more calories today!",
                    color = Color.Red,
                    
                )
            } else {
                Text(
                    text = "Great job on reaching your calorie burn goal!",
                    color = Color.Green,
                    
                )
            }

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
                    checkedTrackColor = MaterialTheme.colorScheme.inversePrimary,
                ),
            )
            Text(text = "Week", Modifier.padding(start = 10.dp, end = 20.dp))
        }

        bargraph.BarGraph(step.value, dayOfWeek)
        Spacer(modifier = Modifier.weight(1f))
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
        )
        {
            BottomAppBar(navController)
        }
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
                        .padding(horizontal = 30.dp), // Adjust horizontal padding as needed
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        NavigationItem(navController, Screen.Menu, Icons.Default.Menu, "Menu")
                        NavigationItem(navController , Screen.Home, Icons.Default.Home, "Home")
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
    object Menu : Screen("Menu")
}


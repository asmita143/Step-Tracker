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
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.stepcounter.Homepage.StatisticsGraph
import com.example.stepcounter.Homepage.StepInfoTop
import com.example.stepcounter.database.StepTrackerViewModel
import com.example.stepcounter.database.entities.Step
import com.example.stepcounter.ui.theme.StepCounterTheme
import java.time.DayOfWeek
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.math.sqrt


class MainActivity : ComponentActivity() {
    private val stepCounter = StepCounter()
    private val viewModel: StepTrackerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val currentTimeMillis  = System.currentTimeMillis()
            val instant = Instant.ofEpochMilli(currentTimeMillis)
            val zoneId = ZoneId.of("UTC")
            val formattedTime = instant.atZone(zoneId)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            val currentDate = instant.atZone(zoneId).toLocalDate()
            val dayOfWeek = currentDate.dayOfWeek

            Log.d("MSGtime" , "$formattedTime $dayOfWeek")
            StepCounterTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(navController = navController, startDestination = Screen.Home.route) {
                        composable(route = Screen.Home.route) {
                            // Create and display the content for the Home screen
                            Home(navController, stepCounter.getTotalStepsTaken(), viewModel, dayOfWeek)
                        }
                        composable(route = Screen.Profile.route) {
                            // Create and display the content for the Profile screen
                            ProfileScreen(navController)
                        }
                    }

                    //viewModel.addSteps(Step(0, "$formattedTime-$dayOfWeek", 160))
                }
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
        stepCounter.stopListening()
    }
}

class StepCounter: SensorEventListener {
    private var sensorManager: SensorManager? = null
    private var accelerometer: Sensor? = null
    private var magnitudePreviousStep = 0f
    private var totalSteps : MutableState<Float> = mutableStateOf(0f)

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
            val step = totalSteps.value.toInt()

        }
    }

    fun startListening() {
        sensorManager?.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }

    fun stopListening() {
        sensorManager?.unregisterListener(this)
    }

    fun getTotalStepsTaken(): Float {
        return totalSteps.value
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

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        stepInfo.StepsInfoSection(totalStepsTaken)

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

        bargraph.BarGraph(step.value, dayOfWeek)
        BottomAppBar(navController)
    }
}

@Composable
fun ProfileScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Profile picture
        Image(
            painter = painterResource(id = R.drawable.profile_pic),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(120.dp)
                .clip(shape = CircleShape)
                .background(MaterialTheme.colorScheme.primary)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // User's name
        Text(
            text = "John Doe",
            style = androidx.compose.ui.text.TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        // User's email
        Text(
            text = "johndoe@example.com",
            style = androidx.compose.ui.text.TextStyle(
                fontSize = 16.sp,
                color = Color.Gray
            )
        )

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




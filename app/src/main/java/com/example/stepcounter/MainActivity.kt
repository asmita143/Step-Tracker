package com.example.stepcounter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.stepcounter.ui.theme.StepCounterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StepCounterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
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
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}
 

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StepCounterTheme {
        Greeting("Android")
    }
}

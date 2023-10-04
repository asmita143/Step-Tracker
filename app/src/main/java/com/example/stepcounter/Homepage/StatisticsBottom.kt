package com.example.stepcounter.Homepage

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.stepcounter.database.entities.Step
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

class StatisticsGraph{

    @Composable
    fun BarGraph(value: List<Step>, dayOfWeek: DayOfWeek) {
        val configuration = LocalConfiguration.current
        val screenHeight = configuration.screenHeightDp.dp
        val entries = ArrayList<BarEntry>()
        val myArrayList = ArrayList<Step>()

        if(value.isNotEmpty()) {
            for(i in value.size - 7 until value.size) {
                myArrayList.add(Step(value[i].stepId, value[i].date,value[i].stepAmount))
            }
        }

        if(myArrayList.isNotEmpty()){
            for (i in 0..6) {
                val day = myArrayList[i].date.split("-")
                entries.add(BarEntry(i.toFloat(),myArrayList[i].stepAmount.toFloat()))
                Log.d("MSGInside", day[1]+ "/" +day[2])
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .height(screenHeight / 2 - 130.dp)
                .background(MaterialTheme.colorScheme.primary)

        ) {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { context: Context ->
                    val view = BarChart(context)
                    view.legend.isEnabled = false
                    val data = BarData(BarDataSet( entries, "Label 1"))
                    view.data = data
                    view
                },
                update = { view ->
                    // Update the view
                    var dataSet: BarDataSet = BarDataSet( entries, "Label 1")
                    val xAxisFormatter: ValueFormatter = DayAxisValueFormatter()
                    val data = BarData(dataSet)
                    view.description.isEnabled = false
                    view.data = data
                    view.axisRight.isEnabled = false
                    view.axisLeft.isEnabled = false
                    view.xAxis.position = XAxis.XAxisPosition.BOTTOM
                    view.xAxis.setDrawGridLines(false)
                    view.animateXY(2000, 3000, Easing.EaseInSine)
                    view.setTouchEnabled(true)
                    view.xAxis.valueFormatter = xAxisFormatter
                    view.invalidate()
                }
            )
        }
    }
}

//Class to convert the float values to String for X-axis Label in Bar graph
class DayAxisValueFormatter() : ValueFormatter() {
    //private val labels = arrayOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
    private val labels = getListOfWeek()
    override fun getFormattedValue(value: Float): String {
        //val labels = arrayOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")

        var label = ""
        when (value) {
            0.0F -> label = labels[6]
            1.0F -> label = labels[5]
            2.0F -> label = labels[4]
            3.0F -> label = labels[3]
            4.0F -> label = labels[2]
            5.0F -> label = labels[1]
            6.0F -> label = labels[0]
        }
        return label
    }

    private fun getListOfWeek() : List<String> {
        val currentDate: LocalDate = LocalDate.now()

        // Create an array to hold the days of the week
        val daysOfWeek = mutableListOf<String>()

        // Add the days of the week, starting from today
        var currentDay = currentDate.minusDays(1)

        for (i in 0 until 7) {
            val dayName = currentDay.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
            daysOfWeek.add(dayName)
            currentDay = currentDay.minusDays(1) // Move to the next day
        }

        Log.d("MSG1", "${daysOfWeek}")

        return daysOfWeek
    }
}
package com.example.stepcounter.Homepage

import android.content.Context
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
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter

class StatisticsGraph{

    @Composable
    fun BarGraph(){
        val configuration = LocalConfiguration.current
        val screenHeight = configuration.screenHeightDp.dp

        //Hard coded values for testing the BarGraph
        val entries = ArrayList<BarEntry>()

        entries.add(BarEntry(0F,3000F))
        entries.add(BarEntry(1F, 4050F))
        entries.add(BarEntry(2F, 4500F))
        entries.add(BarEntry(3F, 6110F))
        entries.add(BarEntry(4F, 5500F))
        entries.add(BarEntry(5F, 3550F))
        entries.add(BarEntry(6F, 3300F))
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
                    val xAxisFormatter: ValueFormatter = DayAxisValueFormatter()
                    var dataSet: BarDataSet = BarDataSet( entries, "Label 1")
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
    private val labels = arrayOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
    override fun getFormattedValue(value: Float): String {
        var label = ""
        when (value) {
            0.0F -> label = labels[0]
            1.0F -> label = labels[1]
            2.0F -> label = labels[2]
            3.0F -> label = labels[3]
            4.0F -> label = labels[4]
            5.0F -> label = labels[5]
            6.0F -> label = labels[6]
        }
        return label
    }
}
package com.example.stepcounter.foodScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun CaloriesScreen() {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    Column(verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxHeight()
            .padding(20.dp)
            //.background(MaterialTheme.colorScheme.background)
            )
                {
        Column(modifier = Modifier
            .padding(horizontal = 20.dp)) {
            //this box is for the date
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Red)
            ) {
                Row(modifier = Modifier.padding(20.dp)) {
                    Column {
                        Text(text = "Today")
                        Text(text = "18 February")
                    }
                }
            }
            //this box is for the calorie scale
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(modifier = Modifier.padding(20.dp)) {
                    Column {
                        Text(text = "1,258", fontSize = 20.sp)
                        Text(text = ("Calories Today").uppercase())
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(100.dp))

        Row(modifier = Modifier
            .weight(1f, false)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.inversePrimary)) {
            Column() {
                Button(onClick = {}, modifier = Modifier
                    .fillMaxWidth()) {
                    Text(text = "More Info")
                }
                Button(onClick = {}, modifier = Modifier
                    .fillMaxWidth()) {
                    Text(text = "Eaten Today")
                }
            }
        }
    }
}
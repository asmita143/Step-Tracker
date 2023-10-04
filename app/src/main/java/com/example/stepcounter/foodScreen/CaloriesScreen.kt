package com.example.stepcounter.foodScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stepcounter.R
import com.example.stepcounter.ui.theme.md_theme_light_background

@Preview
@Composable
fun CaloriesScreen() {

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    Column(verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxHeight()
            .padding(20.dp)
            .background(colorResource(id = R.color.background))
            )
                {
        Column(modifier = Modifier
            .padding(horizontal = 20.dp)) {
            //this box is for the date
            Box(
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                Row(modifier = Modifier.padding(20.dp)) {
                    Column {
                        Text(text = "Today")

                        Text(text = "18 February")
                    }
                }
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height = 4.dp)
                    .background(color = Color.Blue)
            )
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
            .fillMaxWidth(),
            ) {
            Column() {
                ElevatedButton(
                    onClick = {},
                    modifier = Modifier
                    .fillMaxWidth()
                    ,) {
                    Text(text = "More Info")
                }
                ElevatedButton(onClick = {}, modifier = Modifier
                    .fillMaxWidth()) {
                    Text(text = "Eaten Today")
                }
            }
        }
    }
}
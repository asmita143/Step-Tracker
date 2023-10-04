package com.example.stepcounter.foodScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stepcounter.R


@Preview
@Composable
fun CaloriesScreen() {
    var isOverlayVisible by remember { mutableStateOf(false) }
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    Column(verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxHeight()
            .padding(20.dp)
            .background(colorResource(id = R.color.background))
            )
    {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)

        ) {
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

        Row(
            modifier = Modifier
                .weight(1f, false)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(20.dp)
            ) {
                ElevatedButton(
                    onClick = { isOverlayVisible = !isOverlayVisible },
                    modifier = Modifier
                        .fillMaxWidth(0.7f),
                ) {
                    Text(text = "More Info")
                }

                AnimatedVisibility(
                    visible = isOverlayVisible,
                    enter = slideInVertically(
                        initialOffsetY = {-40},
                        animationSpec = tween(400)
                    ),
                    exit = slideOutVertically(
                        targetOffsetY ={-40} ,
                        animationSpec = tween(400)
                    )
                ){
                    MoreInfoOverlay(
                        onCloseClick = { isOverlayVisible = false }
                    )
                }

                    ElevatedButton(
                        onClick = {},
                        modifier = Modifier
                            .fillMaxWidth(0.7f),
                    ) {
                        Text(text = "Eaten Today")
                    }
                }
            }

        }
    }
@Composable
fun MoreInfoOverlay(onCloseClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            text = "Total calories:1500",
            fontSize = 20.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Fat:20%",
            fontSize = 16.sp,
            color = Color.Black
        )
        Text(
            text = "Carbohydrate:20%",
            fontSize = 16.sp,
            color = Color.Black
        )
        Text(
            text = "Sugar:10%",
            fontSize = 16.sp,
            color = Color.Black
        )
        Text(
            text = "Sugar:10%",
            fontSize = 16.sp,
            color = Color.Black
        )
        Text(
            text = "Sugar:10%",
            fontSize = 16.sp,
            color = Color.Black
        )
        Text(
            text = "Sugar:10%",
            fontSize = 16.sp,
            color = Color.Black
        )
        Text(
            text = "Sugar:10%",
            fontSize = 16.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(16.dp))
        SmallFloatingActionButton(
            onClick = { /* Do something when the button in the overlay is clicked */ },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Row {
                Text("Add new entries")
                Spacer(modifier = Modifier.width(10.dp))
                Icon(Icons.Filled.Add, "Small floating action button.")
            }

        }
    }
}
@Composable
fun EatenTodayOverlay(onCloseClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            text = "Total calories:1500",
            fontSize = 20.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Fat:20%",
            fontSize = 16.sp,
            color = Color.Black
        )
        Text(
            text = "Carbohydrate:20%",
            fontSize = 16.sp,
            color = Color.Black
        )
        Text(
            text = "Sugar:10%",
            fontSize = 16.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(16.dp))
        SmallFloatingActionButton(
            onClick = { /* Do something when the button in the overlay is clicked */ },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Row {
                Text("Add new entries")
                Spacer(modifier = Modifier.width(10.dp))
                Icon(Icons.Filled.Add, "Small floating action button.")
            }

        }
    }
}







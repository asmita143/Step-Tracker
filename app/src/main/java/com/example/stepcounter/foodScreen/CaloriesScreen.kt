package com.example.stepcounter.foodScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.stepcounter.R
import java.time.LocalDate

@Composable
fun CaloriesScreen(navController: NavHostController, currentDate: LocalDate) {
    var isOverlayVisible by remember { mutableStateOf(false) }
    var isEatenTodayOverlayVisible by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxHeight()
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
                        Text(text = currentDate.dayOfMonth.toString() + " " + currentDate.month.toString())
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
                Row(modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                )
                {
                    Column(
                        modifier = Modifier
                            .shadow(elevation = 5.dp)
                            .background(Color.White)
                            .padding(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(text = "1,258", fontSize = 15.sp)
                        Text(text = ("Calories Eaten"))
                    }

                    Column(
                        modifier = Modifier
                            .shadow(elevation = 5.dp)
                            .background(Color.White)
                            .padding(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(text = "958", fontSize = 15.sp)
                        Text(text = ("Burnt Calories"))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                //.weight(1f, false)
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
                    onClick = {
                        isOverlayVisible = !isOverlayVisible
                        isEatenTodayOverlayVisible = false
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.7f),
                ) {
                    Text(text = "More Info", color = Color.Black)
                }

                AnimatedVisibility(
                    visible = isOverlayVisible,
                    enter = slideInVertically(
                        initialOffsetY = { -40 },
                        animationSpec = tween(400)
                    ),
                    exit = slideOutVertically(
                        targetOffsetY = { -40 },
                        animationSpec = tween(400)
                    )
                ) {
                    MoreInfoOverlay(
                        onCloseClick = { isOverlayVisible = false }
                    )
                }

                ElevatedButton(
                    onClick = {
                        isEatenTodayOverlayVisible = !isEatenTodayOverlayVisible
                        isOverlayVisible = false // Close the other overlay
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.7f),
                ) {
                    Text(text = "Eaten Today", color = Color.Black)
                }
                AnimatedVisibility(
                    visible = isEatenTodayOverlayVisible,
                    enter = slideInVertically(
                        initialOffsetY = { -40 },
                        animationSpec = tween(400)
                    ),
                    exit = slideOutVertically(
                        targetOffsetY = { -40 },
                        animationSpec = tween(400)
                    )
                ) {
                    EatenTodayOverlay(onCloseClick = { isEatenTodayOverlayVisible = false }, navController)
                }
            }
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
        )
        {
            com.example.stepcounter.BottomAppBar(navController)
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
        //Spacer(modifier = Modifier.height(16.dp))
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
fun EatenTodayOverlay(onCloseClick: () -> Unit, navController: NavHostController) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            text = "List of foods",
            fontSize = 20.sp,
            color = Color.Black
        )
        //Spacer(modifier = Modifier.height(16.dp))
        LazyColumn() {
            item {
                Card(
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate("CaloriesPerProduct")
                        },
                    shape = RoundedCornerShape(CornerSize(10.dp))
                ) {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Orange",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                modifier = Modifier.padding(8.dp)
                            )
                            Text(
                                text = "Time:10:00",
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }

                Card(
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                        .fillMaxWidth()
                        .clickable {

                        },
                    shape = RoundedCornerShape(CornerSize(10.dp))
                ) {
                    Column {
                        Row (
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ){
                            Text(
                                text = "Apple",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                modifier = Modifier.padding(8.dp)
                            )
                            Text(
                                text = "Time:10:00",
                                modifier = Modifier.padding(8.dp)
                            )
                        }

                    }
                }

            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun CaloriesPreview (){
}







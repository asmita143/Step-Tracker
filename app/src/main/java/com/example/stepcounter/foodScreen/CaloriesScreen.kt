package com.example.stepcounter.foodScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.stepcounter.R
import com.example.stepcounter.database.StepTrackerViewModel
import com.example.stepcounter.database.entities.MealToday
import com.example.stepcounter.ui.theme.Typography
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * This screen will show the consumed calories and burned calories by the user in the current day
 * user can see the items/meals that are eaten today
 * Clicking more info button will show a pop up box where user will have an option to add the
 * meal which are consumed today.
 */
@Composable
fun CaloriesScreen(
    navController: NavHostController,
    foodViewModal: StepTrackerViewModel
) {
    var totalCalories = 0
    //get current date
    val currentTimeMillis = System.currentTimeMillis()
    val instant = Instant.ofEpochMilli(currentTimeMillis)
    val zoneId = ZoneId.of("UTC")
    val formattedTime = instant.atZone(zoneId)
        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    val currentDate = instant.atZone(zoneId).toLocalDate()
    val dayOfWeek = currentDate.dayOfWeek

    var isOverlayVisible by remember { mutableStateOf(false) }
    var isEatenTodayOverlayVisible by remember { mutableStateOf(false) }
    val eatenToday =
        foodViewModal.getMealEatenTodayByDate("$formattedTime-$dayOfWeek").observeAsState(listOf())
    val tempEatenToday = eatenToday.value

    for (meal in tempEatenToday) {
        totalCalories += meal.calories
    }

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxHeight()
            .background(colorResource(id = R.color.background))
    )
    {
        CalorieCalculation(
            currentDate = currentDate.dayOfMonth.toString() + " " + currentDate.month.toString(),
            consumedCalories = totalCalories.toFloat(),
            burnedCalories = 500F
        )

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
                        onCloseClick = { isOverlayVisible = false }, navController, totalCalories
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
                    EatenTodayOverlay(
                        onCloseClick = { isEatenTodayOverlayVisible = false },
                        navController,
                        eatenToday.value
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
        )
        {
            com.example.stepcounter.BottomAppBar(navController)
        }
    }
}

@Composable
fun MoreInfoOverlay(onCloseClick: () -> Unit, navController: NavHostController, totalCalories: Int) {
    var scrollState by remember { mutableStateOf(ScrollState(0)) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(5))
            .background(Color.White)
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        repeat(1) {
            Text(
                text = "Total Calories: $totalCalories",
                style = Typography.labelLarge,
                color = Color.Black
            )
            Text(
                text = "Fat:20%",
                style = Typography.labelSmall,
                color = Color.Black
            )
            Text(
                text = "Carbohydrate:20%",
                style = Typography.labelSmall,
                color = Color.Black
            )
            Text(
                text = "Sugar:10%",
                style = Typography.labelSmall,
                color = Color.Black
            )
            Text(
                text = "Protein:10%",
                style = Typography.labelSmall,
                color = Color.Black
            )
            Text(
                text = "Salt:10%",
                style = Typography.labelSmall,
                color = Color.Black
            )

            SmallFloatingActionButton(
                onClick = { navController.navigate("MealOfDay") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                    },
            ) {
                Row {
                    Text("Add new entries")
                    Spacer(modifier = Modifier.width(10.dp))

                    Icon(
                        Icons.Filled.Add,
                        "Small floating action button.",
                    )
                }
            }
        }

    }
}

@Composable
fun EatenTodayOverlay(
    onCloseClick: () -> Unit,
    navController: NavHostController,
    eatenToday: List<MealToday>
) {

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(5))
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            text = "List of foods",
            style = Typography.labelLarge
        )
        //Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 0.dp)) {
            items(eatenToday) { item ->
                Card(
                    modifier = Modifier
                        .defaultMinSize(100.dp, 30.dp)
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate("CaloriesPerProduct/${item.id}")
                        },
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = item.name,
                        style = Typography.labelSmall,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun CaloriesPreview(caloriesVal: String, caloriesText: String) {
    Column(
        modifier = Modifier
            .size(170.dp, 100.dp)
            .clip(RoundedCornerShape(15))
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
        Text(text = caloriesVal, style = Typography.labelLarge)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = caloriesText, style = Typography.labelSmall)
    }
}

@Composable
private fun CalorieCalculation(
    currentDate: String,
    consumedCalories: Float,
    burnedCalories: Float
) {
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
                    Text(text = "Today", style = Typography.labelSmall)
                    Text(
                        text = currentDate,
                        style = Typography.labelLarge
                    )
                }
            }
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(height = 4.dp)
                .background(color = Color.White)
        )
        //this box is for the calorie scale
        Box(
            modifier = Modifier
                .fillMaxWidth()

        ) {
            Row(
                modifier = Modifier
                    .padding(0.dp, 20.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                CaloriesPreview(
                    caloriesVal = consumedCalories.toInt().toString(),
                    caloriesText = "Consumed Calories"
                )
                CaloriesPreview(
                    caloriesVal = burnedCalories.toInt().toString(),
                    caloriesText = "Burned Calories"
                )
            }
        }
    }

    val burnedCaloriesPercentage = (burnedCalories / consumedCalories)

    CircularData(burnedCaloriesPercent = burnedCaloriesPercentage)

}

// Reusable function to display data for the calculated percentage of the burned calories with the consumed calories
@Composable
private fun CircularData(
    burnedCaloriesPercent: Float,
    size: Int = 250,
) {
    // size of graph to show calories, h: 300.dp, w: 300.dp so multiplied by 3
    val burnedCaloriesPercentToSize = (burnedCaloriesPercent * size)
    val burnedCaloriesPercentToPercentage = burnedCaloriesPercent * 100

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(5))
                .size(size.dp)
                .background(Color.White),
            verticalArrangement = Arrangement.Bottom
        ) {
            Column(
                modifier = Modifier
                    .height(burnedCaloriesPercentToSize.dp)
                    .width(size.dp)
                    .background(MaterialTheme.colorScheme.inversePrimary),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (burnedCaloriesPercentToPercentage > 100) {
                    Text(
                        text = "You should eat more.",
                        style = Typography.labelSmall
                    )

                    Text(
                        text = "${(burnedCaloriesPercentToPercentage - 100).toInt()}% excess calories burned.",
                        style = Typography.labelSmall
                    )
                } else {
                    Text(
                        text = "${burnedCaloriesPercentToPercentage.toInt()}% calories burned",
                        style = Typography.labelSmall
                    )
                }
            }
        }
    }
}

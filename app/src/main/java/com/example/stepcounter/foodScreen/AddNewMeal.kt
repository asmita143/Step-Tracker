package com.example.stepcounter.foodScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stepcounter.R
<<<<<<< HEAD
import com.example.stepcounter.database.StepTrackerViewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavHostController
=======
import com.example.stepcounter.ui.theme.Typography
>>>>>>> foodScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewMeal(foodViewModal: StepTrackerViewModel, navController: NavHostController) {
    var name by remember { mutableStateOf("") }
    var mass by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    var selectedItem by remember { mutableStateOf("Select an item") }
    val meal = foodViewModal.getAllMeals().observeAsState(listOf())
    val mealList = ArrayList<String>()

    for (i in meal.value) {
        mealList.add(i.name.toString())
    }

    Column(
        modifier = Modifier
            .padding(top = 50.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
<<<<<<< HEAD
            modifier = Modifier
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "New Meal",
                    modifier = Modifier.padding(bottom = 16.dp)
=======
            Modifier
                .fillMaxWidth(0.90f)
                .padding(5.dp)
        ) {
            Row() {
                Text(
                    text = "Add new meal",
                    style = Typography.labelLarge,
                    modifier = Modifier.padding(start = 20.dp, top = 10.dp, bottom = 10.dp)
>>>>>>> foodScreen
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 20.dp, top = 10.dp, bottom = 10.dp),
                    contentAlignment = Alignment.CenterEnd

                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_camera),
                        contentDescription = "Camera for scanning items",
                        modifier = Modifier
                            .size(30.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth(0.90f),
            contentAlignment = Alignment.Center
        ) {
            Column() {
                OutlinedTextField(
                    value = name,
<<<<<<< HEAD
                    label = { Text(text = "Meal Name") },
                    onValueChange = {
                        name = it
                    },
                    keyboardActions = KeyboardActions(
                        onDone = {
                            // Handle the text input submission if needed
                        }
                    ),
=======
                    label = { Text(text = "Food Item", color = Color.Black) },
                    onValueChange = { name = it },
>>>>>>> foodScreen
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = Color.Black,
                        focusedBorderColor = Color.Gray,// Customize focused outline color
                        unfocusedBorderColor = MaterialTheme.colorScheme.inversePrimary, // Customize unfocused outline color
                    ),
                    textStyle = TextStyle(fontSize = 18.sp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.primary,
                            shape = MaterialTheme.shapes.small
                        )
                        .padding(5.dp)
                )
                OutlinedTextField(
                    value = mass,
                    label = { Text("Mass in grams", color = Color.Black) },
                    onValueChange = { mass = it },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = Color.Black,
                        focusedBorderColor = Color.Gray,// Customize focused outline color
                        unfocusedBorderColor = MaterialTheme.colorScheme.inversePrimary, // Customize unfocused outline color
                    ),
                    textStyle = TextStyle(fontSize = 18.sp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.primary,
                            shape = MaterialTheme.shapes.small
                        )
                        .padding(5.dp)
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .fillMaxHeight(0.5f),
                    contentAlignment = Alignment.Center
                ) {
                    SmallFloatingActionButton(
                        onClick = { navController.navigate("MealOfDay") },
                        modifier = Modifier
                            .fillMaxWidth(),
                    ) {
                        Row {
                            Text("Add item")
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
        Box() {
            Column {
                Text(text = "Not in the list?", style = MaterialTheme.typography.labelLarge, modifier = Modifier.padding(8.dp))
                TextButton(
                    onClick = { navController.navigate("ManualInput") }
                ) {
                    Text(
                        "Add manually",
                        style = Typography.labelSmall,
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.inversePrimary)
                            .padding(8.dp)

                    )
                }
            }

        }
        Spacer(modifier = Modifier.height(50.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
<<<<<<< HEAD
                .padding(20.dp),
            contentAlignment = Alignment.Center
=======
                .height(55.dp)
>>>>>>> foodScreen
        ) {
            com.example.stepcounter.BottomAppBar(navController)
        }
<<<<<<< HEAD
        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp),

            contentAlignment = Alignment.BottomEnd
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_camera),
                contentDescription = "Camera",
                modifier = Modifier
                    .size(48.dp),
                contentScale = ContentScale.Fit
            )
        }
=======

>>>>>>> foodScreen
    }
}



<<<<<<< HEAD
=======




>>>>>>> foodScreen

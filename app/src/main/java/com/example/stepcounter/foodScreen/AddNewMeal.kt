package com.example.stepcounter.foodScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stepcounter.R
import com.example.stepcounter.database.StepTrackerViewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewMeal(foodViewModal: StepTrackerViewModel) {
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

    Column {
        Box(
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
                )

                /*BasicTextField(
                    value = searchText,
                    onValueChange = { newText ->
                        searchText = newText
                        expanded = true // Show the dropdown menu when typing
                    },
                    textStyle = TextStyle(fontSize = 18.sp),
                    modifier = Modifier
                        .background(Color.Gray)
                        .fillMaxWidth()
                        .padding(16.dp),
                )

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    val filteredItems =
                        mealList.filter { it.contains(searchText, ignoreCase = true) }
                    if (filteredItems.isEmpty()) {
                        DropdownMenuItem(text = { "No results found" }, onClick = { *//*TODO*//* })
                    } else {
                        filteredItems.forEach { item ->
                            DropdownMenuItem(
                                text = { item },
                                onClick = {
                                    selectedItem = item
                                    searchText = item
                                    expanded = false
                                }
                            )
                        }
                    }
                }*/

                TextField(
                    value = name,
                    label = { Text(text = "Meal Name") },
                    onValueChange = {
                        name = it
                    },
                    keyboardActions = KeyboardActions(
                        onDone = {
                            // Handle the text input submission if needed
                        }
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
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
                        .padding(16.dp)
                )

                TextField(
                    value = mass,
                    label = { Text("Text") },
                    onValueChange = { mass = it },
                    keyboardActions = KeyboardActions(
                        onDone = {
                            // Handle the text input submission if needed
                        }
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    textStyle = TextStyle(fontSize = 18.sp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.primary,
                            shape = MaterialTheme.shapes.small
                        )
                        .padding(16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            FloatingActionButton(
                onClick = {
                    // Handle the floating "+" button click
                },
                modifier = Modifier.size(56.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = "Add",
                    tint = Color.White
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),

            contentAlignment = Alignment.BottomEnd) {
            Image(
                painter = painterResource(id = R.drawable.ic_camera),
                contentDescription = "Camera",
                modifier = Modifier
                    .size(48.dp),
                contentScale = ContentScale.Fit
            )
        }
    }
}




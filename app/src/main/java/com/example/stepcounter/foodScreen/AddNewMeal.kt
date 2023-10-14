package com.example.stepcounter.foodScreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.stepcounter.R
import com.example.stepcounter.database.StepTrackerViewModel
import com.example.stepcounter.database.entities.Meal
import com.example.stepcounter.ui.theme.Typography
import kotlin.reflect.typeOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewMeal(navController: NavHostController, foodViewModal: StepTrackerViewModel) {
    var name by remember { mutableStateOf("") }
    var mass by remember { mutableStateOf("") }
    val mealList = foodViewModal.getAllMeals().observeAsState(listOf())
    val tempMealList =  mealList.value.associate { it.name to it.calories }
    var matchedItems by remember { mutableStateOf(emptyMap<String?, Int>()) }
    var isDialogVisible by remember { mutableStateOf(false) }
    var isMatchedItem by remember { mutableStateOf(true) }

    LaunchedEffect(matchedItems) {
        if (matchedItems.isNotEmpty()) {
            isDialogVisible = true
        }
    }

    Column(
        modifier = Modifier
            .padding(top = 50.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            Modifier
                .fillMaxWidth(0.90f)
                .padding(5.dp)
        ) {
            Row() {
                Text(
                    text = "Add new meal",
                    style = Typography.labelLarge,
                    modifier = Modifier.padding(start = 20.dp, top = 10.dp, bottom = 10.dp)
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
                    label = { Text(text = "Food Item", color = Color.Black) },
                    onValueChange = { name = it },
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
                if(!isMatchedItem){
                    Text(
                        text = "No matched meal in the database, Please add meal manually",
                        color = Color.Red,
                        fontStyle = FontStyle.Italic,
                        fontFamily = FontFamily.Serif,
                        letterSpacing = 0.1.sp,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }

                if (isDialogVisible) {
                    Text(
                        text = "Did you mean..?",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    )
                    LazyColumn(
                        modifier = Modifier
                            .background(Color.LightGray)
                            .height(160.dp)
                    ) {
                        items(matchedItems.toList()) { (key, value) ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                            ) {
                                Card(
                                    modifier = Modifier
                                        .height(30.dp)
                                        .fillMaxWidth()
                                        .clickable {
                                            //navController.navigate("CaloriesPerProduct")
                                            name = key.toString()
                                            isDialogVisible = false
                                        },
                                    shape = RoundedCornerShape(5.dp)
                                ){
                                    Text(
                                        text = key.toString(),
                                        style = Typography.labelSmall,
                                        modifier = Modifier
                                            .padding(5.dp)
                                    )
                                }
                            }
                        }
                    }
                } else {
                    Spacer(modifier = Modifier.height(160.dp))
                }


                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    SmallFloatingActionButton(
                        onClick = {
                            isMatchedItem = true
                            if(tempMealList.containsKey(name)){
                                val index = tempMealList.keys.indexOf(name)
                                val matchedValue = tempMealList.values.elementAt(index)

                                if(mass.toInt() == 100) {
                                    foodViewModal.addMeal(Meal(0, name, matchedValue))
                                }else{
                                    val calories = (matchedValue * mass.toInt()) / 100
                                    foodViewModal.addMeal(Meal(0, name, calories))
                                }
                                name = ""
                                mass = ""

                            }else {
                                matchedItems = isPartialMatch(name, tempMealList)

                                if(matchedItems.isEmpty()){
                                    isMatchedItem = false
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
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
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Not in the list?",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(8.dp)
                )
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
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
        ) {
            com.example.stepcounter.BottomAppBar(navController)
        }
    }

}

//This function checks and returns the names of partial match
fun isPartialMatch(input: String, stringList: Map<String?, Int>): Map<String?, Int> {
    val partialMatches = mutableMapOf<String?, Int>()

    for (entry in stringList) {
        if (entry.key?.contains(input, ignoreCase = true) == true) {
            partialMatches[entry.key] = entry.value
        }
    }
    return partialMatches
}









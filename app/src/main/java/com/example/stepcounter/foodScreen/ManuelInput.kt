package com.example.stepcounter.foodScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stepcounter.R
import androidx.compose.material3.Text as Text1

@Composable
fun FoodTrackerUI() {
    var foodItem by remember { mutableStateOf("") }
    var caloriesItem by remember { mutableStateOf("") }
    var fatItem by remember { mutableStateOf("") }
    var carbohydratesItem by remember { mutableStateOf("") }
    var sugarcaloriesItem by remember { mutableStateOf("") }

    var foodList by remember { mutableStateOf<List<Pair<String, String>>>(emptyList()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Title
        Text1(
            text = "Add new meal",
            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
            color = Color(0xFF000000) // Text color
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Input Fields
        FoodInput(
            foodItem = foodItem,
            caloriesItem = caloriesItem,
            onFoodItemChange = { foodItem = it },
            onCaloriesItemChange = { caloriesItem = it },
            onAddItem = {
                if (foodItem.isNotBlank() && caloriesItem.isNotBlank()) {
                    foodList = foodList + Pair(foodItem, caloriesItem)
                    foodItem = ""
                    caloriesItem = ""
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Food List
        LazyColumn {
            items(foodList) { (food, calories) ->
                FoodListItem(food = food, calories = calories)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodInput(
    foodItem: String,
    caloriesItem: String,
    onFoodItemChange: (String) -> Unit,
    onCaloriesItemChange: (String) -> Unit,
    onAddItem: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextField(
            value = foodItem,
            onValueChange = { onFoodItemChange(it) },
            textStyle = TextStyle(color = Color(0xFF000000)), // Text color
            singleLine = true,
            placeholder = { Text1("Food Item") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    // Handle Done button click here
                    onAddItem()
                }
            ),
            modifier = Modifier
                .weight(1f)
                .background(Color(0xFFCDE5FF)) // Background color
                .padding(16.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        TextField(
            value = caloriesItem,
            onValueChange = { onCaloriesItemChange(it) },
            textStyle = TextStyle(color = Color(0xFF000000)), // Text color
            singleLine = true,
            placeholder = { Text1(text = "Calories") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    // Handle Done button click here
                    onAddItem()
                }
            ),
            modifier = Modifier
                .weight(1f)
                .background(Color(0xFFCDE5FF)) // Background color
                .padding(16.dp)
        )

        IconButton(
            onClick = {
                // Handle Add button click here
                onAddItem()
            },
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {


        }
    }
}

@Composable
fun FoodListItem(food: String, calories: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color(0xFFDCEEF3)), // Background color

        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text1(
                text = food,
                style = TextStyle(fontWeight = FontWeight.Bold),
                color = Color(0xFF000000) // Text color
            )
            Text1(
                text = "Calories: $calories",
                color = Color(0xFF000000) // Text color
            )
        }
    }
}

@Preview
@Composable
fun FoodTrackerUIPreview() {
    FoodTrackerUI()
}

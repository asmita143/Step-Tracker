package com.example.stepcounter.foodScreen


import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.stepcounter.App
import com.example.stepcounter.BottomAppBar
import com.example.stepcounter.R
import com.example.stepcounter.database.StepTrackerViewModel
import com.example.stepcounter.database.entities.ProductInfo
import kotlin.math.roundToInt

@Composable
fun ManualInput(navController: NavHostController, stepTrackerViewModel: StepTrackerViewModel) {

    var food by remember { mutableStateOf(TextFieldValue()) }
    var mass by remember { mutableStateOf(TextFieldValue()) }
    var calories by remember { mutableStateOf(TextFieldValue()) }
    var fat by remember { mutableStateOf(TextFieldValue()) }
    var carbs by remember { mutableStateOf(TextFieldValue()) }
    var sugar by remember { mutableStateOf(TextFieldValue()) }
    var protein by remember { mutableStateOf(TextFieldValue()) }
    var salt by remember { mutableStateOf(TextFieldValue()) }
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween

    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Box {
            Column {
                Text("Add new meal", style = MaterialTheme.typography.titleLarge)
                InputData(
                    value = food, onValueChange = { food = it }, hint = "Food item",
                    KeyboardType.Text
                )
                InputData(
                    value = mass, onValueChange = { mass = it }, hint = "Mass",
                    KeyboardType.Number
                )
                InputData(
                    value = calories, onValueChange = { calories = it }, hint = "Calories per 100 grams",
                    KeyboardType.Number
                )
                InputData(
                    value = fat, onValueChange = { fat = it }, hint = "Fat",
                    KeyboardType.Number
                )
                InputData(
                    value = carbs, onValueChange = { carbs = it }, hint = "Carbohydrates",
                    KeyboardType.Number
                )
                InputData(
                    value = sugar,
                    onValueChange = { sugar = it },
                    hint = "Sugar",
                    KeyboardType.Number,
                )
                InputData(
                    value = protein,
                    onValueChange = { protein = it },
                    hint = "Protein",
                    KeyboardType.Number,
                )
                InputData(
                        value = salt,
                onValueChange = { salt = it },
                hint = "Salt",
                KeyboardType.Number,
                )
            }

        }

        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier.height(200.dp),
            contentAlignment = Alignment.Center
        ) {
            FilledTonalButton(modifier = Modifier
                .fillMaxWidth(0.7f),

                onClick = {
                    // Adds the product manually into internal database
                    if (
                        food.text.isNotEmpty() &&
                        mass.text.isNotEmpty() &&
                        calories.text.isNotEmpty() &&
                        fat.text.isNotEmpty() &&
                        carbs.text.isNotEmpty() &&
                        sugar.text.isNotEmpty() &&
                        protein.text.isNotEmpty() &&
                        salt.text.isNotEmpty()
                        )
                    {
                        stepTrackerViewModel.addProduct(
                            ProductInfo(
                                barcode = "NO BARCODE",
                                calories = calories.text.toInt(),
                                carbohydrate = carbs.text.toDouble(),
                                fat = fat.text.toDouble(),
                                sugars = sugar.text.toDouble(),
                                protein = protein.text.toDouble(),
                                salt = salt.text.toDouble(),
                                productName = food.text
                            )
                        )
                        Toast.makeText(App.appContext, R.string.product_added, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(App.appContext, R.string.fill_all, Toast.LENGTH_SHORT).show()
                    }

                    // Navigate to the next screen
                    navController.navigate("InputDataPage")
                }
            ) {
                Text(
                    "Save and Continue",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Black
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
        )
        {
            BottomAppBar(navController)
        }

        Spacer(modifier = Modifier.weight(1f))

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputData(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    hint: String,
    keyboardType: KeyboardType,
    ) {
    var isClearButtonVisible by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = value,
        onValueChange = {
            onValueChange(it)
            isClearButtonVisible = it.text.isNotEmpty()
        },
        label = { Text(text = hint, color = Color.Black) },

        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType,
            imeAction = ImeAction.Done
        ),
        modifier = Modifier
            .padding(vertical = 4.dp)
            //.background(MaterialTheme.colorScheme.inversePrimary) // Background color
            .fillMaxWidth(0.7f),

        shape = RoundedCornerShape(5.dp),

        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Color.Black,
            focusedBorderColor = Color.Gray,// Customize focused outline color
            unfocusedBorderColor = MaterialTheme.colorScheme.inversePrimary, // Customize unfocused outline color
        ),

        trailingIcon = {
            if (isClearButtonVisible) {
                IconButton(
                    onClick = {
                        onValueChange(TextFieldValue(""))
                        isClearButtonVisible = false
                    }
                ) {
                    Icon(Icons.Filled.Close, "", tint = Color.Blue)
                }
            }
        },
    )
}

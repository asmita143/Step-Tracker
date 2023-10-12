package com.example.stepcounter.firstScreen

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.stepcounter.MainActivity
import com.example.stepcounter.R
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment


@Composable
fun InputDataPage(navController: NavHostController, context: Context) {
    var name by remember { mutableStateOf(TextFieldValue()) }
    var height by remember { mutableStateOf(TextFieldValue()) }
    var weight by remember { mutableStateOf(TextFieldValue()) }
    var targetSteps by remember { mutableStateOf(TextFieldValue()) }
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp



    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween

    ) {
        // Logo Placeholder
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(20.dp)
        ) {

            Image(
                painter = painterResource(id = R.drawable.logo1), // Replace with your image resource
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                contentScale = ContentScale.Fit
            )
        }

        Spacer(modifier = Modifier.height(50.dp))
        Box {
            Column {
                Text("Input User Data", style = MaterialTheme.typography.labelLarge)
                InputData(value = name, onValueChange = { name = it },hint = "Name",KeyboardType.Text)
                InputData(value = height, onValueChange = { height = it }, hint = "Height",KeyboardType.Number)
                InputData(value = weight, onValueChange = { weight = it }, hint = "Weight",KeyboardType.Number)
                InputData(
                    value = targetSteps,
                    onValueChange = { targetSteps = it },
                    hint = "Target Steps",
                    KeyboardType.Number,
                )
            }

        }

        Spacer(modifier = Modifier.weight(1f))
        Box(modifier = Modifier.height(200.dp),
            contentAlignment = Alignment.Center
        ) {
            FilledTonalButton(modifier = Modifier
                .fillMaxWidth(0.7f),

                onClick = {
                    // Save the user data to SharedPreferences
                    saveUserData("user_name", name.text, context)
                    saveUserData("user_height", height.text, context)
                    saveUserData("user_weight", weight.text, context)
                    saveUserData("user_target_steps", targetSteps.text, context)

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
            com.example.stepcounter.BottomAppBar(navController)
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
    keyboardType:KeyboardType,

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


fun saveUserData(key: String, value: String, context: Context) {
    val sharedPreferences = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString(key, value)
    editor.apply()
}




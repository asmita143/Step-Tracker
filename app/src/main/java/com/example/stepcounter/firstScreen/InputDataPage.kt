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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.stepcounter.MainActivity
import com.example.stepcounter.R


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
            .fillMaxSize()
    ) {
        // Logo Placeholder
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(Color.White)
        ) {
            // You can replace this with your app's logo
            Image(
                painter = painterResource(id = R.drawable.logo1), // Replace with your image resource
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(Color(0xFFDCEEF3))
                    .padding(10.dp),
                contentScale = ContentScale.Fit
            )
        }

        Spacer(modifier = Modifier.height(50.dp))

        // Text Fields
        Text("Input User Data:")
        InputData(value = name, onValueChange = { name = it }, hint = "Name")
        InputData(value = height, onValueChange = { height = it }, hint = "Height")
        InputData(value = weight, onValueChange = { weight = it }, hint = "Weight")
        InputData(value = targetSteps, onValueChange = { targetSteps = it }, hint = "Target Steps")

        Spacer(modifier = Modifier.weight(1f))

        Button(modifier = Modifier
            .fillMaxWidth(0.7f)
            .background(MaterialTheme.colorScheme.inversePrimary),
            onClick = {
                // Save the user data to SharedPreferences
                saveUserData("user_name", name.text, context )
                saveUserData("user_height", height.text, context)
                saveUserData("user_weight", weight.text, context)
                saveUserData("user_target_steps", targetSteps.text, context)

                // Navigate to the next screen
                navController.navigate("InputDataPage")
            }
        ) {
            Text("Save and Continue")
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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputData(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    hint: String
) {
    var isClearButtonVisible by remember { mutableStateOf(false) }
    TextField(
        value = value,
        onValueChange = {
            onValueChange(it)
            isClearButtonVisible = it.text.isNotEmpty()
        },
        placeholder = {
            Text(text = hint)
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                // Handle Done button click here
            }
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .background(Color(0xFFCDE5FF)) // Background color
            .shadow(4.dp, shape = RectangleShape), // Shadow and elevation
        shape = RoundedCornerShape(8.dp),
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




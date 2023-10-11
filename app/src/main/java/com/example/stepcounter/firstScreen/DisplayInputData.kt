package com.example.stepcounter.firstScreen

import android.content.Context
import androidx.compose.runtime.Composable


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.stepcounter.MainActivity
import com.example.stepcounter.Screen

@Composable
fun DisplayDataScreen(navController: NavHostController, context: Context) {
    val name = loadUserData("user_name", context)
    val height = loadUserData("user_height", context)
    val weight = loadUserData("user_weight", context)
    val targetSteps = loadUserData("user_target_steps", context)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Button(modifier = Modifier
            .fillMaxWidth(0.7f)
            .background(MaterialTheme.colorScheme.inversePrimary),
            onClick = {
                navController.navigate(Screen.Profile.route)
            }
        ) {
            Text(text = "Edit")

        }
        Text(
            text = "Displaying User Data",
            style = MaterialTheme.typography.labelLarge
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text("User Data:")
        Text("Name: $name")
        Text("Height: $height")
        Text("Weight: $weight")
        Text("Target Steps: $targetSteps")

        Spacer(modifier = Modifier.weight(1f))
    }
}

fun loadUserData(key: String, context: Context): String {
    val sharedPreferences = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
    return sharedPreferences.getString(key, "") ?: ""
}
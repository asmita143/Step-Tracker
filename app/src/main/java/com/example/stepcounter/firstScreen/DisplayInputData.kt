package com.example.stepcounter.firstScreen

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.stepcounter.MainActivity
import com.example.stepcounter.R
import com.example.stepcounter.Screen

@Composable
fun DisplayDataScreen(navController: NavHostController, context: Context) {
    val name = loadUserData("user_name", context)
    val height = loadUserData("user_height", context)
    val weight = loadUserData("user_weight", context)
    val targetSteps = loadUserData("user_target_steps", context)


    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

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

        Spacer(modifier = Modifier.height(20.dp))

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("...$name", style = MaterialTheme.typography.titleLarge)
            Column(
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth()
                    .background(Color.Gray)
                    .padding(5.dp, 20.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Box(modifier = Modifier.background(Color.Red).fillMaxWidth()
                   ) {
                    Row {
                        Image(
                            painter = painterResource(id = R.drawable.ruler),
                            contentDescription = "Camera",
                            modifier = Modifier
                                .size(36.dp),
                            contentScale = ContentScale.Fit
                        )
                        Text("Height: $height", style = MaterialTheme.typography.labelLarge)
                    }
                    
                }
                Box {
                    Row {
                        Image(
                            painter = painterResource(id = R.drawable.weight),
                            contentDescription = "Camera",
                            modifier = Modifier
                                .size(36.dp),
                            contentScale = ContentScale.Fit
                        )
                        Text("Weight: $weight")
                    }
                    }

                Box {
                    Row {
                        Image(
                            painter = painterResource(id = R.drawable.target),
                            contentDescription = "Camera",
                            modifier = Modifier
                                .size(36.dp),
                            contentScale = ContentScale.Fit
                        )
                        Text("Target Steps: $targetSteps")
                    }
                    }


            }

        }
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier.height(200.dp),
            contentAlignment = Alignment.Center
        ) {
            FilledTonalButton(modifier = Modifier
                .fillMaxWidth(0.7f)
                .background(MaterialTheme.colorScheme.inversePrimary),
                onClick = {
                    navController.navigate(Screen.Profile.route)
                }
            ) {
                Text(text = "Edit")

            }
        }
        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
        )
        {
            com.example.stepcounter.BottomAppBar(navController)
        }

    }
}

fun loadUserData(key: String, context: Context): String {
    val sharedPreferences = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
    return sharedPreferences.getString(key, "") ?: ""
}
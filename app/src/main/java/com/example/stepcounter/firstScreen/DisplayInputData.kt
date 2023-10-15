package com.example.stepcounter.firstScreen

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.stepcounter.MainActivity
import com.example.stepcounter.R
import com.example.stepcounter.Screen
import com.example.stepcounter.ui.theme.Typography
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

// Screen for displaying profile of the user
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
                .padding(2.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo1), // Replace with your image resource
                contentDescription = "logo of app",
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                contentScale = ContentScale.Fit
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("...$name", style = Typography.titleLarge)
            Column(
                modifier = Modifier
                    .height(280.dp)
                    .width(400.dp)
                    .padding(20.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                InputDataDisplayBox(icon = R.drawable.ruler, contentDesc = "height","$height cm")
                InputDataDisplayBox(icon = R.drawable.weight, contentDesc = "weight","$weight kg")
                InputDataDisplayBox(icon = R.drawable.target, contentDesc = "target","$targetSteps steps")
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
                    navController.navigate("InputDataPage")
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
// Loading data from shared preferences
fun loadUserData(key: String, context: Context): String {
    val sharedPreferences = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
    return sharedPreferences.getString(key, "") ?: ""
}

// Function to display data
@Composable
private  fun  InputDataDisplayBox(icon: Int,
                                  contentDesc: String,
                                  data: String){
    Box {
        Row (modifier = Modifier
                .defaultMinSize(200.dp)
                .padding(15.dp),
            horizontalArrangement = Arrangement.SpaceBetween){
            Image(
                painter = painterResource(id = icon),
                contentDescription = contentDesc,
                modifier = Modifier
                    .size(32.dp),
            )
            Spacer(modifier = Modifier.size(20.dp))
            Text( "$data", style = Typography.labelLarge)
        }
    }
}
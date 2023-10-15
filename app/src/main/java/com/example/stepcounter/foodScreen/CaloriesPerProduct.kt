package com.example.stepcounter.foodScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.stepcounter.database.StepTrackerViewModel
import com.example.stepcounter.database.entities.Meal
import com.example.stepcounter.ui.theme.Typography

@Composable
fun CaloriesPerProduct(navController: NavHostController, id: String, foodViewModel: StepTrackerViewModel) {
    val meal  = foodViewModel.getMealById(id.toInt()).observeAsState(listOf())
    val mealInfo = meal.value
    var name by remember { mutableStateOf("") }
    var mass by remember { mutableIntStateOf(0) }
    var energy by remember { mutableIntStateOf(0) }
    var carbohydrate by remember { mutableDoubleStateOf(0.0) }
    var salt by remember { mutableDoubleStateOf(0.0) }
    var protein by remember { mutableDoubleStateOf(0.0) }
    var fat by remember { mutableDoubleStateOf(0.0) }
    var sugar by remember { mutableDoubleStateOf(0.0) }

    for(i in mealInfo) {
        name = i.name
        mass = i.mass
        energy = i.calories
        carbohydrate = i.carbohydrate
        salt = i.salt
        protein = i.protein
        fat = i.fats
        sugar = i.sugar
    }

    Column {
        Card(modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .shadow(
                elevation = 20.dp,
                shape = RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp),
                spotColor = Color.Black
            ),
        ) {
            Row(
                modifier= Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = name,  style = Typography.bodyLarge, modifier = Modifier.padding(20.dp))
                Text(text = "$mass gram", style = Typography.bodyLarge, modifier = Modifier.padding(20.dp))
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        Card(modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(20.dp)
            .shadow(
                elevation = 20.dp,
                shape = RectangleShape,
                spotColor = Color.Black
            ),
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
            ),
        ) {
            Column {
                Row( modifier= Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically){
                    Text(text = "Nutrients", style = Typography.bodyLarge,  modifier = Modifier.padding(20.dp))
                    Text(text = "Percentage", style = Typography.bodyLarge, modifier = Modifier.padding(20.dp))
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height = 4.dp)
                        .background(color = Color.LightGray)
                )
                Row( modifier= Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically){
                    Text(text = "Energy.........", modifier = Modifier.padding(20.dp))
                    Text(text = "$energy Kcal", modifier = Modifier.padding(20.dp))
                }
                Row( modifier= Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically){
                    Text(text = "Fat.........", modifier = Modifier.padding(20.dp))
                    Text(text = "${(100.0/mass.toDouble()) * fat}%", modifier = Modifier.padding(20.dp))
                }
                Row( modifier= Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Carbohydrate.........", modifier = Modifier.padding(20.dp))
                    Text(text = "${(100.0/mass.toDouble()) * carbohydrate}%", modifier = Modifier.padding(20.dp))
                }
                Row ( modifier= Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically){
                    Text(text = "Protein.........", modifier = Modifier.padding(20.dp))
                    Text(text = "${(100.0/mass.toDouble()) * protein}%", modifier = Modifier.padding(20.dp))
                }
                Row ( modifier= Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically){
                    Text(text = "Sugar.........", modifier = Modifier.padding(20.dp))
                    Text(text = "${(100.0/mass.toDouble()) * sugar}%", modifier = Modifier.padding(20.dp))
                }
                Row ( modifier= Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically){
                    Text(text = "Salt.........", modifier = Modifier.padding(20.dp))
                    Text(text = "${(100/mass.toDouble()) * salt}%", modifier = Modifier.padding(20.dp))
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
        )
        {
            com.example.stepcounter.BottomAppBar(navController)
        }
    }
}
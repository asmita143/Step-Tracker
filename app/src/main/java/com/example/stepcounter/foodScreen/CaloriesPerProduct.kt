package com.example.stepcounter.foodScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun CaloriesPerProduct() {
    Column {
        Box(modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
            .padding(12.dp)
            .background(Color.LightGray)
        ) {
            Row(
                modifier= Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Orange", modifier = Modifier.padding(20.dp))
                Text(text = "150 gram",modifier = Modifier.padding(20.dp))
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        Card(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
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
                    Text(text = "Fat.........", modifier = Modifier.padding(20.dp))
                    Text(text = "20%", modifier = Modifier.padding(20.dp))
                }
                Row( modifier= Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Carbohydrate.........", modifier = Modifier.padding(20.dp))
                    Text(text = "20%", modifier = Modifier.padding(20.dp))
                }
                Row ( modifier= Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically){
                    Text(text = "Protein.........", modifier = Modifier.padding(20.dp))
                    Text(text = "20%", modifier = Modifier.padding(20.dp))
                }
                Row ( modifier= Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically){
                    Text(text = "Sugar.........", modifier = Modifier.padding(20.dp))
                    Text(text = "20%", modifier = Modifier.padding(20.dp))
                }
                Row ( modifier= Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically){
                    Text(text = "Salt.........", modifier = Modifier.padding(20.dp))
                    Text(text = "20%", modifier = Modifier.padding(20.dp))
                }

            }
        }
    }
}
package com.example.stepcounter.firstScreen

import androidx.compose.foundation.Image
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.stepcounter.R

@Preview
@Composable
fun InputDataPage() {
    val nameField = remember { mutableStateOf(TextFieldValue()) }
    val heightField = remember { mutableStateOf(TextFieldValue()) }
    val weightField = remember { mutableStateOf(TextFieldValue()) }
    val targetStepsField = remember { mutableStateOf(TextFieldValue()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
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
                    .background(Color(0xFFDCEEF3)),
                contentScale = ContentScale.Fit
            )
        }

        Spacer(modifier = Modifier.height(50.dp))

        // Text Fields
        InputData(
            value = nameField.value,
            onValueChange = { nameField.value = it },
            hint = "Name"
        )
        Spacer(modifier = Modifier.height(16.dp))

        InputData(
            value = heightField.value,
            onValueChange = { heightField.value = it },
            hint = "Height"
        )
        Spacer(modifier = Modifier.height(16.dp))

        InputData(
            value = weightField.value,
            onValueChange = { weightField.value = it },
            hint = "Weight"
        )
        Spacer(modifier = Modifier.height(16.dp))

        InputData(
            value = targetStepsField.value,
            onValueChange = { targetStepsField.value = it },
            hint = "Target Steps"
        )

        Spacer(modifier = Modifier.height(25.dp))


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



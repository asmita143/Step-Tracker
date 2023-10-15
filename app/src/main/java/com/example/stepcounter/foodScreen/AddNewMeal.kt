package com.example.stepcounter.foodScreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.stepcounter.R
import com.example.stepcounter.api.ScannedProduct
import com.example.stepcounter.barcodeScanner.BarcodeScanner
import com.example.stepcounter.barcodeScanner.BarcodeViewModel
import com.example.stepcounter.database.StepTrackerViewModel
import com.example.stepcounter.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewMeal(navController: NavHostController,
               barcodeViewModel: BarcodeViewModel,
               stepTrackerViewModel: StepTrackerViewModel) {
    var name by remember { mutableStateOf("") }
    var mass by remember { mutableStateOf("") }
    val isScanned by barcodeViewModel.liveData.observeAsState(false)
    val barcode by barcodeViewModel.liveData.observeAsState(null)
    val productInfo by barcodeViewModel.product.observeAsState(null)


    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .padding(top = 50.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            Modifier
                .fillMaxWidth(0.90f)
                .padding(5.dp)
        ) {
            Row() {
                Text(
                    text = "Add new meal",
                    style = Typography.labelLarge,
                    modifier = Modifier.padding(start = 20.dp, top = 10.dp, bottom = 10.dp)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 20.dp, top = 10.dp, bottom = 10.dp),
                    contentAlignment = Alignment.CenterEnd

                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_camera),
                        contentDescription = "Camera for scanning items",
                        modifier = Modifier
                            .size(30.dp)
                            .clickable {
                                BarcodeScanner().startScanning(barcodeViewModel)
                                Log.d("Scanned", isScanned.toString())
                            },
                        contentScale = ContentScale.Fit
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth(0.90f),
            contentAlignment = Alignment.Center
        ) {
            Column() {
                OutlinedTextField(
                    value = name,
                    singleLine = true,
                    onValueChange = { name = it },
                    trailingIcon = {
                          Image(
                              painter = painterResource(id = R.drawable.baseline_search_24),
                              contentDescription = "Search for a product",
                              modifier = Modifier
                                  .clickable {
                                      val list = stepTrackerViewModel.getProductsByName(name)
                                      Log.d("Search", list.value.toString())
                                      Log.d("Search", name)
                                      Log.d("Search hardcode", stepTrackerViewModel.getProductsByName("Breakfast Cereal, Wheat Flakes, Nuts, Almond, Nut Flakers, Lidl").value.toString())
                                  },
                              )

                    },
                    label = { Text(text = "Food Item", color = Color.Black) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = Color.Black,
                        focusedBorderColor = Color.Gray,// Customize focused outline color
                        unfocusedBorderColor = MaterialTheme.colorScheme.inversePrimary, // Customize unfocused outline color
                    ),
                    textStyle = TextStyle(fontSize = 18.sp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.primary,
                            shape = MaterialTheme.shapes.small
                        )
                        .padding(5.dp)
                )
                OutlinedTextField(
                    value = mass,
                    label = { Text("Mass in grams", color = Color.Black) },
                    onValueChange = { mass = it },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = Color.Black,
                        focusedBorderColor = Color.Gray,// Customize focused outline color
                        unfocusedBorderColor = MaterialTheme.colorScheme.inversePrimary, // Customize unfocused outline color
                    ),
                    textStyle = TextStyle(fontSize = 18.sp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.primary,
                            shape = MaterialTheme.shapes.small
                        )
                        .padding(5.dp)
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .fillMaxHeight(0.5f),
                    contentAlignment = Alignment.Center
                ) {
                    SmallFloatingActionButton(
                        onClick = { navController.navigate("MealOfDay") },
                        modifier = Modifier
                            .fillMaxWidth(),
                    ) {
                        Row {
                            Text("Add item")
                            Spacer(modifier = Modifier.width(10.dp))
                            Icon(
                                Icons.Filled.Add,
                                "Small floating action button.",
                            )
                        }
                    }
                }
            }
        }
        Box() {
            Column {
                Text(text = "Not in the list?", style = MaterialTheme.typography.labelLarge, modifier = Modifier.padding(8.dp))
                TextButton(
                    onClick = { navController.navigate("ManualInput") }
                ) {
                    Text(
                        "Add manually",
                        style = Typography.labelSmall,
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.inversePrimary)
                            .padding(8.dp)

                    )
                }
            }

        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
        ) {
            com.example.stepcounter.BottomAppBar(navController)
        }

    }
}

@Composable
fun DisplayData(viewModel: BarcodeViewModel = BarcodeViewModel()) {
    val data by viewModel.liveData.observeAsState()
    data?.let { Log.d("LIVE DATA in display", it) }

    Column {
        Text(text = "Data from LiveData: $data")
    }
}

@Composable
fun CheckTheProductDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
) {
    AlertDialog(
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}

@Composable
fun CheckTheProduct(scannedProduct: ScannedProduct) {
    val openAlertDialog = remember { mutableStateOf(false) }

    when {
        openAlertDialog.value -> {
            CheckTheProductDialog(
                onDismissRequest = { openAlertDialog.value = false },
                onConfirmation = {
                    openAlertDialog.value = false
                    println("Product added")
                },
                dialogTitle = "Is it correct?",
                dialogText = "Are you looking for: ${scannedProduct.product?.productName}"
            )
        }
    }
}
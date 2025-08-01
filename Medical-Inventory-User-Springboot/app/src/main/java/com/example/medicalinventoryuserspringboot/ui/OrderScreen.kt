package com.example.medicalinventoryuserspringboot.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.medicalinventoryuserspringboot.Dto.ProductDTO
import com.example.medicalinventoryuserspringboot.ResultState
import com.example.medicalinventoryuserspringboot.viewModel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderScreen(userViewModel: UserViewModel, navController: NavHostController) {
    val userState by userViewModel.user.collectAsState()
    val productState by userViewModel.productListState.collectAsState()
    val orderState by userViewModel.orderResult.collectAsState()

    val context = LocalContext.current

    val selectedProduct = remember { mutableStateOf<ProductDTO?>(null) }
    val quantity = remember { mutableStateOf("1") }

    val user = userState.isSuccessful
    val userId = user?.id
    Log.d("OrderScreen", "ViewModel.loggedInUsername = ${userViewModel.loggedInUsername}")

    LaunchedEffect(user) {
        if (user == null) {
            val rememberedUsername = userViewModel.loggedInUsername
            if (!rememberedUsername.isNullOrEmpty()) {
                userViewModel.getUser(rememberedUsername)
            } else {
                Log.e("OrderScreen", "No username available to fetch user data.")
            }
        } else {
            Log.d("OrderScreen", "Loaded user id: ${user.id}")
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Place Order", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        when (val state = productState) {
            is ResultState.Loading -> CircularProgressIndicator()
            is ResultState.Error -> Text("Error: ${state.message}")
            is ResultState.Success -> {
                var expanded by remember { mutableStateOf(false) }

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    TextField(
                        value = selectedProduct.value?.name ?: "",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Select Product") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier.menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        state.data.forEach { product ->
                            DropdownMenuItem(
                                text = { Text(product.name) },
                                onClick = {
                                    selectedProduct.value = product
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = quantity.value,
            onValueChange = { quantity.value = it },
            label = { Text("Quantity") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val productId = selectedProduct.value?.id
                val qty = quantity.value.toIntOrNull()

                if (userId == null || userId == 0) {
                    Toast.makeText(context, "Invalid user (not loaded)", Toast.LENGTH_SHORT).show()
                } else if (productId == null) {
                    Toast.makeText(context, "Please select a product", Toast.LENGTH_SHORT).show()
                } else if (qty == null || qty <= 0) {
                    Toast.makeText(context, "Enter valid quantity", Toast.LENGTH_SHORT).show()
                } else {
                    userViewModel.placeOrder(userId, productId, qty)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Place Order")
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (val order = orderState) {
            is ResultState.Loading -> CircularProgressIndicator()
            is ResultState.Error -> Text("Error placing order: ${order.message}", color = Color.Red)
            is ResultState.Success -> Text("Order placed successfully (ID: ${order.data.id})", color = Color.Green)
            null -> {}
        }
    }
}

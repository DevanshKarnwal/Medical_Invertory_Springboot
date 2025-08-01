package com.example.medicalinventoryuserspringboot.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.medicalinventoryuserspringboot.ResultState
import com.example.medicalinventoryuserspringboot.Dto.UserStockDTO
import com.example.medicalinventoryuserspringboot.viewModel.UserViewModel

@Composable
fun AllProductScreen(
    viewModel: UserViewModel,
    navController: NavHostController
) {
    val userState by viewModel.user.collectAsState()
    val stockState by viewModel.stockState.collectAsState()

    val userId = userState.isSuccessful?.id ?:0

    LaunchedEffect(userId) {
        if (userId != 0) {
            viewModel.loadUserStock(userId)
        }
    }

    when (stockState) {
        is ResultState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is ResultState.Success -> {
            val stockList = (stockState as ResultState.Success<List<UserStockDTO>>).data
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Your Stock",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                stockList.forEach { stock ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Product: ${stock.productName}")
                            Text("Quantity: ${stock.quantity}")
                        }
                    }
                }
            }
        }

        is ResultState.Error -> {
            val errorMsg = (stockState as ResultState.Error).message
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Error: $errorMsg", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}

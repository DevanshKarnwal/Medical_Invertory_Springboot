package com.example.medicalinventoryadminspringboot.ui

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.medicalinventoryadminspringboot.viewModel.AdminViewModel
import java.time.LocalTime


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DashboardScreen(adminViewModel: AdminViewModel = hiltViewModel()) {
    val productView = adminViewModel.getAllProducts.collectAsState()
    val userView = adminViewModel.getAllUsers.collectAsState()
    val inventoryView = adminViewModel.getAllInventory.collectAsState()

    LaunchedEffect(Unit) {
        Log.d("dashboard-check", "LaunchedEffect triggered")
        if (productView.value.isSuccessful.isEmpty()) {
            Log.d("dashboard-check", "Calling getAllProducts()")
            adminViewModel.getAllProducts()
        }
        if (userView.value.isSuccessful.isEmpty()) adminViewModel.getAllUser()
        if (inventoryView.value.isSuccessful.isEmpty()) adminViewModel.getAllInventories()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(100.dp))

        // Manual Grid: 2x2 cards
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatCard(
                    title = "Total Products",
                    value = productView.value.isSuccessful.size.toString(),
                    backgroundColor = Color(0xFFB3E5FC),
                    textColor = Color(0xFF01579B),
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Low Stock Items",
                    value = inventoryView.value.isSuccessful.count { it.quantity < 10 }.toString(),
                    backgroundColor = Color(0xFFFFCDD2),
                    textColor = Color(0xFFC62828),
                    modifier = Modifier.weight(1f)
                )
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatCard(
                    title = "Active Users",
                    value = userView.value.isSuccessful.count {
                        !it.isBlocked &&
                                !it.isWaiting
                                }.toString(),
                    backgroundColor = Color(0xFFC8E6C9),
                    textColor = Color(0xFF2E7D32),
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Blocked Users",
                    value = userView.value.isSuccessful.count { it.isBlocked }.toString(),
                    backgroundColor = Color(0xFFD1C4E9),
                    textColor = Color(0xFF6A1B9A),
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}




@Composable
fun StatCard(
    title: String,
    value: String,
    backgroundColor: Color,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .aspectRatio(1f),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, fontSize = 14.sp, color = textColor)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
        }
    }
}

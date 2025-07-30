package com.example.medicalinventoryadminspringboot.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.medicalinventoryadminspringboot.model.Order
import com.example.medicalinventoryadminspringboot.model.Users
import com.example.medicalinventoryadminspringboot.viewModel.AdminViewModel

@Composable
fun AllOrdersScreen(viewModel: AdminViewModel = hiltViewModel<AdminViewModel>()) {
    val orderState = viewModel.getAllOrders.collectAsState()

    // Load orders when screen is first composed
    LaunchedEffect(Unit) {
        viewModel.getAllOrders()
    }

    Column(modifier = Modifier.padding(16.dp)) {
        when {
            orderState.value.isError.isNotEmpty() -> {
                Text(text = "Error: ${orderState.value.isError}", color = Color.Red)
            }

            orderState.value.isSuccessful.isEmpty() -> {
                Text(text = "Loading orders...", style = MaterialTheme.typography.bodyMedium)
            }

            else -> {
                AllOrdersShowScreen(
                    orders = orderState.value.isSuccessful,
                    onApprove = { orderId -> viewModel.approveOrder(orderId) },
                    onReject = { orderId -> viewModel.deleteOrder(orderId) }
                )
            }
        }
    }
}

@Composable
fun AllOrdersShowScreen(
    orders: List<Order>,
    onApprove: (Int) -> Unit,
    onReject: (Int) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        items(orders) { order ->
            OrderCard(order = order, onApprove = onApprove, onReject = onReject)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun OrderCard(
    order: Order,
    onApprove: (Int) -> Unit,
    onReject: (Int) -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Top Row: Order ID + Status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Order #ORD${order.id.toString().padStart(3, '0')}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )

                // Status chip
                Box(
                    modifier = Modifier
                        .background(
                            color = if (order.isApproved) Color(0xFF4CAF50) else Color(0xFFFFC107),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = if (order.isApproved) "APPROVED" else "PENDING",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Customer Info
            Text(
                text = "Customer: ${order.user?.name ?: "Unknown"}",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "Date: ${order.generationDate}",
                fontSize = 12.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Item Info
            Text("Items:", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "• ${order.quantity}× ${order.product?.name ?: "Unknown"}",
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Total: ₹${order.price}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF673AB7)
                )

                Row {
                    TextButton(onClick = { onApprove(order.id) }) {
                        Text("Approve")
                    }
                    TextButton(onClick = { onReject(order.id) }) {
                        Text("Reject")
                    }
                }
            }
        }
    }
}

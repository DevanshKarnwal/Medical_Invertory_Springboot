package com.example.medicalinventoryadminspringboot.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.medicalinventoryadminspringboot.Dto.OrderDTO
import com.example.medicalinventoryadminspringboot.viewModel.AdminViewModel

@Composable
fun AllOrdersScreen(viewModel: AdminViewModel ) {
    val orderState = viewModel.getAllOrders.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getAllOrders()
    }

    Column(modifier = Modifier.padding(16.dp)) {
        when {
            orderState.value.isError.isNotEmpty() -> {
                Text("Error: ${orderState.value.isError}", color = Color.Red)
            }

            orderState.value.isSuccessful.isEmpty() -> {
                Text("Loading orders...", style = MaterialTheme.typography.bodyMedium)
            }

            else -> {
                AllOrdersShowScreen(
                    orders = orderState.value.isSuccessful,
                    onApprove = { viewModel.approveOrder(it) },
                    onReject = { viewModel.deleteOrder(it) }
                )
            }
        }
    }
}

@Composable
fun AllOrdersShowScreen(
    orders: List<OrderDTO>,
    onApprove: (Int) -> Unit,
    onReject: (Int) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(orders) { order ->
            OrderCard(order = order, onApprove = onApprove, onReject = onReject)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun OrderCard(
    order: OrderDTO,
    onApprove: (Int) -> Unit,
    onReject: (Int) -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF7F7F7)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Order #${order.id}",
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text("User: ${order.user?.name ?: "N/A"}", fontSize = 16.sp)
            Text("Product: ${order.product?.name ?: "N/A"}", fontSize = 14.sp)
            Text("Qty: ${order.quantity}", fontSize = 14.sp)
            Text("₹ ${order.price}", fontSize = 14.sp)

            if (order.approved) {
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .background(Color(0xFFD0F0C0), RoundedCornerShape(12.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text("Approved", color = Color(0xFF2E7D32), fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    onClick = { onApprove(order.id) },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(0xFF388E3C)
                    ),
                    shape = CircleShape
                ) {
                    Icon(Icons.Default.Check, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Approve")
                }

                Spacer(modifier = Modifier.width(8.dp))

                OutlinedButton(
                    onClick = { onReject(order.id) },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(0xFFD32F2F)
                    ),
                    shape = CircleShape
                ) {
                    Icon(Icons.Default.Close, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Reject")
                }
            }
        }
    }
}

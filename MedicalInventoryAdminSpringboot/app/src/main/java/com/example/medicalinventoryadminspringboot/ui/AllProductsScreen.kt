package com.example.medicalinventoryadminspringboot.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.example.medicalinventoryadminspringboot.model.Product
import com.example.medicalinventoryadminspringboot.viewModel.AdminViewModel

@Composable
fun AllProductsScreen(adminViewModel: AdminViewModel = hiltViewModel() ) {
        val productView = adminViewModel.getAllProducts.collectAsState()
        val  getAllInventory= adminViewModel.getAllInventory.collectAsState()
    LaunchedEffect(Unit) {
        adminViewModel.getAllProducts()
        adminViewModel.getAllInventories()
    }
    LazyColumn(modifier = Modifier.fillMaxWidth().padding(16.dp)) {

        items(productView.value.isSuccessful) {
            item->
                var inventory =0
                if(getAllInventory.value.isSuccessful.isNotEmpty()){
                    inventory = getAllInventory.value.isSuccessful.filter { it.product == item.id }.size
                }
            ProductCard(product = item, stock = inventory)

        }
    }

}

@Composable
fun ProductCard(product: Product, stock: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Product initials circle
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Color(0xFFB39DDB), shape = RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = product.name.take(2).uppercase(),
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Id: ${product.productId}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                Text(
                    text = "$${product.price}",
                    color = Color(0xFF3F51B5),
                    fontWeight = FontWeight.Bold
                )
            }

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "Stock:",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = stock.toString(),
                    color = Color(0xFF2E7D32),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}


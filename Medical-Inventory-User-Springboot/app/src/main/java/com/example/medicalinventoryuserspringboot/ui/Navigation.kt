package com.example.medicalinventoryuserspringboot.ui

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.medicalinventoryuserspringboot.viewModel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Navigation(adminViewModel: UserViewModel = hiltViewModel<UserViewModel>()) {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val hideBottomBarRoutes = listOf(
        Routes.LogInScreen::class.qualifiedName,
        Routes.SignUp::class.qualifiedName
    )
    var index by remember { mutableStateOf(0) }
    val bottomNavItems = listOf(
        BottomNavItem("Products", Icons.Default.Inventory),
        BottomNavItem("Orders", Icons.Default.ShoppingCart),
        BottomNavItem("Sell History", Icons.Default.History),
        BottomNavItem("User Details", Icons.Default.AccountCircle)
    )

    Scaffold(
        topBar = {
            if (hideBottomBarRoutes.none { currentRoute?.startsWith(it.orEmpty()) == true }) {
                CenterAlignedTopAppBar(
                    title = { Text(bottomNavItems[index].name) },
                )
            }
        },
        bottomBar = {
            if (hideBottomBarRoutes.none { currentRoute?.startsWith(it.orEmpty()) == true }) {
                BottomAppBar {
                    bottomNavItems.forEachIndexed { current, item ->
                        NavigationBarItem(
                            selected = index == current,
                            onClick = {
                                index = current

                                when (index) {
                                    0 -> navController.navigate(Routes.AllProducts)
                                    1 -> navController.navigate(Routes.Orders)
                                    2 -> navController.navigate(Routes.SellHistory)
                                    3 -> navController.navigate(Routes.UserDetails)
                                }
                            },
                            icon = { Icon(item.icon, contentDescription = item.name) },
                            label = { Text(item.name) }
                        )
                    }
                }
            }
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = Routes.SignUp,
            modifier = Modifier.padding(it)
        ) {
            composable<Routes.LogInScreen> {
                LogInScreen(navController = navController)
            }
            composable<Routes.SignUp> {
                SignUpScreen(navController = navController)
            }

            composable<Routes.SellHistory> {
                SellHistory()
            }
            composable<Routes.Orders> {
                OrderScreen()
            }
            composable<Routes.AllProducts> {
                AllProductScreen()
            }
            composable<Routes.UserDetails> {
                UserDetails()
            }

        }
    }
}


data class BottomNavItem(
    val name: String,
    val icon: ImageVector
)
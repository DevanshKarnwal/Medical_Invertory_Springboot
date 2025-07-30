package com.example.medicalinventoryadminspringboot.ui

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.medicalinventoryadminspringboot.viewModel.AdminViewModel
import kotlinx.coroutines.launch
import okhttp3.Route

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Navigation(adminViewModel: AdminViewModel = hiltViewModel<AdminViewModel>()) {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val hideBottomBarRoutes = listOf(
        Routes.LogInScreen::class.qualifiedName,
    )
    var index by remember { mutableStateOf(0) }
    val bottomNavItems = listOf(
        BottomNavItem("Dashboard", Icons.Default.Home),
        BottomNavItem("Products", Icons.Default.Category),
        BottomNavItem("Users", Icons.Default.Person),
        BottomNavItem("Orders", Icons.Default.ShoppingCart)
    )

    var adminView = adminViewModel.adminUser.collectAsState()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                // Header with username and Edit button
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Hello, ${adminView.value.isSuccessful.name}",
                        style = MaterialTheme.typography.titleMedium
                    )
                    TextButton(onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate("profile")
                    }) {
                        Text("Edit Profile")
                    }
                }

            }
        }
    ) {
        Scaffold(
            topBar = {
                if (hideBottomBarRoutes.none { currentRoute?.startsWith(it.orEmpty()) == true }) {
                    CenterAlignedTopAppBar(
                        title = { Text(bottomNavItems[index].name) },
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(Icons.Default.Menu, contentDescription = "Menu")
                            }
                        }
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
                                        0 -> navController.navigate(Routes.Dashboard)
                                        1 -> navController.navigate(Routes.AllProducts)
                                        2 -> navController.navigate(Routes.AllUsers)
                                        3 -> navController.navigate(Routes.AllOrders)
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
                startDestination = Routes.LogInScreen,
                modifier = Modifier.padding(it)
            ) {
                composable<Routes.LogInScreen> {
                    LogInScreen(navController = navController)
                }
                composable<Routes.Dashboard> {
                    DashboardScreen()
                }
                composable<Routes.AllUsers> {
                    AllUsersScreens()
                }
                composable<Routes.AllProducts> {
                    AllProductsScreen()
                }
                composable<Routes.AllOrders> {
                    AllOrdersScreen()
                }


            }
        }
    }
}

data class BottomNavItem(
    val name: String,
    val icon: ImageVector
)
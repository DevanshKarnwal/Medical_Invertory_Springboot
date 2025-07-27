package com.example.medicalinventoryadminspringboot.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.LogInScreen) {
        composable<Routes.LogInScreen> {
            LogInScreen()
        }

    }

}
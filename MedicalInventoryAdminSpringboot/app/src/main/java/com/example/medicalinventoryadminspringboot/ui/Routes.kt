package com.example.medicalinventoryadminspringboot.ui

import androidx.navigation.NavHostController
import kotlinx.serialization.Serializable

sealed class Routes {
    @Serializable
    object LogInScreen : Routes()

}
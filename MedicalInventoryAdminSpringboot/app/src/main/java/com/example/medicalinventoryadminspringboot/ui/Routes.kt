package com.example.medicalinventoryadminspringboot.ui

import androidx.navigation.NavHostController
import kotlinx.serialization.Serializable


sealed class Routes {
    @Serializable
    object LogInScreen : Routes()

    @Serializable
    object Dashboard : Routes()

    @Serializable
    object AllUsers : Routes()

    @Serializable
    object AllProducts : Routes()

    @Serializable
    object AllOrders : Routes()

    @Serializable
    object AllInventory : Routes()

}
package com.example.medicalinventoryuserspringboot.ui

import kotlinx.serialization.Serializable


sealed class Routes {
    @Serializable
    object LogInScreen : Routes()

    @Serializable
    object SignUp : Routes()

    @Serializable
    object AllProducts : Routes()

    @Serializable
    object SellHistory : Routes()

    @Serializable
    object UserDetails : Routes()

    @Serializable
    object Stock : Routes()

    @Serializable
    object Orders : Routes()

}
package com.example.medicalinventoryuserspringboot.model


data class Users(
    val id: Int? = null,
    var name: String,
    var email: String = "",
    var password: String,
    var isBlocked: Boolean? = null,
    var isWaiting: Boolean? = null,
    var number: String? = null,
    var pincode: String? = null,
)

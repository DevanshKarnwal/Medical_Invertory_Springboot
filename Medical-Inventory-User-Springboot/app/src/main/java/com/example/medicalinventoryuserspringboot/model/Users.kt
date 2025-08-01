package com.example.medicalinventoryuserspringboot.model


data class Users(
    val id: Int? = null,
    var name: String,
    var email: String = "",
    var password: String,
    val creationDate: String?="",
    var isBlocked: Boolean,
    var isWaiting: Boolean,
    var number: String? = null,
    var pincode: String? = null,
    var role: List<Roles>? = null
)

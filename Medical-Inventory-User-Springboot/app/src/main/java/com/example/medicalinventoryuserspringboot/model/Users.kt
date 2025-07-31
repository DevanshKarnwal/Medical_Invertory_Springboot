package com.example.medicalinventoryuserspringboot.model

import java.sql.Time


data class Users(
    val id: Int? = null,
    var name: String,
    var email: String = "",
    var password: String,
    val creationTime: String?="",
    var isBlocked: Boolean = true,
    var isWaiting: Boolean = true,
    var number: String? = null,
    var pincode: String? = null,
    var role: List<Roles> = emptyList()
)

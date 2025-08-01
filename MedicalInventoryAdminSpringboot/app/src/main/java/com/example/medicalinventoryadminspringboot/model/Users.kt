package com.example.medicalinventoryadminspringboot.model

import com.example.medicalinventoryadminspringboot.Dto.UserSummary


data class Users(
    val id: Int,
    var name: String,
    var email: String = "",
    var password: String,
    val creationDate: String?= "2024-07-31",
    var isBlocked: Boolean,
    var isWaiting: Boolean,
    var number: String? = null,
    var pincode: String? = null,
    var role: List<Roles>
)
fun Users.toUserSummary(): UserSummary {
    return UserSummary(
        id = this.id,
        name = this.name,
        email = this.email,
        isBlocked = isBlocked,
        isWaiting = isWaiting,
        role = this.role,
        creationDate = this.creationDate ?: getCurrentDate(),
        number = this.number,
        pincode = this.pincode,
        password = this.password
    )
}

fun getCurrentDate(): String {
    val formatter = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
    return formatter.format(java.util.Date())
}

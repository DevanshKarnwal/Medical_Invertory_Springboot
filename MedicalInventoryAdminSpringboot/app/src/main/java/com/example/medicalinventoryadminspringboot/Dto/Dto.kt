package com.example.medicalinventoryadminspringboot.Dto


import com.example.medicalinventoryadminspringboot.model.Roles
import com.example.medicalinventoryadminspringboot.model.Users

@kotlinx.serialization.Serializable
data class OrderDTO(
    val id: Int,
    val generationDate: String,
    val quantity: Int,
    val product: ProductSummary?,
    val user: UserSummary?,
    val price: Double,
    val approved: Boolean
)

@kotlinx.serialization.Serializable
data class UserSummary(
    val id: Int,
    val name: String,
    val email: String,
    val isBlocked: Boolean = true,
    val isWaiting: Boolean = true,
    val role : List<Roles>,
    val creationDate: String,
    val number: String? = null,
    val pincode: String? = null,
    var password: String,
)

@kotlinx.serialization.Serializable
data class ProductSummary(
    val id: Int,
    val name: String,
    val price: Double
)

fun UserSummary.toUsers(): Users {
    return Users(
        id = this.id,
        name = this.name,
        email = this.email,
        isBlocked = this.isBlocked,
        isWaiting = this.isWaiting,
        role = this.role,
        password = this.password,
        creationDate = this.creationDate,
        number = this.number,
        pincode = this.pincode
    )

}
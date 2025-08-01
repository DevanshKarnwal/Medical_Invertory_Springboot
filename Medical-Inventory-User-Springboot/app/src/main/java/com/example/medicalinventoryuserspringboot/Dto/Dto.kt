package com.example.medicalinventoryuserspringboot.Dto


import com.example.medicalinventoryuserspringboot.model.Roles
import com.example.medicalinventoryuserspringboot.model.Users
import kotlinx.serialization.Serializable

@Serializable
data class OrderDTO(
    val id: Int,
    val generationDate: String,
    val quantity: Int,
    val product: ProductSummary?,
    val user: UserSummary?,
    val price: Double,
    val approved: Boolean
)
@Serializable
data class OrderRequest(
    val userId: Int,
    val productId: Int,
    val quantity: Int
)

@Serializable
data class UserSummary(
    val id: Int,
    val name: String,
    val email: String,
    val isBlocked: Boolean = true,
    val isWaiting: Boolean = true,
    val role : List<Roles>,
    val cerationDate: String,
    val number: String? = null,
    val pincode: String? = null,
    var password: String,
)

@Serializable
data class ProductSummary(
    val id: Int,
    val name: String,
    val price: Double
)

data class PlaceOrderRequest(
    val userId: Int,
    val productId: Int,
    val quantity: Int
)

@Serializable
data class ProductDTO(
    val id: Int,
    val productId: String,
    val name: String,
    val price: Double,
    val category: String
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
        creationDate = this.cerationDate,
        number = this.number,
        pincode = this.pincode
    )

}
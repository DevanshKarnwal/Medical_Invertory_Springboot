package com.example.medicalinventoryuserspringboot.Dto


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
data class UserSummary(
    val id: Int,
    val name: String,
    val email: String
)

@Serializable
data class ProductSummary(
    val id: Int,
    val name: String,
    val price: Double
)


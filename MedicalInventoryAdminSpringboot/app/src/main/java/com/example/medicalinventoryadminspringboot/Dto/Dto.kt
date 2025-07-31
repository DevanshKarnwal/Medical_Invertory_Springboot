package com.example.medicalinventoryadminspringboot.Dto


import kotlinx.serialization.Serializable

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
    val email: String
)

@kotlinx.serialization.Serializable
data class ProductSummary(
    val id: Int,
    val name: String,
    val price: Double
)


package com.example.medicalinventoryuserspringboot.Dto

data class UserStockDTO(
    val userId: Int,
    val productId: Int,
    val productName: String,
    val productPrice: Double,
    val quantity: Int
)
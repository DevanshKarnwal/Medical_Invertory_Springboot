package com.example.medicalinventoryadminspringboot.model

import java.sql.Time
import java.time.LocalDateTime

data class Order(
    val id: Int = 0,
    val isApproved: Boolean = false,
    val generationDate: String="" ,
    val quantity: Int = 0,
    val product: Product? = null,
    val user: Users? = null,
    val price: Double = 0.0
)
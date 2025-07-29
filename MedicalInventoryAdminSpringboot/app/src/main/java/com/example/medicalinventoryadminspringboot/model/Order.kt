package com.example.medicalinventoryadminspringboot.model

import java.sql.Time
import java.time.LocalDateTime

data class Order(
    val id: Int = 0,
    val isApproved: Boolean = false,
    val generationDate: Time ,
    val quantity: Int = 0,
    val product: Int? = null,  // Only product ID
    val user: Int? = null,     // Only user ID
    val price: Double = 0.0
)
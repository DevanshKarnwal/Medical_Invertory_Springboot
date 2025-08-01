package com.example.medicalinventoryuserspringboot.model


data class SalesHistory(

    var id: Int = 0,

    var productName: String = "",
    var userName: String = "",
    var quantity: Int = 0,
    var price: Double = 0.0,
    var sold: Boolean = false,
    var purchased: Boolean = false,
    var deleted: Boolean = false
)
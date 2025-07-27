package com.example.medicalinventoryadminspringboot.model

import java.sql.Time



data class Users(
    val id : Int,
    var name : String,
    var email : String ="",
    var password : String,
    val creationTime: Time,
    var isBlocked : Boolean,
    var isWaiting : Boolean,
    var number : String="",
    var pincode : String="",
    var role : List<Roles>
)

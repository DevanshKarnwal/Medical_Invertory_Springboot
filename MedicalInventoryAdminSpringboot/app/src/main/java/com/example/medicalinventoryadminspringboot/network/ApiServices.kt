package com.example.medicalinventoryadminspringboot.network

import com.example.medicalinventoryadminspringboot.model.LoginRequest
import com.example.medicalinventoryadminspringboot.model.Users
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {

    @POST("public/loginUser")
    suspend fun loginUser(
        @Body request: LoginRequest
    ): Response<String>



}
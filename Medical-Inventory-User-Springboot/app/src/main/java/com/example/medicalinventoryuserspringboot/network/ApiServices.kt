package com.example.medicalinventoryuserspringboot.network

import com.example.medicalinventoryuserspringboot.model.LoginRequest
import com.example.medicalinventoryuserspringboot.model.Users
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiServices {

    @POST("public/loginUser")
    suspend fun loginUser(
        @Body request: LoginRequest
    ): Response<String>

    @PUT("user")
    suspend fun updateUser(@Body user: Users): Response<ResponseBody>


}
package com.example.medicalinventoryuserspringboot.network

import com.example.medicalinventoryuserspringboot.model.LoginRequest
import com.example.medicalinventoryuserspringboot.model.Users
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ApiServices {

    @POST("public/createUser")
    suspend fun createUser(@Body user: Users): Response<String>

    @POST("public/loginUser")
    suspend fun loginUser(
        @Body request: LoginRequest
    ): Response<String>

    @PUT("user")
    suspend fun updateUser(@Body user: Users): Response<ResponseBody>

    @GET("user/userName")
    suspend fun getUser(@Query("name") name :String) : Response<Users>

}
package com.example.medicalinventoryadminspringboot.repository

import android.util.Log
import com.example.medicalinventoryadminspringboot.ResultState
import com.example.medicalinventoryadminspringboot.model.LoginRequest
import com.example.medicalinventoryadminspringboot.network.ApiServices
import jakarta.inject.Inject
import java.net.HttpURLConnection

class AdminRepo @Inject constructor(private val apiServices: ApiServices) {

    suspend fun loginUser(name: String, password: String): ResultState<String> {
        val response = apiServices.loginUser(LoginRequest(name, password))
        return if (response.code() == HttpURLConnection.HTTP_OK) {
            ResultState.Success(response.body() ?: "User found")
        } else {
            ResultState.Error("Login failed: ${response.errorBody()?.string() ?: response.message()} ${response.toString()}")
        }
    }



}
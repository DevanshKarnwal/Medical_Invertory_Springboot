package com.example.medicalinventoryuserspringboot.repo

import android.util.Log
import com.example.medicalinventoryuserspringboot.ResultState
import com.example.medicalinventoryuserspringboot.model.LoginRequest
import com.example.medicalinventoryuserspringboot.model.Users
import com.example.medicalinventoryuserspringboot.network.ApiServices
import jakarta.inject.Inject
import java.net.HttpURLConnection

class UserRepo @Inject constructor(private val apiServices: ApiServices)  {

    suspend fun loginUser(name: String, password: String): ResultState<String> {
        val response = apiServices.loginUser(LoginRequest(name, password))
        return if (response.code() == HttpURLConnection.HTTP_OK) {
            ResultState.Success(response.body() ?: "User found")
        } else {
            ResultState.Error(
                "Login failed: ${
                    response.errorBody()?.string() ?: response.message()
                } ${response.toString()}"
            )
        }
    }

    suspend fun createUser(user: Users): ResultState<String> {
            try {
                val response = apiServices.createUser(user)
                if(response.code() == HttpURLConnection.HTTP_OK){
                    return ResultState.Success(response.body() ?: "User created")
                }
                return ResultState.Error("User already created")
            }
            catch (e : Exception){
                return ResultState.Error(e.message ?: "Unknown error occurred")
            }
    }

    suspend fun getUser(name: String): ResultState<Users> {
        val response = apiServices.getUser(name);
        Log.d("admin123", "${response.toString()} aaa ${response.isSuccessful.toString()}")
        return ResultState.Success(
            response.body() ?: Users(
                name="",
                password = "",
                isBlocked = false,
                isWaiting = false,
                number = "",
                pincode = "",
            )
        );
    }

}
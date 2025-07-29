package com.example.medicalinventoryadminspringboot.repository

import android.util.Log
import com.example.medicalinventoryadminspringboot.ResultState
import com.example.medicalinventoryadminspringboot.model.Inventory
import com.example.medicalinventoryadminspringboot.model.LoginRequest
import com.example.medicalinventoryadminspringboot.model.Order
import com.example.medicalinventoryadminspringboot.model.Product
import com.example.medicalinventoryadminspringboot.model.Users
import com.example.medicalinventoryadminspringboot.network.ApiServices
import jakarta.inject.Inject
import okhttp3.Response
import java.net.HttpURLConnection

class AdminRepo @Inject constructor(private val apiServices: ApiServices) {

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

    suspend fun getAdminUser(name: String): ResultState<Users> {
        val response = apiServices.getAdminUser(name);
        Log.d("admin123","${response.toString()} aaa ${response.isSuccessful.toString()}")
        return ResultState.Success(
            response.body() ?: Users(
                0,
                "",
                "",
                "",
                java.sql.Time(0),
                false,
                false,
                "",
                "",
                emptyList()
            )
        );
    }
    suspend fun getAllUser(): ResultState<List<Users>> {
        try {
            val response = apiServices.getAllUser()
            return ResultState.Success(response.body() ?: emptyList())
        }
       catch (e:Exception){
           return ResultState.Error(e.message ?: "Unknown error occurred")
       }
    }
    suspend fun getAllProducts(): ResultState<List<Product>> {
        try {
            val response = apiServices.getAllProducts()
            Log.d("product123","${response.isSuccessful}  ${response.body()}")
            return ResultState.Success(response.body() ?: emptyList())
        }
        catch (e:Exception){
            return ResultState.Error(e.message ?: "Unknown error occurred")
        }
    }

    suspend fun getAllInventories(): ResultState<List<Inventory>> {
        try {
            val response = apiServices.getAllInventories()
            return ResultState.Success(response.body() ?: emptyList())
        }
        catch (e:Exception){
            return ResultState.Error(e.message ?: "Unknown error occurred")
        }

    }

    suspend fun getAllOrders(): ResultState<List<Order>> {
        try {
            val response = apiServices.getAllOrders()
            return ResultState.Success(response.body() ?: emptyList())
        }
        catch (e:Exception){
            return ResultState.Error(e.message ?: "Unknown error occurred")
        }
    }


}
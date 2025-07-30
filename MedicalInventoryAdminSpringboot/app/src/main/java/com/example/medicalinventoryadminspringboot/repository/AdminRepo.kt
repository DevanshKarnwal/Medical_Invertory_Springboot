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
import java.sql.Time

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
        Log.d("admin123", "${response.toString()} aaa ${response.isSuccessful.toString()}")
        return ResultState.Success(
            response.body() ?: Users(
                0, "", "", "", Time(0), false, false, "", "", emptyList()
            )
        );
    }

    suspend fun getAllUser(): ResultState<List<Users>> {
        try {
            val response = apiServices.getAllUser()
            return ResultState.Success(response.body() ?: emptyList())
        } catch (e: Exception) {
            return ResultState.Error(e.message ?: "Unknown error occurred")
        }
    }

    suspend fun getAllProducts(): ResultState<List<Product>> {
        try {
            val response = apiServices.getAllProducts()
            Log.d("product123", "${response.isSuccessful}  ${response.body()}")
            return ResultState.Success(response.body() ?: emptyList())
        } catch (e: Exception) {
            return ResultState.Error(e.message ?: "Unknown error occurred")
        }
    }

    suspend fun getSpecificProduct(id: Int): ResultState<Product> {
        try {
            val response = apiServices.getSpecificProduct(id.toString())
            return ResultState.Success(response.body() ?: Product(0,
                "", "", 0.0, ""))
        } catch (e: Exception) {
            return ResultState.Error(e.message ?: "Unknown error occurred")
        }

    }

    suspend fun getAllInventories(): ResultState<List<Inventory>> {
        try {
            val response = apiServices.getAllInventories()
            return ResultState.Success(response.body() ?: emptyList())
        } catch (e: Exception) {
            return ResultState.Error(e.message ?: "Unknown error occurred")
        }

    }

    suspend fun getAllOrders(): ResultState<List<Order>> {
        try {
            val response = apiServices.getAllOrders()
            return ResultState.Success(response.body() ?: emptyList())
        } catch (e: Exception) {
            return ResultState.Error(e.message ?: "Unknown error occurred")
        }
    }

    suspend fun getInventoryByProductId(id: Int): ResultState<Inventory> {
        try {
            val response = apiServices.getInventoryByProductId(id.toString())
            return ResultState.Success(response.body() ?: Inventory(0, 0, 0))
        } catch (e: Exception) {
            return ResultState.Error(e.message ?: "Unknown error occurred")
        }

    }

    suspend fun updateUserBlockAndWaiting(user: Users): ResultState<String> {
        return try {
            val response = apiServices.updateUser(user)
            if (response.isSuccessful) {
                ResultState.Success(response.body()?.string() ?: "User updated successfully")
            } else {
                val errorMsg = response.errorBody()?.string() ?: "Unknown server error"
                Log.e("UpdateUser", "Server error: $errorMsg")
                ResultState.Error(errorMsg)
            }
        } catch (e: Exception) {
            Log.e("UpdateUser", "Exception: ${e.message}")
            ResultState.Error(e.message ?: "Unknown error occurred")
        }
    }

}
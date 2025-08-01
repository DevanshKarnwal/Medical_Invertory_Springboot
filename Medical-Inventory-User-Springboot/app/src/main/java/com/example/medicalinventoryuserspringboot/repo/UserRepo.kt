package com.example.medicalinventoryuserspringboot.repo

import android.util.Log
import com.example.medicalinventoryuserspringboot.Dto.OrderDTO
import com.example.medicalinventoryuserspringboot.Dto.OrderRequest
import com.example.medicalinventoryuserspringboot.Dto.ProductDTO
import com.example.medicalinventoryuserspringboot.ResultState
import com.example.medicalinventoryuserspringboot.model.LoginRequest
import com.example.medicalinventoryuserspringboot.model.Product
import com.example.medicalinventoryuserspringboot.Dto.UserStockDTO
import com.example.medicalinventoryuserspringboot.model.SalesHistory
import com.example.medicalinventoryuserspringboot.model.Users
import com.example.medicalinventoryuserspringboot.network.ApiServices
import jakarta.inject.Inject
import java.net.HttpURLConnection
import java.util.Collections
import kotlin.toString

class UserRepo @Inject constructor(private val apiServices: ApiServices)  {

    suspend fun updateUser(user: Users): ResultState<String> {
        return try {
            // your API call here
            apiServices.updateUser(user)
            ResultState.Success("User updated successfully")
        } catch (e: Exception) {
            ResultState.Error("Failed to update user: ${e.localizedMessage}")
        }
    }


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
        val response = apiServices.getUser(name)
        return if (response.isSuccessful && response.body() != null) {
            ResultState.Success(response.body()!!)
        } else {
            ResultState.Error("Failed to fetch user or user not found")
        }
    }
    suspend fun fetchProducts(): ResultState<List<ProductDTO>> {
        return try {
            val response = apiServices.getAllProducts()
            if (response.isSuccessful) {
                val data = response.body() ?: emptyList()
                ResultState.Success(data)
            } else {
                ResultState.Error("Error loading products : HTTP ${response.code()}")
            }
        } catch (e: Exception) {
            ResultState.Error(e.localizedMessage ?: "Unknown error")
        }
    }


    suspend fun fetchUserStock(userId: Int): ResultState<List<UserStockDTO>> {
        return try {
            val response = apiServices.getUserStock(userId)
            if (response.isSuccessful) {
                val stockList = response.body() ?: emptyList()
                ResultState.Success(stockList)
            } else {
                ResultState.Error(response.errorBody()?.string() ?: "Failed to fetch user stock")
            }
        } catch (e: Exception) {
            ResultState.Error(e.message ?: "Unknown error")
        }
    }

    suspend fun placeOrder(userId: Int, productId: Int, quantity: Int): ResultState<OrderDTO> {
        return try {
            val orderRequest = OrderRequest(userId, productId, quantity)
            val response = apiServices.placeOrder(orderRequest)
            ResultState.Success(response)
        } catch (e: Exception) {
            ResultState.Error(e.localizedMessage ?: "Unknown error")
        }
    }

    suspend fun salesHistory(): ResultState<List<SalesHistory>> {
        return try {
            val response = apiServices.salesHistory()
            if (response.isSuccessful) {
                val salesHistory = response.body() ?: Collections.emptyList<SalesHistory>()
                ResultState.Success(salesHistory)
            } else {
                ResultState.Error(response.errorBody()?.string() ?: "Failed to fetch sales history")
            }
        }catch (e: Exception) {
            ResultState.Error(e.localizedMessage ?: "Unknown error")
        }
    }


}
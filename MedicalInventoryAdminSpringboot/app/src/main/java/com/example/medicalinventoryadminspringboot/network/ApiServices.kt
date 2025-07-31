package com.example.medicalinventoryadminspringboot.network

import com.example.medicalinventoryadminspringboot.Dto.OrderDTO
import com.example.medicalinventoryadminspringboot.model.Inventory
import com.example.medicalinventoryadminspringboot.model.LoginRequest
import com.example.medicalinventoryadminspringboot.model.Order
import com.example.medicalinventoryadminspringboot.model.Product
import com.example.medicalinventoryadminspringboot.model.Users
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {

    @POST("public/loginUser")
    suspend fun loginUser(
        @Body request: LoginRequest
    ): Response<String>

    @GET("admin/userName")
    suspend fun getAdminUser(@Query("name") name :String) : Response<Users>

    @GET("admin/users")
    suspend fun getAllUser() : Response<List<Users>>

    @GET("admin/products")
    suspend fun getAllProducts() : Response<List<Product>>

    @GET("admin/product")
    suspend fun getSpecificProduct(@Query("id") id: String) : Response<Product>

    @GET("admin/inventories")
    suspend fun getAllInventories() : Response<List<Inventory>>

    @GET("admin/orders")
    suspend fun getAllOrders() : Response<List<OrderDTO>>


    @GET("admin/inventoryProduct")
    suspend fun getInventoryByProductId(@Query("id") id: String) : Response<Inventory>

    @PUT("user")
    suspend fun updateUser(@Body user: Users): Response<ResponseBody>

    @PUT("admin/orders")
    suspend fun approveOrder(@Query("id") id: Int): Response<ResponseBody>

    @DELETE("admin/orders")
    suspend fun deleteOrder(@Query("id") id: Int): Response<ResponseBody>


}
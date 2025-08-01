package com.example.medicalinventoryuserspringboot.network


import com.example.medicalinventoryuserspringboot.Dto.OrderDTO
import com.example.medicalinventoryuserspringboot.Dto.OrderRequest
import com.example.medicalinventoryuserspringboot.Dto.ProductDTO
import com.example.medicalinventoryuserspringboot.model.LoginRequest
import com.example.medicalinventoryuserspringboot.model.Product
import com.example.medicalinventoryuserspringboot.Dto.UserStockDTO
import com.example.medicalinventoryuserspringboot.model.SalesHistory
import com.example.medicalinventoryuserspringboot.model.Users
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ApiServices {

    @POST("public/loginUser")
    suspend fun loginUser(
        @Body request: LoginRequest
    ): Response<String>

    @POST("public/createUser")
    suspend fun createUser(@Body user: Users): Response<String>

    //    @PUT("user")
//    suspend fun updateUser(@Body user: Users): Response<ResponseBody>
    @GET("admin/userName")
    suspend fun getUser(@Query("name") name: String): Response<Users>

//    @GET("user/products")
//    suspend fun getAllProducts() : Response<List<Product>>

    @GET("user/user-stock")
    suspend fun getUserStock(@Query("userId") userId: Int): Response<List<UserStockDTO>>

    @GET("user/products/all")
    suspend fun getAllProducts(): Response<List<ProductDTO>>


    @POST("user/order/place")
    suspend fun placeOrder(@Body orderRequest: OrderRequest): OrderDTO

    @GET("user/salesHistory")
    suspend fun salesHistory() : Response<List<SalesHistory>>

    @PUT("users")
    suspend fun updateUser(@Body user: Users): Response<String>




}
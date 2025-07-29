package com.example.medicalinventoryadminspringboot.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicalinventoryadminspringboot.ResultState
import com.example.medicalinventoryadminspringboot.model.Inventory
import com.example.medicalinventoryadminspringboot.model.Product
import com.example.medicalinventoryadminspringboot.model.Users
import com.example.medicalinventoryadminspringboot.repository.AdminRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import retrofit2.Response

@HiltViewModel
class AdminViewModel @Inject constructor(val adminRepo: AdminRepo) : ViewModel() {

    private val _adminUser = MutableStateFlow(AdminInView())
    val adminUser = _adminUser.asStateFlow()
    private val _loginView = MutableStateFlow(LogInAdminView())
    val loginView = _loginView.asStateFlow()
    private val _getAllUsers = MutableStateFlow(GetAllUsers())
    val getAllUsers = _getAllUsers.asStateFlow()
    private val _getAllProducts = MutableStateFlow(GetAllProducts())
    val getAllProducts = _getAllProducts.asStateFlow()
    private val _getAllInventory = MutableStateFlow(GetAllInventory())
    val getAllInventory = _getAllInventory.asStateFlow()

    public fun loginUser(userName : String, password : String){
        viewModelScope.launch (Dispatchers.IO){
            val result = adminRepo.loginUser(userName,password);
            when(result){
                is ResultState.Error -> {
                    _loginView.value = LogInAdminView(isError = result.message)
                }
                is ResultState.Success -> {
                    _loginView.value = LogInAdminView(isSuccessful = result.data)
                    getAdminUser(userName)

                }

                ResultState.Loading -> _loginView.value = LogInAdminView(isError = "Loading")
            }
        }
    }

    public fun getAdminUser(userName: String){
        viewModelScope.launch (Dispatchers.IO){
            val result = adminRepo.getAdminUser(userName);
            when(result){
                is ResultState.Error -> {
                    _adminUser.value = AdminInView(isError = result.message)
                }
                is ResultState.Success -> {
                    Log.d("admin123" , "inside viewmodel ${adminUser.value.isSuccessful.name}")
                    _adminUser.value = AdminInView(isSuccessful = result.data)

                }

                ResultState.Loading -> _adminUser.value = AdminInView(isError = "Loading")
            }
        }
    }

    public fun getAllUser(){
        viewModelScope.launch (Dispatchers.IO){
            val result = adminRepo.getAllUser();
            when(result) {
                is ResultState.Error -> {
                    _getAllUsers.value = GetAllUsers(isError = result.message)
                }
                is ResultState.Success -> {
                    _getAllUsers.value = GetAllUsers(isSuccessful = result.data)
                }
                ResultState.Loading -> _getAllUsers.value = GetAllUsers(isError = "Loading")
            }
        }
    }
    public fun getAllProducts(){
        viewModelScope.launch (Dispatchers.IO){
            val result = adminRepo.getAllProducts();

            when(result) {
                is ResultState.Error -> {
                    Log.d("dashboard-check", "Error triggered ${result.message}")
                    _getAllProducts.value = GetAllProducts(isError = result.message)
                }
                is ResultState.Success -> {
                    Log.d("dashboard-check", "LaunchedEffect success")
                    _getAllProducts.value = GetAllProducts(isSuccessful = result.data)
                }
                ResultState.Loading -> _getAllProducts.value = GetAllProducts(isError = "Loading")
            }
        }
    }
    public fun getAllInventories(){
        viewModelScope.launch (Dispatchers.IO){
            val result = adminRepo.getAllInventories();
            when(result) {
                is ResultState.Error -> {
                    _getAllInventory.value = GetAllInventory(isError = result.message)
                }
                is ResultState.Success -> {
                    _getAllInventory.value = GetAllInventory(isSuccessful = result.data)
                }
                ResultState.Loading -> _getAllInventory.value = GetAllInventory(isError = "Loading")
            }
        }
    }



}

 data class AdminInView(
    var isSuccessful : Users = Users(0,"","", "",java.sql.Time(0),false,false,"","", emptyList()),
    var isError : String = ""

)

 data class LogInAdminView(
    var isSuccessful : String = "",
    var isError : String = ""
)

data class GetAllUsers(
    var isSuccessful : List<Users> = emptyList(),
    var isError :String= ""
)

data class GetAllProducts(
    var isSuccessful : List<Product> = emptyList(),
    var isError: String = ""
)

data class GetAllInventory(
    var isSuccessful : List<Inventory> = emptyList(),
    var isError : String = ""
)
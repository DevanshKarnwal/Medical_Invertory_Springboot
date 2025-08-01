package com.example.medicalinventoryuserspringboot.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicalinventoryuserspringboot.Dto.OrderDTO
import com.example.medicalinventoryuserspringboot.Dto.ProductDTO
import com.example.medicalinventoryuserspringboot.Dto.UserStockDTO
import com.example.medicalinventoryuserspringboot.ResultState
import com.example.medicalinventoryuserspringboot.model.SalesHistory
import com.example.medicalinventoryuserspringboot.model.Users
import com.example.medicalinventoryuserspringboot.repo.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepo: UserRepo
) : ViewModel() {

    private val _user = MutableStateFlow(UsersInView())
    val user = _user.asStateFlow()

    private val _loginView = MutableStateFlow(LogInAdminView())
    val loginView = _loginView.asStateFlow()

    private val _createAccountView = MutableStateFlow(LogInView())
    val createAccountView = _createAccountView.asStateFlow()

    private val _stockState = MutableStateFlow<ResultState<List<UserStockDTO>>>(ResultState.Loading)
    val stockState = _stockState.asStateFlow()

    private val _productListState = MutableStateFlow<ResultState<List<ProductDTO>>>(ResultState.Loading)
    val productListState = _productListState.asStateFlow()

    private val _salesHistoryState = MutableStateFlow<ResultState<List<SalesHistory>>>(ResultState.Loading)
    val salesHistoryState = _salesHistoryState.asStateFlow()

    private val _updateStatus = MutableStateFlow<String?>(null)
    val updateStatus: StateFlow<String?> = _updateStatus

    private val _orderResult = MutableStateFlow<ResultState<OrderDTO>?>(null)
    val orderResult = _orderResult.asStateFlow()
    var loggedInUsername: String? = null
    var userId = 0
    init {
        loadProducts()
    }

    fun updateUser(user: Users) {
        viewModelScope.launch(Dispatchers.IO) {
            _updateStatus.value = null
            val result = userRepo.updateUser(user)
            when (result) {
                is ResultState.Success -> {
                    _updateStatus.value = "User updated successfully!"
                    getUser(user.name)
                }
                is ResultState.Error -> {
                    _updateStatus.value = "Failed to update user: ${result.message}"
                }
                ResultState.Loading -> {
                    _updateStatus.value = "Updating..."
                }
            }
        }
    }


    fun getSalesHistory(){
        viewModelScope.launch {
            _salesHistoryState.value = ResultState.Loading
            _salesHistoryState.value = userRepo.salesHistory()
        }
    }

    fun createUser(user: Users) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = userRepo.createUser(user)
            when (result) {
                is ResultState.Error -> _createAccountView.value = LogInView(isError = result.message)
                is ResultState.Success -> _createAccountView.value = LogInView(isSuccessful = result.data)
                ResultState.Loading -> _createAccountView.value = LogInView(isError = "Loading")
            }
        }
    }

    fun loginUser(userName: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = userRepo.loginUser(userName, password)
            when (result) {
                is ResultState.Error -> {
                    _loginView.value = LogInAdminView(isError = result.message)
                }
                is ResultState.Success -> {
                    loggedInUsername = userName // <- store the logged-in username
                    _loginView.value = LogInAdminView(isSuccessful = result.data)
                    getUser(userName)
                }
                ResultState.Loading -> _loginView.value = LogInAdminView(isError = "Loading")
            }
        }
    }

    fun getUser(userName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = userRepo.getUser(userName)
            when (result) {
                is ResultState.Error -> {
                    _user.value = UsersInView(isError = result.message)
                }
                is ResultState.Success -> {
                    userId = result.data.id?:0;
                    Log.d("UserFetch", "Fetched user id=${result.data.id}, name=${result.data.name}, email=${result.data.email}")
                    _user.value = UsersInView(isSuccessful = result.data)
                }
                ResultState.Loading -> _user.value = UsersInView(isError = "Loading")
            }
        }
    }

    fun loadUserStock(userId: Int) {
        viewModelScope.launch {
            _stockState.value = ResultState.Loading
            _stockState.value = userRepo.fetchUserStock(userId)
        }
    }

    private fun loadProducts() {
        viewModelScope.launch {
            _productListState.value = ResultState.Loading
            _productListState.value = userRepo.fetchProducts()
        }
    }

    fun placeOrder(userId: Int?, productId: Int, quantity: Int) {
        if (userId == null || userId == 0) {
            Log.e("OrderError", "Invalid userId: $userId (user not loaded properly)")
            return
        }

        viewModelScope.launch {
            _orderResult.value = ResultState.Loading
            val result = userRepo.placeOrder(userId, productId, quantity)
            _orderResult.value = result
        }
    }
    fun salesHistory() {
        viewModelScope.launch {
            _salesHistoryState.value = ResultState.Loading
            val result = userRepo.salesHistory()
            _salesHistoryState.value = result
        }
    }
}


data class UsersInView(
    var isSuccessful: Users? = null,
    var isError: String = ""
)

data class LogInAdminView(
    var isSuccessful: String = "",
    var isError: String = ""
)

data class LogInView(
    var isSuccessful: String = "",
    var isError: String = ""
)

data class SalesHistoryView(
    var isSuccessful: List<SalesHistory>? = null,
    var isError: String = ""
)
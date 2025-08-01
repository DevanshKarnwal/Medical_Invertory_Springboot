package com.example.medicalinventoryadminspringboot.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicalinventoryadminspringboot.Dto.OrderDTO
import com.example.medicalinventoryadminspringboot.Dto.UserSummary
import com.example.medicalinventoryadminspringboot.Dto.toUsers
import com.example.medicalinventoryadminspringboot.ResultState
import com.example.medicalinventoryadminspringboot.model.Inventory
import com.example.medicalinventoryadminspringboot.model.Product
import com.example.medicalinventoryadminspringboot.model.Users
import com.example.medicalinventoryadminspringboot.model.getCurrentDate
import com.example.medicalinventoryadminspringboot.model.toUserSummary
import com.example.medicalinventoryadminspringboot.repository.AdminRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class AdminViewModel @Inject constructor(val adminRepo: AdminRepo) : ViewModel() {

    private val _adminUser = MutableStateFlow(AdminInView())
    val adminUser = _adminUser.asStateFlow()
    private val _loginView = MutableStateFlow(LogInAdminView())
    val loginView = _loginView.asStateFlow()
    private var _getAllUsers = MutableStateFlow(GetAllUsers())
    val getAllUsers = _getAllUsers.asStateFlow()
    private val _getAllProducts = MutableStateFlow(GetAllProducts())
    val getAllProducts = _getAllProducts.asStateFlow()
    private val _getAllInventory = MutableStateFlow(GetAllInventory())
    val getAllInventory = _getAllInventory.asStateFlow()
    private val _getInventoryByProductId = MutableStateFlow(GetInventoryByProductId())
    val getInventoryByProductId = _getInventoryByProductId.asStateFlow()
    private val _getSpecificProductById = MutableStateFlow(GetSpecificProductById())
    val getSpecificProductById = _getSpecificProductById.asStateFlow()
    private val _getAllOrders = MutableStateFlow(GetAllOrders())
    val getAllOrders = _getAllOrders.asStateFlow()

    public fun loginUser(userName: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = adminRepo.loginUser(userName, password);
            when (result) {
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

    public fun getAdminUser(userName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = adminRepo.getAdminUser(userName);
            when (result) {
                is ResultState.Error -> {
                    _adminUser.value = AdminInView(isError = result.message)
                }

                is ResultState.Success -> {
                    Log.d("admin123", "inside viewmodel ${adminUser.value.isSuccessful.name}")
                    _adminUser.value = AdminInView(isSuccessful = result.data)

                }

                ResultState.Loading -> _adminUser.value = AdminInView(isError = "Loading")
            }
        }
    }

    public fun getAllUser() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = adminRepo.getAllUser();
            when (result) {
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

    public fun getAllProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = adminRepo.getAllProducts();

            when (result) {
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

    public fun getAllInventories() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = adminRepo.getAllInventories();
            when (result) {
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

    public fun getInventoryByProductId(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = adminRepo.getInventoryByProductId(id);
            when (result) {
                is ResultState.Error -> {
                    _getInventoryByProductId.value =
                        GetInventoryByProductId(isError = result.message)
                }

                is ResultState.Success -> {
                    _getInventoryByProductId.value =
                        GetInventoryByProductId(isSuccessful = result.data)
                }

                ResultState.Loading -> _getInventoryByProductId.value =
                    GetInventoryByProductId(isError = "Loading")

            }
        }
    }

    public fun getSpecificProductById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = adminRepo.getSpecificProduct(id);
            when (result) {
                is ResultState.Error -> {
                    _getSpecificProductById.value = GetSpecificProductById(isError = result.message)
                }

                is ResultState.Success -> {
                    _getSpecificProductById.value =
                        GetSpecificProductById(isSuccessful = result.data)
                }

                ResultState.Loading -> _getSpecificProductById.value =
                    GetSpecificProductById(isError = "Loading")
            }
        }
    }

    fun toggleBlock(user: UserSummary) {
        val updatedUser = user.copy(isBlocked = !user.isBlocked)
        updateUserOnServer(updatedUser.toUsers())

    }

    fun toggleWaiting(user: UserSummary) {
        val updatedUser = user.copy(isWaiting = !user.isWaiting, )
        updateUserOnServer(updatedUser.toUsers())
    }

    fun updateUserOnServer(user: Users) {
        viewModelScope.launch {
            when (val result = adminRepo.updateUserBlockAndWaiting(user)) {
                is ResultState.Success -> {
                    val updatedUser = user.toUserSummary()
                    val currentList = _getAllUsers.value.isSuccessful.toMutableList()
                    val index = currentList.indexOfFirst { it.id == updatedUser.id }
                    if (index != -1) {
                        currentList[index] = updatedUser // ✅ now it matches
                        _getAllUsers.value = _getAllUsers.value.copy(isSuccessful = currentList)
                    }
                }

                is ResultState.Error -> {
                    _getAllUsers.value = _getAllUsers.value.copy(isError = result.message)
                }

                ResultState.Loading -> {
                    // Optional: set loading state if needed
                }
            }
        }
    }

    fun getAllOrders() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = adminRepo.getAllOrders()) {
                is ResultState.Success -> {
                    _getAllOrders.value = GetAllOrders(isSuccessful = result.data)
                }
                is ResultState.Error -> {
                    _getAllOrders.value = GetAllOrders(isError = result.message)
                }
                ResultState.Loading -> {
                    _getAllOrders.value = GetAllOrders(isError = "Loading orders...")
                }
            }
        }
    }

    fun approveOrder(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = adminRepo.approveOrder(id)) {
                is ResultState.Success -> {
                    // Optionally refetch orders
                    getAllOrders()
                }
                is ResultState.Error -> {
                    _getAllOrders.value = _getAllOrders.value.copy(isError = result.message)
                }
                ResultState.Loading -> {}
            }
        }
    }

    fun deleteOrder(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = adminRepo.deleteOrder(id)) {
                is ResultState.Success -> {
                    // Optionally refetch orders
                    getAllOrders()
                }
                is ResultState.Error -> {
                    _getAllOrders.value = _getAllOrders.value.copy(isError = result.message)
                }
                ResultState.Loading -> {}
            }
        }
    }

}

data class AdminInView(
    var isSuccessful: Users = Users(0, "", "", "", "", false, false, "", "", emptyList()),
    var isError: String = ""

)

data class LogInAdminView(
    var isSuccessful: String = "",
    var isError: String = ""
)

data class GetAllUsers(
    var isSuccessful: List<UserSummary> = emptyList(),
    var isError: String = ""
)

data class GetAllProducts(
    var isSuccessful: List<Product> = emptyList(),
    var isError: String = ""
)

data class GetAllInventory(
    var isSuccessful: List<Inventory> = emptyList(),
    var isError: String = ""
)

data class GetInventoryByProductId(
    var isSuccessful: Inventory = Inventory(0, 0, 0),
    var isError: String = ""
)

data class GetSpecificProductById(
    var isSuccessful: Product = Product(0, "", "", 0.0, ""),
    var isError: String = ""
)

data class GetAllOrders(
    val isSuccessful: List<OrderDTO> = emptyList(),
    val isError: String = ""
)
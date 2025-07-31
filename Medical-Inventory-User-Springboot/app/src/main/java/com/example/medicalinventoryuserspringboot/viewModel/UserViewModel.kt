package com.example.medicalinventoryuserspringboot.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicalinventoryuserspringboot.ResultState
import com.example.medicalinventoryuserspringboot.model.Users
import com.example.medicalinventoryuserspringboot.repo.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class UserViewModel @Inject constructor(val userRepo: UserRepo) : ViewModel(){
    private val _user = MutableStateFlow(UsersInView())
    val user = _user.asStateFlow()
    private val _loginView = MutableStateFlow(LogInAdminView())
    val loginView = _loginView.asStateFlow()
    private val _createAccountView = MutableStateFlow(LogInView())
    val createAccountView = _createAccountView.asStateFlow()

    public fun createUser(user: Users) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = userRepo.createUser(user);

            when (result) {
                is ResultState.Error -> {
                    _createAccountView.value = LogInView(isError = result.message)
                }
                is ResultState.Success -> {
                    _createAccountView.value = LogInView(isSuccessful = result.data)
                }
                ResultState.Loading -> _createAccountView.value = LogInView(isError = "Loading")
            }
        }
    }

    public fun loginUser(userName: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = userRepo.loginUser(userName, password);
            when (result) {
                is ResultState.Error -> {
                    _loginView.value = LogInAdminView(isError = result.message)
                }

                is ResultState.Success -> {
                    _loginView.value = LogInAdminView(isSuccessful = result.data)
                    getUser(userName)

                }

                ResultState.Loading -> _loginView.value = LogInAdminView(isError = "Loading")
            }
        }
    }

    public fun getUser(userName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = userRepo.getUser(userName);
            when (result) {
                is ResultState.Error -> {
                    _user.value = UsersInView(isError = result.message)
                }

                is ResultState.Success -> {
                    Log.d("admin123", "inside viewmodel ${user.value.isSuccessful.name}")
                    _user.value = UsersInView(isSuccessful = result.data)

                }

                ResultState.Loading -> _user.value = UsersInView(isError = "Loading")
            }
        }
    }








}

data class UsersInView(
    var isSuccessful: Users = Users(
        0,
        "",
        password = "",
        isBlocked = false,
        isWaiting = false,
        number = "",
        pincode = "",
    ),
    var isError: String = ""

)

data class LogInAdminView(
    var isSuccessful: String = "",
    var isError: String = ""
)

data class LogInView(
    var isSuccessful:String = "",
    var isError: String = ""
)
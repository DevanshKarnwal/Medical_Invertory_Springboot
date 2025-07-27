package com.example.medicalinventoryadminspringboot.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicalinventoryadminspringboot.ResultState
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

    public fun loginUser(userName : String, password : String){
        viewModelScope.launch (Dispatchers.IO){
            val result = adminRepo.loginUser(userName,password);
            when(result){
                is ResultState.Error -> {
                    _loginView.value = LogInAdminView(isError = result.message)
                }
                is ResultState.Success -> {
                    _loginView.value = LogInAdminView(isSuccessful = result.data)
                }

                ResultState.Loading -> _loginView.value = LogInAdminView(isError = "Loading")
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
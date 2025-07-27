package com.example.medicalinventoryadminspringboot.viewModel

import androidx.lifecycle.ViewModel
import com.example.medicalinventoryadminspringboot.model.Users
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class AdminViewModel @Inject constructor() : ViewModel() {

    private val _adminUser = MutableStateFlow(AdminInView())
    val adminUser = _adminUser.asStateFlow()


}

data class AdminInView(
    var isLoaded : Boolean = false,
    var isSuccessful : Users = Users(0,"","", "",java.sql.Time(0),false,false,"","", emptyList()),
    var isError : String = ""

)
package com.example.medicalinventoryadminspringboot

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.medicalinventoryadminspringboot.ui.Navigation
import com.example.medicalinventoryadminspringboot.ui.SignUpScreen
import com.example.medicalinventoryadminspringboot.ui.theme.MedicalInventoryAdminSpringbootTheme
import com.example.medicalinventoryadminspringboot.viewModel.AdminViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val adminViewModel: AdminViewModel = hiltViewModel()
            MedicalInventoryAdminSpringbootTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Navigation(adminViewModel=adminViewModel)
                }
            }
        }
    }
}


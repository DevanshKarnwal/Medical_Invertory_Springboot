package com.example.medicalinventoryuserspringboot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.medicalinventoryuserspringboot.ui.Navigation
import com.example.medicalinventoryuserspringboot.ui.theme.MedicalInventoryUserSpringbootTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MedicalInventoryUserSpringbootTheme {
                Navigation()
            }
        }
    }
}


package com.example.medicalinventoryuserspringboot.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.medicalinventoryuserspringboot.viewModel.UserViewModel

@Composable
fun UserDetails(userViewModel: UserViewModel, navController: NavHostController) {
    val userState by userViewModel.user.collectAsState()
    val updateStatus by userViewModel.updateStatus.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    val user = userState.isSuccessful

    if (user != null) {
        var name by remember { mutableStateOf(user.name) }
        var email by remember { mutableStateOf(user.email) }
        var pincode by remember { mutableStateOf(user.pincode ?: "") }
        var isBlocked by remember { mutableStateOf(user.isBlocked) }
        var isWaiting by remember { mutableStateOf(user.isWaiting) }

        LaunchedEffect(updateStatus) {
            updateStatus?.let {
                snackbarHostState.showSnackbar(it)
                navController.popBackStack()
            }
        }

        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text("Update User", style = MaterialTheme.typography.titleLarge)

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                )

                OutlinedTextField(
                    value = pincode,
                    onValueChange = { pincode = it },
                    label = { Text("Pincode") },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                )
                Button(
                    onClick = {
                        userViewModel.updateUser(
                            user.copy(
                                name = name,
                                email = email,
                                pincode = pincode,
                                isBlocked = isBlocked,
                                isWaiting = isWaiting
                            )
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Text("Update")
                }
            }
        }
    } else {
        // Show a loading or error state if user is null
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}

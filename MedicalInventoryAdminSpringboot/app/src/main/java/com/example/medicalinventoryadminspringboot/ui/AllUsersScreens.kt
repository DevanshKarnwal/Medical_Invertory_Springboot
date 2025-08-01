package com.example.medicalinventoryadminspringboot.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicalinventoryadminspringboot.Dto.UserSummary
import com.example.medicalinventoryadminspringboot.model.Users
import com.example.medicalinventoryadminspringboot.viewModel.AdminViewModel

@Composable
fun AllUsersScreens(viewModel: AdminViewModel ) {
    val usersState = viewModel.getAllUsers.collectAsState()
    LaunchedEffect(Unit)
    {
        viewModel.getAllUser()
    }
    Column(modifier = Modifier.padding(16.dp)) {
        when {
            usersState.value.isError.isNotEmpty() -> {
                Text(text = "Error: ${usersState.value.isError}", color = Color.Red)
            }
            usersState.value.isSuccessful.isEmpty() -> {
                Text(text = "Loading users...", style = MaterialTheme.typography.bodyMedium)
            }
            else -> {
                UserListScreen(
                    users = usersState.value.isSuccessful,
                    onToggleBlocked = { user -> viewModel.toggleBlock(user) },
                    onToggleWaiting = { user -> viewModel.toggleWaiting(user) }
                )
            }
        }
    }
}

@Composable
fun UserListScreen(
    users: List<UserSummary>,
    onToggleBlocked: (UserSummary) -> Unit,
    onToggleWaiting: (UserSummary) -> Unit
) {
    LazyColumn {
        items(users) { user ->
            UserCard(
                user = user,
                onToggleBlocked = onToggleBlocked,
                onToggleWaiting = onToggleWaiting
            )
        }
    }
}

@Composable
fun UserCard(
    user: UserSummary,
    onToggleBlocked: (UserSummary) -> Unit,
    onToggleWaiting: (UserSummary) -> Unit
) {
    val initials = user.name.split(" ").let {
        (it.getOrNull(0)?.firstOrNull()?.toString() ?: "") +
                (it.getOrNull(1)?.firstOrNull()?.toString() ?: "")
    }.uppercase()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            // Circle Avatar with initials
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(color = getColorFromName(user.name), shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = initials,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = user.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(text = user.email, style = MaterialTheme.typography.bodySmall)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    StatusChip(
                        text = if (user.isBlocked) "INACTIVE" else "ACTIVE",
                        color = if (user.isBlocked) Color(0xFFFFCDD2) else Color(0xFFC8E6C9)
                    )
                    StatusChip(text = user.role.firstOrNull()?.name ?: "UNKNOWN")
                }
            }

            // Action buttons
            Column(horizontalAlignment = Alignment.End) {
                TextButton(onClick = { onToggleBlocked(user) }) {
                    Text(text = if (user.isBlocked) "Unblock" else "Block")
                }
                TextButton(onClick = { onToggleWaiting(user) }) {
                    Text(text = if (user.isWaiting) "Approve" else "Mark Waiting")
                }
            }
        }
    }
}

@Composable
fun StatusChip(text: String, color: Color = Color.LightGray) {
    Box(
        modifier = Modifier
            .background(color = color, shape = RoundedCornerShape(50))
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Text(text = text, style = MaterialTheme.typography.bodySmall, color = Color.Black)
    }
}

fun getColorFromName(name: String): Color {
    val hash = name.hashCode()
    val r = (hash shr 16 and 0xFF)
    val g = (hash shr 8 and 0xFF)
    val b = (hash and 0xFF)
    return Color(r, g, b)
}

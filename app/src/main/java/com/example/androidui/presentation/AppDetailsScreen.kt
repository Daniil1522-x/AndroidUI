package com.example.androidui.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.androidui.domain.model.App
import com.example.androidui.domain.model.AppDetailsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDetailsScreen(
    onBackClick: () -> Unit,
    viewModel: AppDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            when (val s = state) {
                is AppDetailsState.Content -> {
                    TopAppBar(
                        title = { Text(s.app.name) },
                        navigationIcon = {
                            IconButton(onClick = onBackClick) {
                                Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                            }
                        },
                        actions = {
                            IconButton(onClick = { viewModel.toggleWishlist() }) {
                                Icon(
                                    imageVector = if (s.app.isInWishlist) {
                                        Icons.Default.Favorite
                                    } else {
                                        Icons.Default.FavoriteBorder
                                    },
                                    contentDescription = "Вишлист",
                                    tint = if (s.app.isInWishlist) Color.Red else Color.Gray
                                )
                            }
                        }
                    )
                }
                else -> {
                    TopAppBar(
                        title = { Text("") },
                        navigationIcon = {
                            IconButton(onClick = onBackClick) {
                                Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                            }
                        }
                    )
                }
            }
        }
    ) { padding ->
        when (val s = state) {
            is AppDetailsState.Loading -> {
                Box(
                    Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is AppDetailsState.Error -> {
                Box(
                    Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Ошибка: ${s.message ?: "Неизвестная ошибка"}")
                }
            }
            is AppDetailsState.Content -> {
                AppDetailsContent(app = s.app, padding = padding)
            }
        }
    }
}

@Composable
private fun AppDetailsContent(app: App, padding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        AsyncImage(
            model = app.iconUrl,
            contentDescription = app.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(96.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = app.name, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = app.category, fontSize = 14.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = app.description, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
            Text("Установить")
        }
    }
}

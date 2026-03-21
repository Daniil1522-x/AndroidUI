package com.example.androidui.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.androidui.domain.model.App
import kotlinx.coroutines.launch

private val RuStoreBlue = Color(0xFF2787F5)

@Composable
fun AppListScreen(
    onAppClick: (App) -> Unit,
    viewModel: AppListViewModel = viewModel()
) {
    val apps by viewModel.apps.collectAsState()
    val showSnackbar by viewModel.showSnackbar.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(showSnackbar) {
        if (showSnackbar) {
            scope.launch {
                snackbarHostState.showSnackbar("Добро пожаловать в RuStore!")
            }
            viewModel.onSnackbarShown()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            AppListHeader(onLogoClick = { viewModel.onLogoClick() })

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(apps) { app ->
                    AppListItem(app = app, onClick = { onAppClick(app) })
                    HorizontalDivider(color = Color.LightGray, thickness = 0.5.dp)
                }
            }
        }
    }
}

@Composable
private fun AppListHeader(onLogoClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(RuStoreBlue)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "RuStore",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(1f)
                .clickable { onLogoClick() }
        )
    }
}

@Composable
fun AppListItem(app: App, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = app.iconUrl,
            contentDescription = app.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(12.dp))
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = app.name,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
            Text(
                text = app.description,
                fontSize = 13.sp,
                color = Color.Gray
            )
            Text(
                text = app.category,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}
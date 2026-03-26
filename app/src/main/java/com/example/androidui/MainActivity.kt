package com.example.androidui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.androidui.presentation.AppDetailsScreen
import com.example.androidui.presentation.AppDetailsViewModel
import com.example.androidui.presentation.AppListScreen
import com.example.androidui.presentation.AppListViewModel
import com.example.androidui.ui.theme.AndroidUITheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidUITheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel: AppListViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = "app_list") {
        composable("app_list") {
            AppListScreen(
                onAppClick = { app ->
                    navController.navigate("app_details/${app.id}")
                },
                viewModel = viewModel
            )
        }
        composable("app_details/{appId}") { backStackEntry ->
            val appId = backStackEntry.arguments?.getString("appId")
            val detailsViewModel: AppDetailsViewModel = hiltViewModel()
            val app by detailsViewModel.app.collectAsState()
            val isLoading by detailsViewModel.isLoading.collectAsState()
            val error by detailsViewModel.error.collectAsState()

            LaunchedEffect(appId) {
                if (!appId.isNullOrBlank()) {
                    detailsViewModel.loadApp(appId)
                }
            }

            when {
                isLoading -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                    }
                }

                !error.isNullOrBlank() -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = error ?: "Unknown error")
                    }
                }

                app != null -> {
                    AppDetailsScreen(
                        app = app!!,
                        onBackClick = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}
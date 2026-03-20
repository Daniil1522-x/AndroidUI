package com.example.androidui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.androidui.presentation.AppDetailsScreen
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
    val viewModel: AppListViewModel = viewModel()

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
            val appId = backStackEntry.arguments?.getString("appId")?.toIntOrNull()
            val apps by viewModel.apps.collectAsState()
            val app = apps.find { it.id == appId }
            if (app != null) {
                AppDetailsScreen(
                    app = app,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}
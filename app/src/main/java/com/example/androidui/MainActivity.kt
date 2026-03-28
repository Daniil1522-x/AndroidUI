package com.example.androidui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
        composable(
            route = "app_details/{appId}",
            arguments = listOf(
                navArgument("appId") { type = NavType.StringType }
            )
        ) {
            AppDetailsScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
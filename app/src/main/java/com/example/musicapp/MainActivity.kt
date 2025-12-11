package com.example.musicapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.musicapp.screens.ExploreScreen
import com.example.musicapp.screens.HomeScreen
import com.example.musicapp.screens.LoginScreen
import com.example.musicapp.screens.SignUpScreen // <--- 1. IMPORTANTE: Importar la pantalla de registro
import com.example.musicapp.ui.theme.MusicAppTheme
import com.example.musicapp.ui.theme.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MusicAppTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Routes.Login
                    ) {
                        composable(Routes.Home) {
                            HomeScreen(navController)
                        }

                        composable(Routes.Explore) {
                            ExploreScreen(navController)
                        }

                        composable(Routes.Login) {
                            LoginScreen(
                                onNavigateToSignUp = {
                                    navController.navigate(Routes.Signup)
                                },
                                onNavigateToHome = {
                                    navController.navigate(Routes.Home) {
                                        popUpTo(Routes.Login) { inclusive = true }
                                    }
                                }
                            )
                        }

                        composable(Routes.Signup) {
                            SignUpScreen(
                                onNavigateToLogin = {
                                    navController.navigate(Routes.Login) {
                                        popUpTo(Routes.Signup) { inclusive = true }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

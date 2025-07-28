package com.fintern.ourbudgeting.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fintern.ourbudgeting.ui.login.HomeScreen
import com.fintern.ourbudgeting.ui.login.LoginScreen

@Composable
fun AppNavHost(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Screen.Login.name
    ) {
        composable(Screen.Login.name) {
            LoginScreen(
                onNavigateToHome = {
                    navController.navigate(Screen.Home.name) {
                        popUpTo(Screen.Login.name) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Home.name) {
            HomeScreen()
        }
    }
}
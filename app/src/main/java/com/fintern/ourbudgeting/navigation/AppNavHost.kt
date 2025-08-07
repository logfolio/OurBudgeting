package com.fintern.ourbudgeting.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fintern.ourbudgeting.ui.assetmanagement.screen.AssetManagementScreen
import com.fintern.ourbudgeting.ui.calendar.CalendarScreen
import com.fintern.ourbudgeting.ui.login.HomeScreen
import com.fintern.ourbudgeting.ui.login.LoginScreen
import com.fintern.ourbudgeting.ui.statistics.chart.StatisticsScreen

@Composable
fun AppNavHost(navController: NavHostController, modifier: Modifier = Modifier) {

    NavHost(
        navController = navController,
        startDestination = Screen.LOGIN.name
    ) {
        composable(Screen.LOGIN.name) {
            LoginScreen(
                onNavigateToHome = {
                    navController.navigate(Screen.HOME.name) {
                        popUpTo(Screen.LOGIN.name) { inclusive = true }
                    }
                }
            )
        }

        composable(BottomNavigationItem.HOME.name) {
            HomeScreen()
        }

        composable(BottomNavigationItem.CALENDAR.name) {
            CalendarScreen()
        }

        composable(BottomNavigationItem.STATISTICS.name) {
            StatisticsScreen(
                uid = "",
                householdId = "",
            )
        }

        composable(BottomNavigationItem.ASSETMANAGEMENT.name) {
            AssetManagementScreen(
                asset = 0,
                dept = 0
            )
        }

        composable(BottomNavigationItem.SETTING.name) {

        }
    }
}
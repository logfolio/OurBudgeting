package com.fintern.ourbudgeting.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fintern.ourbudgeting.ui.assetmanagement.assetdisplay.AssetDisplayScreen
import com.fintern.ourbudgeting.ui.calendar.CalendarScreen
import com.fintern.ourbudgeting.ui.common.model.TransactionType
import com.fintern.ourbudgeting.ui.household.PersonalHouseholdManagementScreen
import com.fintern.ourbudgeting.ui.login.HomeScreen
import com.fintern.ourbudgeting.ui.login.LoginScreen
import com.fintern.ourbudgeting.ui.login.LoginViewModel
import com.fintern.ourbudgeting.ui.save.TransactionSaveScreen
import com.fintern.ourbudgeting.ui.setting.SettingScreen
import com.fintern.ourbudgeting.ui.statistics.chart.StatisticsScreen
import com.fintern.ourbudgeting.ui.user.UserViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel()
) {
    val uiState by loginViewModel.uiState.collectAsState()
    val isLoggedIn = uiState.currentUser != null

    val uid by userViewModel.uid.collectAsState()
    val household by userViewModel.household.collectAsState()
    val householdId = household?.id ?: ""

    val startDestination = if (isLoggedIn) BottomNavigationItem.HOME.name else Screen.LOGIN.name

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Screen.LOGIN.name) {
            LoginScreen(
                onNavigateToHome = {
                    navController.navigate(BottomNavigationItem.HOME.name) {
                        popUpTo(Screen.LOGIN.name) { inclusive = true }
                    }
                }
            )
        }

        composable(BottomNavigationItem.HOME.name) { HomeScreen() }
        composable(BottomNavigationItem.CALENDAR.name) { CalendarScreen() }
        composable(BottomNavigationItem.STATISTICS.name) {
            StatisticsScreen(
                uid = "",
                householdId = ""
            )
        }
        composable(BottomNavigationItem.ASSETMANAGEMENT.name) {
            AssetDisplayScreen(
                householdId = householdId,
                onEditAssetTypeClick = { navController.navigate("edit_asset") },
                onAddAssetTypeClick = { navController.navigate("add_asset") }
            )
        }
        composable(BottomNavigationItem.SETTING.name) {
            SettingScreen(
                onNavigateToPersonalHouseholdManagement = {
                    navController.navigate(Screen.PERSONALHOUSEHOLDMANAGEMENT.name)
                }
            )
        }
        composable(Screen.TRANSACTIONSAVE.name) {
            TransactionSaveScreen(
                initialTransactionType = TransactionType.EXPENSE,
                householdId = "",
                onNavigateToBack = {
                    navController.popBackStack()
                },
                // TODO: householdId 추가
            )
        }
        composable(Screen.PERSONALHOUSEHOLDMANAGEMENT.name) {
            PersonalHouseholdManagementScreen(
                onNavigateToBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
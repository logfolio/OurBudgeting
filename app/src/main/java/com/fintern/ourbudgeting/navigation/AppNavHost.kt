package com.fintern.ourbudgeting.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.fintern.ourbudgeting.ui.assetmanagement.assetdisplay.AssetDisplayScreen
import com.fintern.ourbudgeting.ui.assetmanagement.assetedition.AssetEditScreen
import com.fintern.ourbudgeting.ui.assetmanagement.assettypeaddition.AssetAdditionScreen
import com.fintern.ourbudgeting.ui.calendar.CalendarScreen
import com.fintern.ourbudgeting.ui.common.model.TransactionType
import com.fintern.ourbudgeting.ui.home.HomeScreen
import com.fintern.ourbudgeting.ui.household.PersonalHouseholdManagementScreen
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
    val nickname by userViewModel.nickname.collectAsState()
    val email by userViewModel.email.collectAsState()

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

        composable(BottomNavigationItem.HOME.name) {
            HomeScreen(
                householdId = householdId,
                onAddIncomeClick = {
                    navController.navigate("${Screen.TRANSACTIONSAVE.name}?type=${TransactionType.INCOME.name}")
                },
                onAddExpenseClick = {
                    navController.navigate("${Screen.TRANSACTIONSAVE.name}?type=${TransactionType.EXPENSE.name}")
                },
            )
        }
        composable(BottomNavigationItem.CALENDAR.name) { CalendarScreen() }
        composable(BottomNavigationItem.STATISTICS.name) {
            StatisticsScreen(
                uid = uid,
                householdId = householdId,
            )
        }
        composable(BottomNavigationItem.ASSETMANAGEMENT.name) {
            AssetDisplayScreen(
                householdId = householdId,
                onEditAssetTypeClick = { navController.navigate(Screen.ASSETEDIT.name) },
                onAddAssetTypeClick = { navController.navigate(Screen.ASSETADDITION.name) }
            )
        }
        composable(Screen.ASSETEDIT.name) {
            AssetEditScreen(
                householdId = householdId,
                onNavigateBack = { navController.navigateUp() }
            )
        }
        composable(Screen.ASSETADDITION.name) {
            AssetAdditionScreen(
                householdId = householdId,
                onNavigateBack = { navController.navigateUp() }
            )
        }
        composable(BottomNavigationItem.SETTING.name) {
            SettingScreen(
                onNavigateToPersonalHouseholdManagement = {
                    navController.navigate(Screen.PERSONALHOUSEHOLDMANAGEMENT.name)
                },
                nickname = nickname,
                email = email
            )
        }
        composable(
            route = "${Screen.TRANSACTIONSAVE.name}?type={type}",
            arguments = listOf(
                navArgument("type") {
                    type = NavType.StringType
                    defaultValue = TransactionType.EXPENSE.name
                },
            )
        ) { backStackEntry ->
            val typeArg = backStackEntry.arguments?.getString("type")
            val initialType = runCatching { TransactionType.valueOf(typeArg ?: "") }
                .getOrDefault(TransactionType.EXPENSE)

            TransactionSaveScreen(
                initialTransactionType = initialType,
                householdId = householdId,
                onNavigateToBack = { navController.popBackStack() }
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
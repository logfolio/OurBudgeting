package com.fintern.ourbudgeting.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigationBar() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.LOGIN.name) {
                NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
                    BottomNavigationItem.entries.forEach { screen ->
                        NavigationBarItem(
                            selected = currentRoute == screen.name,
                            onClick = {
                                navController.navigate(route = screen.name) {
                                    popUpTo(navController.currentDestination?.route ?: screen.name) {
                                        inclusive = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    painter = painterResource(screen.icon),
                                    contentDescription = stringResource(screen.contentDescription),
                                )
                            },
                            label = { Text(stringResource(screen.label)) }
                        )
                    }
                }
            }
        }
    ) { contentPadding ->
        AppNavHost(navController = navController, modifier = Modifier.padding(contentPadding))
    }
}
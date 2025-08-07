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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController

@Composable
fun AppNavigationBar(
    modifier: Modifier = Modifier,
    navHostController: NavHostController
) {
    val startDestination = BottomNavigationItem.HOME
    var selectedDestination by rememberSaveable { mutableIntStateOf(startDestination.ordinal) }

    Scaffold(
        modifier = modifier,
        bottomBar = {
            NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
                BottomNavigationItem.entries.forEachIndexed { index, screen ->
                    NavigationBarItem(
                        selected = selectedDestination == index,
                        onClick = {
                            navHostController.navigate(route = screen.name)
                            selectedDestination = index
                        },
                        icon = {
                            Icon(
                                painter = painterResource(screen.icon),
                                contentDescription = screen.contentDescription,
                            )
                        },
                        label = { Text(screen.label) }
                    )
                }
            }
        }
    ) { contentPadding ->
        AppNavHost(navController = navHostController, modifier.padding(contentPadding))
    }
}
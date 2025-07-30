package com.fintern.ourbudgeting

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.fintern.ourbudgeting.navigation.AppNavHost
import com.fintern.ourbudgeting.ui.theme.OurBudgetingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OurBudgetingTheme {
                val navController = rememberNavController()

                AppNavHost(navController)
            }
        }
    }
}
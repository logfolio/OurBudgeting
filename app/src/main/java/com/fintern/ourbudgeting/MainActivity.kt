package com.fintern.ourbudgeting

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.fintern.ourbudgeting.navigation.AppNavigationBar
import com.fintern.ourbudgeting.ui.theme.OurBudgetingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OurBudgetingTheme {
                AppNavigationBar()
            }
        }
    }
}
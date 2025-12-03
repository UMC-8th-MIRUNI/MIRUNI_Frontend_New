package com.miruni.feature.aiplanner.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.miruni.core.navigation.MiruniRoute
import com.miruni.core.navigation.NavigationDestination
import com.miruni.feature.aiplanner.CalendarScreen
import jakarta.inject.Inject

class CalendarNavigation @Inject constructor() : NavigationDestination {
    override val route: String = MiruniRoute.Calendar.route

    override fun register(
        builder: NavGraphBuilder,
        navController: NavHostController
    ) {
        builder.composable(route) {
            CalendarScreen(navController = navController)
        }
    }
}
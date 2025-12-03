package com.miruni.feature.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.miruni.core.navigation.MiruniRoute
import com.miruni.core.navigation.NavigationDestination
import com.miruni.feature.home.HomeScreen
import jakarta.inject.Inject

class HomeNavigation @Inject constructor() : NavigationDestination {
    override val route: String = MiruniRoute.Home.route

    override fun register(
        builder: NavGraphBuilder,
        navController: NavHostController
    ) {
        builder.composable(route) {
            HomeScreen(navController = navController)
        }
    }
}
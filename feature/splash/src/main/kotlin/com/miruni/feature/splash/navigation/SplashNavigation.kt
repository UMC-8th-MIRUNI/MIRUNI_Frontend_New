package com.miruni.feature.splash.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.miruni.core.navigation.MiruniRoute
import com.miruni.core.navigation.NavigationDestination
import com.miruni.feature.splash.SplashScreen
import jakarta.inject.Inject

class SplashNavigation @Inject constructor() : NavigationDestination {
    override val route: String = MiruniRoute.Splash.route

    override fun register(
        builder: NavGraphBuilder,
        navController: NavHostController
    ) {
        builder.composable(route) {
            SplashScreen(navController = navController)
        }
    }
}
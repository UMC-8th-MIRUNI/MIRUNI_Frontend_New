package com.miruni.feature.splash.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.miruni.core.navigation.MiruniRoute
import com.miruni.core.navigation.NavigationDestination
import com.miruni.feature.splash.MiruniSplashScreen
import jakarta.inject.Inject

class MiruniSplashNavigation @Inject constructor() : NavigationDestination {

    override val route: String = MiruniRoute.Splash.route

    override fun register(
        builder: NavGraphBuilder,
        navController: NavHostController
    ) {
        builder.composable(route) {
            MiruniSplashScreen(
                navController = navController,
                onTimeout = {
                    navController.navigate(MiruniRoute.Home.route) {
                        popUpTo(MiruniRoute.Splash.route) { inclusive = true }
                    }
                }
            )
        }
    }
}
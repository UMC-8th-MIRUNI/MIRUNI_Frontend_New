package com.miruni.feature.onboard.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.miruni.core.navigation.MiruniRoute
import com.miruni.core.navigation.NavigationDestination
import com.miruni.feature.onboard.presentation.screen.OnboardScreen
import javax.inject.Inject

class OnboardNavigation @Inject constructor() : NavigationDestination {
    override val route: String = MiruniRoute.AppOnboarding.route

    override fun register(builder: NavGraphBuilder, navController: NavHostController) {
        builder.composable(route) {
            OnboardScreen(navController = navController)
        }
    }
}
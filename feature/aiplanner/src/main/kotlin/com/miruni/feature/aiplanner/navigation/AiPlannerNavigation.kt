package com.miruni.feature.aiplanner.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.miruni.core.navigation.MiruniRoute
import com.miruni.core.navigation.NavigationDestination
import com.miruni.feature.aiplanner.presentation.AiPlannerScreen
import javax.inject.Inject

class AiPlannerNavigation @Inject constructor() : NavigationDestination {
    override val route: String = MiruniRoute.AiPlanner.route

    override fun register(
        builder: NavGraphBuilder,
        navController: NavHostController
    ) {
        builder.composable(route) {
            AiPlannerScreen(navController = navController)
        }
    }
}
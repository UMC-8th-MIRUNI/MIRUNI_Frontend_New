package com.miruni.feature.aiplanner.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.miruni.core.navigation.MiruniRoute
import com.miruni.core.navigation.NavigationDestination
import com.miruni.feature.aiplanner.presentation.screen.AiPlannerLoadingScreen
import com.miruni.feature.aiplanner.presentation.screen.AiPlannerOnboardingScreen
import com.miruni.feature.aiplanner.presentation.screen.AiPlannerMainScreen
import com.miruni.feature.aiplanner.presentation.screen.AiPlannerPlanningScreen
import javax.inject.Inject

class AiPlannerNavigation @Inject constructor() : NavigationDestination {
    override val route: String = MiruniRoute.AiPlanner.route

    override fun register(
        builder: NavGraphBuilder,
        navController: NavHostController
    ) {
        // AI 플래너 온보딩
        builder.composable(MiruniRoute.AiPlannerOnboarding.route) {
            AiPlannerOnboardingScreen(navController = navController)
        }
        // AI 플래너 메인
        builder.composable(route) {
            AiPlannerMainScreen(navController = navController)
        }
        // AI 플래너 AI 플래닝
        builder.composable(route) {
            AiPlannerPlanningScreen(navController = navController)
        }
        // AI 플래너 AI 플래닝 로딩
        builder.composable(route) {
            AiPlannerLoadingScreen(navController = navController)
        }
    }
}
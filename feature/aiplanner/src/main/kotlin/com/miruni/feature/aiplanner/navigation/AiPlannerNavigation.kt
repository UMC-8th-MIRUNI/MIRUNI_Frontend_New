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
import com.miruni.feature.aiplanner.presentation.screen.AiPlannerScheduleScreen
import javax.inject.Inject

class AiPlannerNavigation @Inject constructor() : NavigationDestination {
    override val route: String = "aiPlanner"

    override fun register(
        builder: NavGraphBuilder,
        navController: NavHostController
    ) {
        // AI 플래너 온보딩
        builder.composable(MiruniRoute.AiPlannerOnboarding.route) {
            AiPlannerOnboardingScreen(navController = navController)
        }
        // AI 플래너 메인
        builder.composable(MiruniRoute.AiPlannerMain.route) {
            AiPlannerMainScreen(navController = navController)
        }
        // AI 플래너 AI 플래닝
        builder.composable(MiruniRoute.AiPlannerPlanning.route) {
            AiPlannerPlanningScreen(navController = navController)
        }
        // AI 플래너 로딩
        builder.composable(MiruniRoute.AiPlannerLoading.route) {
            AiPlannerLoadingScreen(navController = navController)
        }
        // AI 플래너 스케줄 표
        builder.composable(MiruniRoute.AiPlannerSchedule.route) {
            AiPlannerScheduleScreen(navController = navController)
        }
    }
}
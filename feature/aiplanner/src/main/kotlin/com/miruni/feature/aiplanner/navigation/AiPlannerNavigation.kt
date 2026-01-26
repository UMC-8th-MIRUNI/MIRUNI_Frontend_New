package com.miruni.feature.aiplanner.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
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
        builder.navigation(
            route = this.route,
            startDestination = MiruniRoute.AiPlannerMain.route
        ) {
            // AI 플래너 온보딩
            composable(MiruniRoute.AiPlannerOnboarding.route) {
                AiPlannerOnboardingScreen(navController = navController)
            }
            // AI 플래너 메인
            composable(MiruniRoute.AiPlannerMain.route) {
                AiPlannerMainScreen(navController = navController)
            }
            // AI 플래너 AI 플래닝
            composable(MiruniRoute.AiPlannerPlanning.route) {
                AiPlannerPlanningScreen(navController = navController)
            }
            // AI 플래너 로딩
            composable(MiruniRoute.AiPlannerLoading.route) {
                AiPlannerLoadingScreen(navController = navController)
            }
            // AI 플래너 스케줄 표
            composable(
                route = "${MiruniRoute.AiPlannerSchedule.route}?from={from}&planId={planId}",
                arguments = listOf(
                    navArgument("from") {
                        type = NavType.StringType
                        defaultValue = "MAIN"
                    },
                    navArgument("planId") {
                        type = NavType.IntType
                        defaultValue = -1
                    }
                )
            ) { backStackEntry ->
                AiPlannerScheduleScreen(navController = navController)
            }
        }
    }
}
package com.miruni.feature.home.navigation

import android.util.Log
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.miruni.core.navigation.MiruniRoute
import com.miruni.core.navigation.NavigationDestination
import com.miruni.feature.home.HomeScreen
import com.miruni.feature.home.dnd.DndCompleteScreen
import com.miruni.feature.home.dnd.DndEarlyEndScreen
import com.miruni.feature.home.dnd.DndOnboardingScreen
import com.miruni.feature.home.dnd.DndPauseScreen
import com.miruni.feature.home.dnd.DndTimerRunningScreen
import com.miruni.feature.home.dnd.DndTimerSetScreen
import jakarta.inject.Inject

class HomeNavigation @Inject constructor(
//    override val arguments: List<NamedNavArgument>
) : NavigationDestination {
    override val route: String = MiruniRoute.Home.route

    override fun register(
        builder: NavGraphBuilder,
        navController: NavHostController
    ) {
        Log.d("HomeNavigation", "Register HomeNavigation")

        builder.composable(MiruniRoute.Home.route) {
            Log.d("HomeNavigation", "HomeScreen entered")
            HomeScreen(
                navController = navController
            )
        }

        builder.composable(MiruniRoute.HomeDndOnboarding.route) {
            Log.d("HomeNavigation", "DndOnboardingScreen entered")
            DndOnboardingScreen(
                navController = navController
            )
        }

        builder.composable(MiruniRoute.HomeDndTimerSetting.route) {
            Log.d("HomeNavigation", "HomeDndTimerSetting entered")
            DndTimerSetScreen(
                navController = navController,
            )
        }

        builder.composable(MiruniRoute.HomeDndPause.route) {
            Log.d("HomeNavigation", "HomeDndPause entered")
            DndPauseScreen(
                navController = navController,
            )
        }

        builder.composable(MiruniRoute.HomeDndEarlyEnd.route) {
            Log.d("HomeNavigation", "HomeDndEarlyEnd entered")
            DndEarlyEndScreen(
                navController = navController,
            )
        }

        builder.composable(
            MiruniRoute.HomeDndTimerRunning.route,
            arguments = listOf(
                navArgument("hour") { type = NavType.IntType},
                navArgument("minute") { type = NavType.IntType}
            )
        ) { backStackEntry ->

            val hour = backStackEntry.arguments?.getInt("hour") ?: 0
            val minute = backStackEntry.arguments?.getInt("minute") ?: 0

            Log.d("HomeNavigation", "HomeDndTimerRunning entered")
            DndTimerRunningScreen(
                hour = hour,
                minute = minute,
                navController = navController,
            )
        }

        builder.composable(
            MiruniRoute.HomeDndComplete.route,
            arguments = listOf(
                navArgument("hour") { type = NavType.IntType},
                navArgument("minute") { type = NavType.IntType}
            )
        ) { backStackEntry ->

            val hour = backStackEntry.arguments?.getInt("hour") ?: 0
            val minute = backStackEntry.arguments?.getInt("minute") ?: 0

            Log.d("HomeNavigation", "HomeDndComplete entered")
            DndCompleteScreen(
                hour = hour,
                minute = minute,
                navController = navController,
            )
        }
    }
}
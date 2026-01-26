package com.miruni.feature.home.navigation

import android.util.Log
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navArgument
import com.miruni.core.navigation.MiruniRoute
import com.miruni.core.navigation.ModalRoute
import com.miruni.core.navigation.NavigationDestination
import com.miruni.feature.home.presentation.screen.HomeScreen
import com.miruni.feature.home.dnd.DndCompleteScreen
import com.miruni.feature.home.dnd.DndEarlyEndScreen
import com.miruni.feature.home.dnd.DndOnboardingScreen
import com.miruni.feature.home.dnd.DndPauseScreen
import com.miruni.feature.home.dnd.DndTimerScreen
import com.miruni.feature.home.dnd.DndTimerViewModel
import com.miruni.feature.home.dnd.RerunTimerErrorModal
import com.miruni.feature.home.dnd.RerunTimerSettingModal
import com.miruni.feature.home.presentation.screen.AlarmLogScreen
import com.miruni.feature.home.runSchedule.RunScheduleTimerScreen
import jakarta.inject.Inject

class HomeNavigation @Inject constructor(
//    override val arguments: List<NamedNavArgument>
) : NavigationDestination {
    override val route: String = MiruniRoute.Home.route

    override fun register(
        builder: NavGraphBuilder,
        navController: NavHostController
    ) {
        builder.composable(route) {
            HomeScreen(
                navController = navController
            )
        }

        builder.composable(MiruniRoute.AlarmLogs.route) {
            AlarmLogScreen(
                navController = navController
            )
        }

        builder.composable(MiruniRoute.HomeDndOnboarding.route) {
            Log.d("HomeNavigation", "DndOnboardingScreen entered")
            DndOnboardingScreen(
                navController = navController
            )
        }

        builder.composable(MiruniRoute.HomeDndTimerSetting.route) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(MiruniRoute.Home.route)
            }

            val viewModel: DndTimerViewModel = viewModel(parentEntry)

            Log.d("HomeNavigation", "HomeDndTimerSetting entered")

            DndTimerScreen(
                navController = navController,
                viewModel = viewModel,
            )
        }

        builder.composable(MiruniRoute.HomeDndPause.route) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(MiruniRoute.Home.route)
            }

            val viewModel: DndTimerViewModel = viewModel(parentEntry)

            Log.d("HomeNavigation", "HomeDndPause entered")
            DndPauseScreen(
                navController = navController,
                viewModel = viewModel,
                hour = 0,
                minute = 0
            )
        }

        builder.composable(
            MiruniRoute.HomeDndEarlyEnd.route,
            arguments = listOf(
                navArgument("hour") { type = NavType.IntType },
                navArgument("minute") { type = NavType.IntType }
            )
        ) { backStackEntry ->

            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(MiruniRoute.Home.route)
            }

            val viewModel: DndTimerViewModel = viewModel(parentEntry)

            val hour = backStackEntry.arguments?.getInt("hour") ?: 0
            val minute = backStackEntry.arguments?.getInt("minute") ?: 0

            Log.d("HomeNavigation", "HomeDndEarlyEnd entered")
            DndEarlyEndScreen(
                hour = hour,
                minute = minute,
                navController = navController,
                viewModel = viewModel
            )
        }

        builder.composable(
            MiruniRoute.HomeDndComplete.route,
            arguments = listOf(
                navArgument("hour") { type = NavType.IntType },
                navArgument("minute") { type = NavType.IntType }
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

        builder.composable(
            MiruniRoute.RunScheduleTimerSetting.route) {
            RunScheduleTimerScreen(
                navController = navController
            )
        }

        builder.dialog(
            ModalRoute.Setting.route
        ) {
            RerunTimerSettingModal(
                onGoSetting = {
                    navController.navigate(ModalRoute.Setting.route)
                },
                onClose = {
                    navController.navigate(MiruniRoute.HomeDndPause.route)
                },
            )
        }

        builder.dialog(
            ModalRoute.Error.route
        ) {
            RerunTimerErrorModal(
                navController = navController
            )
        }
    }
}
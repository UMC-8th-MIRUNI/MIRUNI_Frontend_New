package com.miruni.feature.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.miruni.core.navigation.MiruniRoute
import com.miruni.core.navigation.NavigationDestination
import com.miruni.feature.home.HomeScreen
import com.miruni.feature.home.dnd.DndOnboardingScreen
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
        builder.composable(MiruniRoute.Home.route) {
            HomeScreen(navController = navController)
        }

        builder.composable(MiruniRoute.HomeDndOnboarding.route) {
            DndOnboardingScreen(
                navController = navController
            )
        }

        builder.composable(MiruniRoute.HomeDndTimerSetting.route) {
            DndTimerSetScreen(
                navController = navController,
                onConfirm = { _, _ -> }
            )
        }
    }
}
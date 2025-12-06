package com.miruni.feature.pwreset.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.miruni.core.navigation.MiruniRoute
import com.miruni.core.navigation.NavigationDestination
import com.miruni.feature.pwreset.PwResetScreen
import jakarta.inject.Inject

class PwResetNavigation @Inject constructor(
//    override val arguments: List<NamedNavArgument>
) : NavigationDestination {
    override val route: String = MiruniRoute.PwReset.route

    override fun register(
        builder: NavGraphBuilder,
        navController: NavHostController
    ) {
        builder.composable(route) {
            PwResetScreen(
                navController = navController,
                onLoginRestart = {}
            )
        }
    }
}
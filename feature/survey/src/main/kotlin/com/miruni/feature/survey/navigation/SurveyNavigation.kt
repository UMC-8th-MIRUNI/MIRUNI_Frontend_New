package com.miruni.feature.survey.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.miruni.core.navigation.MiruniRoute
import com.miruni.core.navigation.NavigationDestination
import com.miruni.feature.survey.SurveyScreen
import jakarta.inject.Inject

class SurveyNavigation @Inject constructor() : NavigationDestination {
    override val route: String = MiruniRoute.Survey.route

    override fun register(
        builder: NavGraphBuilder,
        navController: NavHostController
    ) {
        builder.composable(MiruniRoute.Survey.route) {
            SurveyScreen(navController = navController)
        }
    }
}

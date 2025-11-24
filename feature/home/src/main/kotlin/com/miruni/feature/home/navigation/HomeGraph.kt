package com.miruni.feature.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.miruni.feature.home.HomeScreen

fun NavGraphBuilder.homeGraph(route: String) {
    composable(route) {
        HomeScreen()
    }
}
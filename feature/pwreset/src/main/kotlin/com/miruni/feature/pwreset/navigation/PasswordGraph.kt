package com.miruni.feature.pwreset.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.miruni.feature.pwreset.PwResetScreen

fun NavGraphBuilder.passwordGraph(route: String) {
    composable(route) {
        PwResetScreen()
    }
}
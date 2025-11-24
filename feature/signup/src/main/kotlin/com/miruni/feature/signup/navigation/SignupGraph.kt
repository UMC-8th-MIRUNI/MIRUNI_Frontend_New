package com.miruni.feature.signup.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.miruni.feature.signup.SignUpScreen

fun NavGraphBuilder.signupGraph(route: String = "signup") {
    composable(route) {
        SignUpScreen()
    }
}
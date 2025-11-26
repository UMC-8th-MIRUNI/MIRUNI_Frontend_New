package com.miruni.feature.signup.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.miruni.feature.signup.SignupScreen

fun NavGraphBuilder.signupGraph(
    route: String = "signup",
    onSignUpSuccess: () -> Unit
) {
    composable(route) {
        SignupScreen(
            onSignUpSuccess = onSignUpSuccess
        )
    }
}
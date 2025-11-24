package com.miruni.feature.login.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.miruni.feature.login.LoginScreen

fun NavGraphBuilder.loginGraph(
    route: String = "login",
    onLoginSuccess: () -> Unit,
    onSignUpClick: () -> Unit,
    onResetPasswordClick: () -> Unit
) {
    composable(route) {
        LoginScreen(
            onLoginSuccess = onLoginSuccess,
            onSignUpClick = onSignUpClick,
            onResetPasswordClick = onResetPasswordClick
        )
    }
}
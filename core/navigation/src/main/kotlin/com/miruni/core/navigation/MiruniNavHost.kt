package com.miruni.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun MiruniNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination ="login"
    ) {
//        loginGraph(
//            route = MiruniRoute.Login.route,
//            onLoginSuccess = { navController.navigate("home") },
//            onSignUpClick = { navController.navigate("signup") },
//            onResetPasswordClick = { navController.navigate("reset_password") }
//        )

//        signupGraph(route = MiruniRoute.SignUp.route)
//        homeGraph(route = MiruniRoute.Home.route)
//        passwordGraph(route = MiruniRoute.ResetPassword.route)
    }
}
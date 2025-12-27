package com.miruni.feature.login.navigation

sealed class LoginRoute(val route: String) {
    data object Login : LoginRoute("login")
    data object Notification : LoginRoute("notification")
    data object Start : LoginRoute("start")
}
package com.miruni.core.navigation

sealed class MiruniRoute(val route: String) {
    data object Login : MiruniRoute("login")
    data object Home : MiruniRoute("home")
    data object SignUp : MiruniRoute("signup")
    data object ResetPassword : MiruniRoute("reset_password")
}
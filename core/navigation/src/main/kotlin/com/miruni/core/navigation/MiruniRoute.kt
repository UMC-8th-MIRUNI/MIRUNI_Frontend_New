package com.miruni.core.navigation

sealed class MiruniRoute(val route: String) {
    data object Splash : MiruniRoute("splash")
    data object Home : MiruniRoute("home")
    data object Calendar : MiruniRoute("calendar")
    data object MyPage : MiruniRoute("myPage")
}
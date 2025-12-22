package com.miruni.core.navigation

sealed class MiruniRoute(val route: String) {
    data object Splash : MiruniRoute("splash")
    data object Home : MiruniRoute("home")
    data object HomeDndOnboarding : MiruniRoute("home/dnd/onboarding")
    data object HomeDndTimerSetting : MiruniRoute("home/dnd/timerSetting")
    data object HomeDndPause : MiruniRoute("home/dnd/pause")
    data object HomeDndEarlyEnd : MiruniRoute("home/dnd/earlyEnd")
    data object HomeDndTimerRunning : MiruniRoute("home/dnd/timerRunning/{hour}/{minute}") {
        fun createRoute(hour: Int, minute: Int) : String {
            return "home/dnd/timerRunning/$hour/$minute"
        }
    }

    data object Calendar : MiruniRoute("calendar")
    data object MyPage : MiruniRoute("myPage")
    data object Login : MiruniRoute("login")
    data object SignUp : MiruniRoute("signup")
    data object PwReset : MiruniRoute("pwReset")
}
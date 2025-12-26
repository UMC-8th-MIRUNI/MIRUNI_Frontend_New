package com.miruni.core.navigation

sealed class MiruniRoute(val route: String) {
    data object Splash : MiruniRoute("splash")
    data object Home : MiruniRoute("home")
    data object Calendar : MiruniRoute("calendar")
    data object MyPage : MiruniRoute("myPage")
    data object Login : MiruniRoute("login")
    data object SignUp : MiruniRoute("signup")
    data object PwReset : MiruniRoute("pwReset")
    data object AlarmLogs : MiruniRoute("alarmLogs")
    data object AiPlanner : MiruniRoute("aiPlanner")
    data object AiPlannerOnboarding : MiruniRoute("aiPlannerOnboarding")
    data object Dnd : MiruniRoute("dnd")
    data object Execution : MiruniRoute("execution")
}
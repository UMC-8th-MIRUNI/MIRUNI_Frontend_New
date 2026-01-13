package com.miruni.core.navigation

sealed class MiruniRoute(val route: String) {
    data object Splash : MiruniRoute("splash")
    data object Home : MiruniRoute("home")
    data object HomeDndOnboarding : MiruniRoute("home/dnd/onboarding")
    data object HomeDndTimerSetting : MiruniRoute("home/dnd/timerSetting")

    data object HomeDndPause : MiruniRoute("home/dnd/pause/{hour}/{minute}") {
        fun createRoute(hour: Int, minute: Int): String {
            return "home/dnd/pause/$hour/$minute"
        }
    }

    data object HomeDndEarlyEnd : MiruniRoute("home/dnd/earlyEnd/{hour}/{minute}") {
        fun createRoute(hour: Int, minute: Int) : String {
            return "home/dnd/earlyEnd/$hour/$minute"
        }
    }

    data object HomeDndComplete : MiruniRoute("home/dnd/complete/{hour}/{minute}") {
        fun createRoute(hour: Int, minute: Int) : String {
            return "home/dnd/complete/$hour/$minute"
        }
    }

    data object RunScheduleTimerSetting : MiruniRoute("runScheduleTimerSetting")

    data object Calendar : MiruniRoute("calendar")
    data object MyPage : MiruniRoute("myPage")

    data object MyPageSettingAccount : MiruniRoute("myPageSettingAccount")
    data object MyPageSettingNotification : MiruniRoute("myPageSettingNotification")
    data object MyPageInfo : MiruniRoute("myPageInfo")

    data object Login : MiruniRoute("login")
    data object SignUp : MiruniRoute("signup")
    data object PwReset : MiruniRoute("pwReset")
    data object AlarmLogs : MiruniRoute("alarmLogs")
    data object AiPlanner : MiruniRoute("aiPlanner")
    data object AiPlannerOnboarding : MiruniRoute("aiPlannerOnboarding")
//    data object Dnd : MiruniRoute("dnd")
    data object Execution : MiruniRoute("execution")
}

sealed class ModalRoute(val route: String) {
    data object Error : ModalRoute("home/rerun/TimerErrorModal")
    data object Setting : ModalRoute("home/rerun/TimerSettingModal/{hour}/{minute}")
}

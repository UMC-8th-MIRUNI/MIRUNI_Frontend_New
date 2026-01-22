package com.miruni.feature.mypage.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.miruni.core.navigation.MiruniRoute
import com.miruni.core.navigation.NavigationDestination
import com.miruni.feature.mypage.MyPageRoute
import com.miruni.feature.mypage.account.SettingAccountScreen
import com.miruni.feature.mypage.info.InformationScreen
import com.miruni.feature.mypage.info.feedback.WriteFeedbackScreen
import com.miruni.feature.mypage.notification.SettingNotificationScreen
import jakarta.inject.Inject

class MyPageNavigation @Inject constructor(
//    override val arguments: List<NamedNavArgument>
) : NavigationDestination {
    override val route: String = MiruniRoute.MyPage.route

    override fun register(
        builder: NavGraphBuilder,
        navController: NavHostController
    ) {
        builder.composable(MiruniRoute.MyPage.route) {
            MyPageRoute(navController = navController)
        }

        builder.composable(MiruniRoute.MyPageSettingAccount.route) {
            SettingAccountScreen(navController = navController)
        }

        builder.composable(MiruniRoute.MyPageSettingNotification.route) {
            SettingNotificationScreen(navController = navController)
        }

        builder.composable(MiruniRoute.MyPageInfo.route) {
            InformationScreen(navController = navController)
        }

        builder.composable(MiruniRoute.MyPageWriteFeedback.route) {
            WriteFeedbackScreen(navController = navController)
        }
    }
}

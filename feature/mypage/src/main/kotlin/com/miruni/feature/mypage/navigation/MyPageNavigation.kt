package com.miruni.feature.mypage.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.miruni.core.navigation.MiruniRoute
import com.miruni.core.navigation.NavigationDestination
import com.miruni.feature.mypage.MyPageScreen
import jakarta.inject.Inject

class MyPageNavigation @Inject constructor(
    override val arguments: List<NamedNavArgument>
) : NavigationDestination {
    override val route: String = MiruniRoute.MyPage.route

    override fun register(
        builder: NavGraphBuilder,
        navController: NavHostController
    ) {
        builder.composable(route) {
            MyPageScreen(navController = navController)
        }
    }
}
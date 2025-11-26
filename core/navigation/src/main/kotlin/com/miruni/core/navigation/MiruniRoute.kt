package com.miruni.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

interface NavigationDestination {
    fun register(builder: NavGraphBuilder, navController: NavHostController)
}

object MiruniRoute {
    const val SPLASH = "splash"
    const val HOME = "home"
    const val CALENDAR = "calendar"
    const val MY_PAGE = "myPage"
}
package com.miruni.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

interface NavigationDestination {
    val route: String
    fun register(builder: NavGraphBuilder, navController: NavHostController)
}
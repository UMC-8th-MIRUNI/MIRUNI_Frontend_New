package com.miruni.miruni_fe

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.miruni.core.navigation.MiruniRoute
import com.miruni.core.navigation.NavigationDestination
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun MainScreen(destinations: Set<NavigationDestination>) {
    val navController = rememberNavController()
    val navBackStackEntry = navController.currentBackStackEntryAsState().value
    val currentRoute = navBackStackEntry?.destination?.route

    val hideBottomNavItem = setOf(
        MiruniRoute.Login.route,
        MiruniRoute.SignUp.route,
        MiruniRoute.Splash.route,
    )

    Scaffold(
        bottomBar = {
            if (currentRoute !in hideBottomNavItem) {
                BottomNavigationBar(navController)
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = MiruniRoute.Login.route,
            modifier = Modifier.padding(padding)
        ) {
            destinations.forEach { it.register(this, navController) }
        }
    }
}

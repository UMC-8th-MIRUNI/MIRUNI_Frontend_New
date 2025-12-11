package com.miruni.miruni_fe

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.miruni.core.navigation.MiruniRoute
import com.miruni.core.navigation.NavigationDestination

@Composable
fun MainScreen(
    destinations: Set<NavigationDestination>
) {
    val navController = rememberNavController()
    val currentRoute = navController
        .currentBackStackEntryAsState().value?.destination?.route

    // bottom bar를 표시할 대상 route 리스트
    val bottomBarRoutes = listOf(
        MiruniRoute.Home.route,
        MiruniRoute.Calendar.route,
        MiruniRoute.MyPage.route
    )

    val bottomBarVisible = currentRoute in bottomBarRoutes

    Scaffold(
        bottomBar = {
            if (bottomBarVisible) {
                BottomNavigationBar(navController = navController)
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = MiruniRoute.Splash.route, // 앱 시작할때마다 스플래쉬 띄워?
            modifier = Modifier.padding(padding)
        ) {
            destinations.forEach { it.register(this, navController) }
        }
    }
}
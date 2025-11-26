package com.miruni.miruni_fe

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.miruni.core.navigation.MiruniRoute
import com.miruni.core.navigation.NavigationDestination
import com.miruni.feature.calendar.CalendarNavigation
import com.miruni.feature.home.HomeNavigation
import com.miruni.feature.mypage.MyPageNavigation
import com.miruni.feature.splash.MiruniSplashNavigation
import kotlin.collections.forEach

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val currentRoute = navController
        .currentBackStackEntryAsState().value
        ?.destination?.route

    val bottomBarRoutes = listOf(
        MiruniRoute.HOME,
        MiruniRoute.CALENDAR,
        MiruniRoute.MY_PAGE
    )

    val destinations: List<NavigationDestination> = listOf(
        MiruniSplashNavigation(),
        HomeNavigation(),
        CalendarNavigation(),
        MyPageNavigation(),
    )

    Scaffold(
        bottomBar = {
            if (currentRoute in bottomBarRoutes) {
                BottomNavigationBar(navController)
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = MiruniRoute.SPLASH, // 앱 시작할때마다 스플래쉬 띄워?
            modifier = Modifier.padding(padding)
        ) {
            destinations.forEach { it.register(this, navController) }
        }
    }
}


@Composable
fun BottomNavigationBar(navController: NavController) {
    val bottomNavItems = listOf(
        BottomNavItem(MiruniRoute.HOME, "home", Icons.Default.Home),
        BottomNavItem(MiruniRoute.CALENDAR, "calendar", Icons.Default.DateRange),
        BottomNavItem(MiruniRoute.MY_PAGE, "myPage", Icons.Default.Person)
    )

    val currentRoute = navController
        .currentBackStackEntryAsState().value?.destination?.route

    NavigationBar {
        bottomNavItems.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(MiruniRoute.HOME) { saveState = true }
                    }
                },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) }
            )
        }
    }
}
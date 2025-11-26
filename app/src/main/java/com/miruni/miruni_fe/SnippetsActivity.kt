package com.miruni.miruni_fe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.miruni.core.navigation.AppRoutes
import com.miruni.core.navigation.NavigationDestination
import com.miruni.designsystem.MiruniTheme
import com.miruni.feature.calendar.CalendarNavigation
import com.miruni.feature.home.HomeNavigation
import com.miruni.feature.mypage.MyPageNavigation
import dagger.hilt.android.AndroidEntryPoint

data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector,
)

@AndroidEntryPoint
class SnippetsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MiruniTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val destinations: List<NavigationDestination> = listOf(
        HomeNavigation(),
        CalendarNavigation(),
        MyPageNavigation(),
    )

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = AppRoutes.HOME,
            modifier = Modifier.padding(padding)
        ) {
            destinations.forEach { it.register(this, navController) }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
//    val navController = rememberNavController()

    val bottomNavItems = listOf(
        BottomNavItem(AppRoutes.HOME, "home", Icons.Default.Home),
        BottomNavItem(AppRoutes.CALENDAR, "calendar", Icons.Default.DateRange),
        BottomNavItem(AppRoutes.MY_PAGE, "myPage", Icons.Default.Person)
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
                        popUpTo(AppRoutes.HOME) { saveState = true }
                    }
                },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) }
            )
        }
    }
}
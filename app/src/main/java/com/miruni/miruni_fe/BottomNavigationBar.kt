package com.miruni.miruni_fe

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.miruni.core.navigation.MiruniRoute

@Composable
fun BottomNavigationBar(navController: NavController) {
    val bottomNavItems = listOf(
        BottomNavItem(MiruniRoute.Home.route, "Home", Icons.Default.Home),
        BottomNavItem(MiruniRoute.Calendar.route, "Calendar", Icons.Default.DateRange),
        BottomNavItem(MiruniRoute.MyPage.route, "My Page", Icons.Default.Person)
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
                        popUpTo(MiruniRoute.Home.route) { saveState = true }
                    }
                },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) }
            )
        }
    }
}
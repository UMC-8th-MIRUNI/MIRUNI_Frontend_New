package com.miruni.miruni_fe

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.miruni.core.designsystem.MiruniTheme
import com.miruni.core.navigation.MiruniRoute
import com.miruni.feature.home.dnd.DndOnboardingScreen

data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector,
)

@Composable
fun BottomNavigationBar(navController: NavController) {
    val bottomNavItems = listOf(
        BottomNavItem(MiruniRoute.Home.route, "Home", Icons.Default.Home),
        BottomNavItem(MiruniRoute.Calendar.route, "Calendar", Icons.Default.DateRange),
        BottomNavItem(MiruniRoute.MyPage.route, "My Page", Icons.Default.Person)
    )

    val currentRoute = navController
        .currentBackStackEntryAsState().value?.destination?.route

    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color(0xFFFBFBFB)),
        contentColor = (Color(0xFFFBFBFB)),
        tonalElevation = 0.dp,
        windowInsets = WindowInsets(0)
    ) {
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
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        tint = if (currentRoute == item.route)
                            Color(0xFF24C354)
                        else
                            Color.Black
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF24C354),
                    selectedTextColor = Color(0xFF24C354),
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    MiruniTheme {
        BottomNavigationBar(
            navController = rememberNavController()
        )
    }
}
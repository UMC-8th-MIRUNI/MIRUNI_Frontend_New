package com.miruni.core.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.miruni.designsystem.MiruniTheme

interface NavigationDestination {
    fun register(builder: NavGraphBuilder, navController: NavHostController)
}

object AppRoutes {
    const val HOME = "home"
    const val CALENDAR = "calendar"
    const val MY_PAGE = "myPage"
}

sealed class MiruniRoute(val route: String) {
    data object Login : MiruniRoute("login")
    data object Home : MiruniRoute("home")
    data object SignUp : MiruniRoute("signup")
    data object ResetPassword : MiruniRoute("reset_password")
    data object MyPage : MiruniRoute("my_page")
    data object Calendar : MiruniRoute("calendar")
}

enum class MiruniDestination(
    val route: String,
//    val icon: ImageVector,
    val label: String,
    val contentDescription: String
) {
    Home(
        route = "home",
//         icon = Icons
        label = "Home",
        contentDescription = "Home"
    ),
    Calendar(
        route = "calendar",
//        icon = Icons.Default.DateRange, 아이콘 이미지 추가해주세요
        label = "Calendar",
        contentDescription = "Calendar"
    ),
    MyPage(
        route = "mypage",
//        icon = Icons.Default.Person, 아이콘 이미지 추가해주세요
        label = "MyPage",
        contentDescription = "MyPage"
    )
}

@Composable
fun MiruniAppNavHost(
    navController: NavHostController,
    startDestination: MiruniDestination,
    modifier: Modifier = Modifier
) {
        NavHost(
            navController = navController,
            startDestination = startDestination.route,
            modifier = modifier
        ) { }
}

//@Composable
//fun BottomNavigationBar(
//    navController: NavHostController,
//    modifier: Modifier = Modifier
//) {
//    val navController = rememberNavController()
//    val startDestination = MiruniDestination.Home
//    var selectedMiruniDestination by rememberSaveable { mutableIntStateOf(0) }
//
//    Scaffold(
//        modifier = modifier,
//        bottomBar = {
//            NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
//                MiruniDestination.entries.forEachIndexed { index, destination ->
//                    NavigationBarItem(
//                        selected = selectedMiruniDestination == index,
//                        onClick = {
//                            navController.navigate(destination.route)
//                            selectedMiruniDestination = index
//                        },
//                        icon = {
//                            // 아이콘 이미지 추가해주세요
////                            Icon(
////                                destination.icon,
////                                contentDescription = destination.contentDescription
////                            )
//                        },
//                        label = { Text(destination.label) }
//                    )
//                }
//            }
//        }
//    ) { contentPadding ->
//        MiruniAppNavHost(
//            navController = navController,
//            startDestination = startDestination,
//            modifier = Modifier.padding(contentPadding)
//        )
//    }
//}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BottomNavigationBarPreview() {
    MiruniTheme {
        // 실제 네비게이션 동작 없이 선택된 탭만 시뮬레이션
        var selectedMiruniDestination by rememberSaveable { mutableIntStateOf(0) }

        Scaffold(
            bottomBar = {
                NavigationBar {
                    MiruniDestination.entries.forEachIndexed { index, destination ->
                        NavigationBarItem(
                            selected = selectedMiruniDestination == index,
                            onClick = { selectedMiruniDestination = index },
                            icon = {
                                // TODO: 실제 아이콘 넣어주세요
//                                Icon(Icons.Default.Home, contentDescription = destination.contentDescription)
                            },
                            label = { Text(destination.label) }
                        )
                    }
                }
            }
        ) { contentPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
            ) {
                // 선택된 화면 시뮬레이션
                when (MiruniDestination.entries[selectedMiruniDestination]) {
                    MiruniDestination.Home -> Box(Modifier.fillMaxSize()) { Text("Home Screen") }
                    MiruniDestination.Calendar -> Box(Modifier.fillMaxSize()) { Text("Calendar Screen") }
                    MiruniDestination.MyPage -> Box(Modifier.fillMaxSize()) { Text("MyPage Screen") }
                }
            }
        }
    }
}
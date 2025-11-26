package com.miruni.feature.mypage

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.miruni.core.navigation.AppRoutes
import com.miruni.core.navigation.NavigationDestination
import com.miruni.designsystem.MiruniTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPageScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(text = "MyPage Screen")
    }
}

class MyPageNavigation : NavigationDestination {
    override fun register(builder: NavGraphBuilder, navController: NavHostController) {
        builder.composable(AppRoutes.MY_PAGE) {
            MyPageScreen()
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun MyPageScreenPreview() {
    MiruniTheme {
        MyPageScreen()
    }
}
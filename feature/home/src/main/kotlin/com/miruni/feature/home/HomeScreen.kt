package com.miruni.feature.home

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
import com.miruni.core.navigation.MiruniRoute
import com.miruni.core.navigation.NavigationDestination
import com.miruni.designsystem.MiruniTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(text = "Home Screen")
    }
}

class HomeNavigation : NavigationDestination {
    override fun register(builder: NavGraphBuilder, navController: NavHostController) {
        builder.composable(MiruniRoute.HOME) {
            HomeScreen()
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    MiruniTheme {
        HomeScreen()
    }
}
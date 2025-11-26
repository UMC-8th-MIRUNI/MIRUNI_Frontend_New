package com.miruni.feature.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.miruni.core.navigation.MiruniRoute
import com.miruni.core.navigation.NavigationDestination
import com.miruni.designsystem.MiruniTheme

@Composable
private fun MiruniSplashScreen(
    modifier: Modifier = Modifier,
    onTimeout: () -> Unit
) {
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(1500L)
        onTimeout()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(text = "Miruni Splash Screen")
    }
}

class MiruniSplashNavigation : NavigationDestination {
    override fun register(
        builder: NavGraphBuilder,
        navController: NavHostController
    ) {
        builder.composable(MiruniRoute.SPLASH) {
            MiruniSplashScreen(
                onTimeout = {
                    navController.navigate(MiruniRoute.HOME) {
                        popUpTo(MiruniRoute.SPLASH) { inclusive = true }
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun MiruniSplashScreenPreview() {
    MiruniTheme {
        MiruniSplashScreenPreview()
    }
}
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.miruni.designsystem.MiruniTheme

@Composable
fun MiruniSplashScreen(
    navController: NavHostController,
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun MiruniSplashScreenPreview() {
    MiruniTheme {
        MiruniSplashScreen(navController = rememberNavController(), onTimeout = {})
    }
}
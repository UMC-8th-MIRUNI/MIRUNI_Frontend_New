package com.miruni.feature.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.miruni.core.common.SIDE_EFFECTS_KEY
import com.miruni.core.designsystem.AppTypography
import com.miruni.core.designsystem.Gray
import com.miruni.core.designsystem.MainColor
import com.miruni.core.designsystem.MiruniTheme
import com.miruni.core.navigation.MiruniRoute

@Composable
fun MiruniSplashScreen(
    navController: NavHostController,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val iconAlpha = remember { Animatable(0f) }
    val textOffsetY = remember { Animatable(20f) }

    LaunchedEffect(SIDE_EFFECTS_KEY) {
        viewModel.effect.collect { effect ->
            when (effect) {
                SplashContract.Effect.Navigate.ToAppOnboarding -> {
                    navController.navigate(MiruniRoute.AppOnboarding.route) {
                        popUpTo(MiruniRoute.Splash.route) { inclusive = true }
                    }
                }
                SplashContract.Effect.Navigate.ToLogin -> {
                    navController.navigate(MiruniRoute.Login.route) {
                        popUpTo(MiruniRoute.Splash.route) { inclusive = true }
                    }
                }
                SplashContract.Effect.Navigate.ToHome -> {
                    navController.navigate(MiruniRoute.Home.route) {
                        popUpTo(MiruniRoute.Splash.route) { inclusive = true }
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.setEvent(SplashContract.Event.Initialize)
        iconAlpha.animateTo(1f, animationSpec = tween(1000))
        textOffsetY.animateTo(0f, animationSpec = tween(800))
    }

    SplashContent()
}

@Composable
fun SplashContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2BC559)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            Image(
                painter = painterResource(R.drawable.splash_top_img),
                contentDescription = null
            )
        }

        Spacer(modifier = Modifier.height(35.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = "logo"
            )
            Spacer(modifier = Modifier.width(15.dp))
            Text(
                text = "MIRUNI",
                style = AppTypography.AlexandriaTextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 30.sp,
                    letterSpacing = 0.em,
                    lineHeightRatio = 1.0f
                ),
                color = Color(0xFF2D4327)
            )
        }
        Spacer(modifier = Modifier.height(34.dp))
        Text(
            text = "미루는 습관 개선 Helper",
            style = AppTypography.PretendardTextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                letterSpacing = (-0.05).em
            ),
            color = Gray.gray_700
        )
        Text(
            text = "미루니와 DO IT!",
            style = AppTypography.PretendardTextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                lineHeightRatio = 1.0f
            )
        )
        Spacer(modifier = Modifier.height(53.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
        ) {
            Image(
                painter = painterResource(R.drawable.splash_bottom_img),
                contentDescription = null
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun MiruniSplashScreenPreview() {
    SplashContent()
}
package com.miruni.feature.login.component.screen

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.miruni.core.designsystem.AppTypography
import com.miruni.core.designsystem.Black
import com.miruni.core.designsystem.MainColor
import com.miruni.core.designsystem.White
import com.miruni.core.designsystem.Yellow
import com.miruni.feature.login.R
import com.miruni.feature.login.component.MiruniButton


@Composable
fun StartScreen(
    onStartedClicked: () -> Unit,
) {
    val view = LocalView.current
    if (!view.isInEditMode){
        DisposableEffect(Unit) {
            val window = (view.context as Activity).window
            val controller = WindowInsetsControllerCompat(window, view)
            val prevLightStatus = controller.isAppearanceLightStatusBars
            val prevLightNav = controller.isAppearanceLightNavigationBars

            window.statusBarColor = MainColor.miruni_green.toArgb()
            window.navigationBarColor = MainColor.miruni_green.toArgb()
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = false
                isAppearanceLightNavigationBars = false
            }
            onDispose {
                controller.isAppearanceLightStatusBars = prevLightStatus
                controller.isAppearanceLightNavigationBars = prevLightNav
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MainColor.miruni_green),
        contentAlignment = Alignment.Center
    ) {
        // 오른쪽 위
        Image(
            painter = painterResource(R.drawable.ic_miruni_bg),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = 105.dp, y = (-60).dp)
                .graphicsLayer {
                    scaleX = -1f
                    scaleY = -1f
                },
        )

        // 왼쪽 아래
        Image(
            painter = painterResource(R.drawable.ic_miruni_bg),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(x = (-105).dp, y = (60).dp),
        )
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "XXX님\n반가워요",
                style = AppTypography.header_bold_20,
                color = Yellow.yellow,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(22.dp))
            Text(
                "MIRUNI와 함께,\n" +
                        "더 효율적인 하루를 시작해볼까요?",
                style = AppTypography.body_regular_14,
                color = White,
                textAlign = TextAlign.Center
            )
        }
        MiruniButton(
            text = "시작하기",
            onClick = onStartedClicked,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(start = 24.dp, end = 24.dp, bottom = 100.dp),
            containerColor = White,
            contentColor = Black
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun StartScreenPreview() {
    StartScreen(onStartedClicked = {})
}
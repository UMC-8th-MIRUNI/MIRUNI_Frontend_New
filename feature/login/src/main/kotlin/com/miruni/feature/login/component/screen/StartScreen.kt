package com.miruni.feature.login.component.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.miruni.core.designsystem.AppTypography
import com.miruni.core.designsystem.Black
import com.miruni.core.designsystem.MainColor
import com.miruni.core.designsystem.White
import com.miruni.core.designsystem.Yellow
import com.miruni.feature.login.component.MiruniButton

@Composable
fun StartScreen(
    onStartedClicked: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MainColor.miruni_green),
        contentAlignment = Alignment.Center
    ) {
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
            modifier = Modifier.align(Alignment.BottomCenter).padding(start = 24.dp, end = 24.dp, bottom = 100.dp),
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
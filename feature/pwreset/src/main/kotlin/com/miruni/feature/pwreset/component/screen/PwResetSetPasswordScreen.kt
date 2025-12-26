package com.miruni.feature.pwreset.component.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.miruni.core.designsystem.AppTypography
import com.miruni.core.designsystem.Black
import com.miruni.core.designsystem.Gray
import com.miruni.core.designsystem.MainColor
import com.miruni.feature.pwreset.component.button.MiruniButton
import com.miruni.feature.pwreset.component.textfield.MiruniOutlinedTextField

@Composable
fun PwResetSetPasswordScreen(
    onLoginRestart: () -> Unit,
    onNextClicked: () -> Unit,
) {
    PwResetTheme(
        headerText = "비밀번호 재설정",
        subHeaderText = "기억할 수 있 비밀번호로 재설정 해주세요."
    ) {
        Column(
            modifier = Modifier.fillMaxSize().weight(1f),
            verticalArrangement = Arrangement.Bottom,
        ) {
            MiruniOutlinedTextField(
                value = "",
                onValueChange = {},
                label = "새로운 비밀번호",
                labelStyle = AppTypography.body_regular_14.copy(color = Black),
                placeholder = "영문,숫를 포함해 8자이상으로 설정해주세요."
            )
            Spacer(modifier = Modifier.height(16.dp))
            MiruniOutlinedTextField(
                value = "",
                onValueChange = {},
                label = "새로운 비밀번호 확인",
                labelStyle = AppTypography.body_regular_14.copy(color = Black)
            )
            Spacer(modifier = Modifier.height(12.dp))
            MiruniButton(
                text = "비밀번호 재설정",
                onClick = {}
            )
        }
        Row(
            modifier = Modifier.fillMaxSize().weight(1f).padding(bottom = 24.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                "비밀번호가 기억났나요?",
                style = AppTypography.body_regular_14,
                color = Gray.gray_500
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                "로그인하기",
                modifier = Modifier.clickable(){
                    onLoginRestart()
                },
                style = AppTypography.body_regular_14,
                color = MainColor.miruni_green
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PwResetSetPasswordScreenPreview(){
    PwResetSetPasswordScreen(
        onLoginRestart = {},
        onNextClicked = {}
    )
}
package com.miruni.feature.pwreset.presentation.component.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import com.miruni.core.designsystem.Gray
import com.miruni.core.designsystem.MainColor
import com.miruni.feature.pwreset.presentation.component.button.MiruniButton
import com.miruni.feature.pwreset.presentation.component.textfield.MiruniOutlinedTextField

@Composable
fun PwResetEmailScreen(
    email: String,
    canNext: Boolean,
    onEmailChanged: (String) -> Unit,
    onNextClicked: () -> Unit,
    onLoginRestart: () -> Unit
) {
    PwResetTheme(
        headerText = "비밀번호를 잊으셨나요?",
        subHeaderText = "가입하신 이메일 주소를 입력하시면, 비밀번호를\n재설정할 수 있는 안내 메일을 보내드릴게요."
    ) {
        Spacer(modifier = Modifier.height(42.dp))
        MiruniOutlinedTextField(
            value = email,
            onValueChange = onEmailChanged,
            label = {
                Text(
                    "이메일",
                    style = AppTypography.body_regular_14,
                    color = Gray.gray_500,
                    modifier = Modifier.background(Color.Transparent)
                )
            },
        )
        Spacer(modifier = Modifier.height(48.dp))
        MiruniButton(
            onClick = onNextClicked,
            enabled = canNext
        )
        Row(
            modifier = Modifier.fillMaxWidth().weight(1f).padding(bottom = 24.dp),
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
fun PwResetEmailScreenPreview() {
    PwResetEmailScreen(
        onEmailChanged = {},
        onNextClicked = {},
        onLoginRestart = {},
        email = "",
        canNext = true
    )
}
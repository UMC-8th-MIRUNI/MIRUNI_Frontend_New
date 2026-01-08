package com.miruni.feature.pwreset.component.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.miruni.core.designsystem.AppTypography
import com.miruni.core.designsystem.Gray
import com.miruni.feature.pwreset.R
import com.miruni.feature.pwreset.component.button.MiruniButton

@Composable
fun PwResetSuccessScreen(
    onNextClicked: () -> Unit,
) {
    PwResetTheme(
        "", ""
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Image(
                painter = painterResource(R.drawable.img_check),
                contentDescription = "이미지",
                modifier = Modifier.size(136.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text("비밀번호 변경 완료", style = AppTypography.header_bold_20)
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                "비밀번호가 성공적으로 변경되었어",
                style = AppTypography.body_regular_12.copy(color = Gray.gray_500)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
        ) {
            MiruniButton(
                onClick = onNextClicked,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 48.dp)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PwResetSuccessScreenPreview() {
    PwResetSuccessScreen(onNextClicked = {})
}
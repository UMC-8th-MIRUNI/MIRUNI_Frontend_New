package com.miruni.feature.pwreset.component.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
fun PwResetNoticeScreen(
    onNextClicked: () -> Unit,
) {
    PwResetTheme(
        "",""
    ){
        Box(
            modifier = Modifier
            .fillMaxSize()
            .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.img_email_and_file),
                contentDescription = "이미지",
                modifier = Modifier.size(width = 181.dp, height = 154.dp)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            Text(
                "비밀번호를 재설정할 수 있는\n안내메일을 보내드렸어요!",
                style = AppTypography.header_bold_20
            )
            Spacer(modifier = Modifier.height(18.dp))
            Text(
                "메일이 오지 않았나요?\n스팸함을 확인하거나 다시 보내주세요!",
                style = AppTypography.body_regular_12,
                color = Gray.gray_500
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
            ){
                MiruniButton(
                    onClick = onNextClicked,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 48.dp)
                )
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PwResetNoticeScreenPreview() {
    PwResetNoticeScreen(
        onNextClicked = {}
    )
}
package com.miruni.feature.pwreset.component.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.miruni.core.designsystem.AppTypography
import com.miruni.core.designsystem.Gray
import com.miruni.feature.pwreset.component.button.MiruniButton
import com.miruni.feature.pwreset.component.textfield.CodeInput

@Composable
fun PwResetCheckScreen(
    email: String,
    onNextClicked: () -> Unit,
) {
    var code by remember { mutableStateOf("") }

    PwResetTheme(
        headerText = "이메일을 확인해주세요",
        subHeaderText = email
    ){
        CodeInput(
            value = code,
            onValueChange = { code = it },
        )
        Spacer(modifier = Modifier.height(22.dp))
        MiruniButton(
            text = "인증하기",
            onClick = onNextClicked
        )
        Spacer(modifier = Modifier.height(55.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("코드 다시 보내기",style = AppTypography.sub_bold_12, color = Gray.gray_500)
            Spacer(modifier = Modifier.width(12.dp))
            Text("00:20", style = AppTypography.body_regular_14)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PwResetCheckScreenPreview() {
    PwResetCheckScreen(
        email = "",
        onNextClicked = {}
    )
}
package com.miruni.feature.mypage.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.miruni.core.designsystem.AppTypography
import com.miruni.core.designsystem.MainColor
import com.miruni.core.designsystem.White
import java.time.format.TextStyle

@Composable
fun MyPageBottomBar(
    canConfirm: Boolean,
    btnText: String = "제출하기",
    contentColor: Color = Color.White,
    containerColor: Color = MainColor.miruni_green,
    onConfirmClick: () -> Unit,
) {
    Surface(
        color = Color.Transparent,
        modifier = Modifier
            .navigationBarsPadding()
            .padding(horizontal = 20.dp)
    ) {
        Column {
            Button(
                onClick = onConfirmClick,
                enabled = canConfirm,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = containerColor,
                    contentColor = contentColor
                )
            ) {
                Text(btnText, style = AppTypography.button_semibold_16)
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
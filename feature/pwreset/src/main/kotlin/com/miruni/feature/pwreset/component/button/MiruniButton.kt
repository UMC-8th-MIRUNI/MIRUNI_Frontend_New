package com.miruni.feature.pwreset.component.button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.TextStyle
import com.miruni.core.designsystem.AppTypography
import com.miruni.core.designsystem.Gray
import com.miruni.core.designsystem.MainColor
import com.miruni.core.designsystem.White

@Composable
fun MiruniButton(
    text: String = "다음",
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    height: Dp = 55.dp,
    textStyle: TextStyle = AppTypography.button_semibold_16,
    containerColor: Color = MainColor.miruni_green,
    contentColor: Color = White,
    disabledContainerColor: Color = Gray.gray_500,
    disabledContentColor: Color = White,
) {
    Button(
        modifier = modifier.fillMaxWidth().height(height),
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = disabledContainerColor,
            disabledContentColor = disabledContentColor
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Text(text = text, style = textStyle)
    }
}

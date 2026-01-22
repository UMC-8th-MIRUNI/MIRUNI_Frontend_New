package com.miruni.feature.aiplanner.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.miruni.core.designsystem.AppTypography
import com.miruni.core.designsystem.Gray

@Composable
fun ProgressingTagButton(
    textColor: Color = Gray.gray_700,
    backgroundColor: Color = Gray.gray_300,
    borderColor: Color = Gray.gray_400
) {
    Box(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(9.dp)
            )
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(9.dp)
            )
            .padding(horizontal = 8.dp, vertical = 2.dp)
    ) {
        Text(
            text = "진행중",
            style = AppTypography.button_regular_9,
            color = textColor
        )
    }
}

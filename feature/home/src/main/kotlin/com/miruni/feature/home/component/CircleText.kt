package com.miruni.feature.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.miruni.core.designsystem.MainColor

@Composable
fun CircleText(
    circleSize: Dp = 20.dp,
    text: String,
    textStyle: TextStyle,
    textColor: Color = MainColor.miruni_green,
    backgroundColor: Color = Color(0xFFC7E7D1)
) {
    Box(
        modifier = Modifier
            .size(circleSize)
            .background(
                color = backgroundColor,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor,
            style = textStyle
        )
    }
}
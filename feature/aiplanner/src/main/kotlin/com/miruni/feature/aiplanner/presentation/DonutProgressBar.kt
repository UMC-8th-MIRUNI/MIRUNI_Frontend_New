package com.miruni.feature.aiplanner.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun DonutProgressBar(
    modifier: Modifier = Modifier,
    progress: Float,
    strokeWidth: Dp = 8.dp,
    backgroundColor: Color = Color.LightGray,
    progressColor: Color = Color(0xFF1E6718)
) {
    Canvas(modifier = modifier) {
        val diameter = size.minDimension // 지름
        val radius = (diameter - strokeWidth.toPx()) / 2
        val center = Offset(size.width / 2, size.height / 2)

        // 배경 원 (도넛 바깥 배경)
        drawArc( // 호 그리기
            color = backgroundColor,
            startAngle = -90f, // 시작 중심각
            sweepAngle = 360f,
            useCenter = false,
            topLeft = Offset(center.x - radius, center.y - radius), // 원형 좌측 상단
            size = Size(radius * 2, radius * 2),
            style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
        )

        // 진행 원
        drawArc( // 호 그리기
            color = progressColor,
            startAngle = -90f, // 시작 중심각
            sweepAngle = 360f * progress,
            useCenter = false,
            topLeft = Offset(center.x - radius, center.y - radius), // 원형 좌측 상단
            size = Size(radius * 2, radius * 2),
            style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
        )
    }
}
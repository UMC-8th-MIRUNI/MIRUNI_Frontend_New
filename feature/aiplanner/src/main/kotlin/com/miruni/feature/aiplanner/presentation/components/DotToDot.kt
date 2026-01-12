package com.miruni.feature.aiplanner.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.miruni.feature.aiplanner.presentation.model.YInformation

@Composable
fun DotToDot(
    items: SnapshotStateList<YInformation>,
    firstItemTop: Float?
) {
    if (firstItemTop != null) {
        Canvas(modifier = Modifier
            .fillMaxSize()
            .padding(start = 12.dp)
        ) {
            val x = 12.dp.toPx() // x 좌표
            var currentTop = firstItemTop!! // 현재 아이템의 top이라고 생각하는 y좌표

            for (i in items.indices) {
                val currentItem = items[i]
                val currentCenter = currentTop + currentItem.height / 2f

                // 점
                drawCircle(
                    color = Color.Gray,
                    radius = 8.dp.toPx(),
                    center = Offset(x, currentCenter)
                )

                // 점 간 연결선
                if (i < items.lastIndex) {
                    val nextTop = items[i + 1].top
                    val nextCenter =  nextTop + items[i + 1].height / 2f
                    drawLine(
                        color = Color.Gray.copy(alpha = 0.5f),
                        start = Offset(x, currentCenter + 12.dp.toPx()),
                        end = Offset(x, nextCenter - 12.dp.toPx()),
                        strokeWidth = 2.dp.toPx()
                    )
                    currentTop = nextTop
                }
            }
        }
    }
}
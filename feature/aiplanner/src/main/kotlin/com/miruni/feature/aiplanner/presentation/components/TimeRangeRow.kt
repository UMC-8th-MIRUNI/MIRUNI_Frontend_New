package com.miruni.feature.aiplanner.presentation.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.miruni.core.designsystem.MainColor
import com.miruni.core.designsystem.White
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TimeRangeRow(
    startTime: LocalTime,
    endTime: LocalTime,
    onStartChange: (LocalTime) -> Unit,
    onEndChange: (LocalTime) -> Unit
) {
    var startAnchor by remember { mutableStateOf<Rect?>(null) }
    var endAnchor by remember { mutableStateOf<Rect?>(null) }

    var showStart by remember { mutableStateOf(false) }
    var showEnd by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TimeBox(
            time = startTime,
            onClick = { showStart = true },
            onPositioned = { startAnchor = it }
        )
        Text(
            text = "-",
            fontSize = 7.sp
        )
        TimeBox(
            time = endTime,
            onClick = { showEnd = true },
            onPositioned = { endAnchor = it }
        )
    }

    if (showStart && startAnchor != null) {
        TimePickerPopup(
            anchor = startAnchor!!,
            initial = startTime,
            onDismiss = { showStart = false },
            onSelect = onStartChange
        )
    }

    if (showEnd && endAnchor != null) {
        TimePickerPopup(
            anchor = endAnchor!!,
            initial = endTime,
            onDismiss = { showEnd = false },
            onSelect = onEndChange
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TimeBox(
    time: LocalTime,
    onClick: () -> Unit,
    onPositioned: (Rect) -> Unit
) {
    Box(
        modifier = Modifier
            .onGloballyPositioned {
                onPositioned(it.boundsInRoot())
            }
            .border(0.5.dp, Color(0xFFE5E7F0), RoundedCornerShape(2.dp))
            .padding(2.dp)
            .clickable { onClick() }
    ) {
        Text(
            text = time.format(DateTimeFormatter.ofPattern("hh:mm a"))
                .replace("AM", "오전")
                .replace("PM", "오후")
        )
    }
}

@Composable
fun TimePickerPopup(
    anchor: Rect,
    initial: LocalTime,
    onDismiss: () -> Unit,
    onSelect: (LocalTime) -> Unit
) {
    Popup(
        offset = IntOffset(
            x = anchor.left.roundToInt(),
            y = anchor.bottom.roundToInt()
        ),
        onDismissRequest = onDismiss
    ) {
        Box(
            modifier = Modifier
                .width(300.dp)
                .height(260.dp)
                .background(color = White, shape = RoundedCornerShape(12.dp))
                .border(1.dp, MainColor.miruni_green, RoundedCornerShape(12.dp))
                .padding(8.dp)
        ) {
            // TimeSpinner 내부에서 time 변경을 감지해 hour/min/isAm를 업데이트
            TimeSpinner(
                time = initial,
                onChange = { newTime ->
                    onSelect(newTime)
                }
            )
        }
    }
}
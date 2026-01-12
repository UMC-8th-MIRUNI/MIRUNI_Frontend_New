package com.miruni.feature.aiplanner.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.miruni.core.designsystem.AppTypography
import com.miruni.core.designsystem.MainColor
import java.time.LocalDate
import java.time.LocalTime
import kotlin.math.roundToInt

@Composable
fun CalendarPopup(
    anchorBounds: Rect, // 선택 날짜 출력 카드 위치
    width: Dp, // 카드의 가로 길이
    onDismiss: () -> Unit, // 외부 클릭 시 닫기 콜백
    onRangeSelected: (LocalDate, LocalDate, LocalTime, LocalTime) -> Unit
) {
    val density = LocalDensity.current
    val config = LocalConfiguration.current

    val screenHeightPx = with(density) { config.screenHeightDp.dp.toPx() } // 화면 전체 높이
    val popupMaxHeight = screenHeightPx * 0.7f // 팝업 최대 높이

    val y = anchorBounds.bottom // 선택 날짜 출력 카드 하단 y좌표
    val spaceBelow = screenHeightPx - y // 선택 날짜 출력 카드 아래에 남은 공간

    val finalY = if (spaceBelow < popupMaxHeight) {
        // 아래 공간이 부족하면 위에 띄움
        (anchorBounds.top - popupMaxHeight).coerceAtLeast(0f)
    } else {
        y
    }

    Popup(
        // 팝업 위치는 기기 화면 기준
        offset = IntOffset(
            x = anchorBounds.left.roundToInt(),
            y = finalY.roundToInt()
        ),
        onDismissRequest = onDismiss
    ) {
        Card(
            modifier = Modifier
                .width(width * 0.53f) // Card 가로와 동일
                .heightIn(max = with(density) { popupMaxHeight.toDp() }),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            CalendarPopupContent(onRangeSelected)
        }
    }
}

@Composable
fun CalendarPopupContent(
    onRangeSelected: (LocalDate, LocalDate, LocalTime, LocalTime) -> Unit
) {
    var startDate by remember { mutableStateOf<LocalDate?>(null) }
    var endDate by remember { mutableStateOf<LocalDate?>(null) }

    var startTime by remember { mutableStateOf(LocalTime.of(9, 0)) }
    var endTime by remember { mutableStateOf(LocalTime.of(18, 0)) }

    Column {
        RangeCalendar(
            startDate = startDate,
            endDate = endDate,
            onDateClick = { clicked ->
                when {
                    startDate == null -> startDate = clicked
                    endDate == null -> {
                        if (clicked < startDate) {
                            endDate = startDate
                            startDate = clicked
                        } else endDate = clicked
                    }
                    else -> {
                        startDate = clicked
                        endDate = null
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        TimeRangeRow(
            startTime = startTime,
            endTime = endTime,
            onStartChange = { startTime = it },
            onEndChange = { endTime = it }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            enabled = startDate != null && endDate != null,
            onClick = {
                onRangeSelected(startDate!!, endDate!!, startTime, endTime)
            },
            modifier = Modifier
                .align(Alignment.End)
                .padding(7.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MainColor.miruni_green
            )
        ) {
            Text(
                text = "완료",
                color = Color.White,
                style = AppTypography.PretendardTextStyle(
                    fontWeight = FontWeight.Medium,
                    fontSize = 7.sp,
                    letterSpacing = 0f.em
                )
            )
        }
    }
}

@Preview
@Composable
fun PreviewCalendarPopup() {
    CalendarPopupContent(
        onRangeSelected = {sd, ed, st, et ->
            null
        }
    )
}
package com.miruni.feature.aiplanner.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
    anchorBounds: Rect,
    anchorWidth: Dp,
    onDismiss: () -> Unit,
    onRangeSelected: (LocalDate, LocalDate, LocalTime, LocalTime) -> Unit
) {
    val density = LocalDensity.current
    val config = LocalConfiguration.current

    // 화면 크기(px)
    val screenHeightPx = with(density) { config.screenHeightDp.dp.toPx() }
    val screenWidthPx = with(density) { config.screenWidthDp.dp.toPx() }

    // 팝업 높이를 화면 대비 높이 비율로 고정 (0.26배)
    val popupHeightPx = screenHeightPx * 0.26f
    val popupHeightDp = with(density) { popupHeightPx.toDp() }

    // 팝업 너비를 anchor 너비 대비 비율로 고정 (0.53배)
    val popupWidthDp = anchorWidth * 0.53f
    val popupWidthPx = with(density) { popupWidthDp.toPx() }

    // anchor 위치 (window 기준 px)
    val anchorTop = anchorBounds.top
    val anchorBottom = anchorBounds.bottom
    val anchorLeft = anchorBounds.left

    // 아래 여유 공간(px)
    val spaceBelow = screenHeightPx - anchorBottom

    // Y 위치: 아래 공간이 충분하면 아래에 붙이고, 부족하면 위에 띄운다.
    val finalYPx =
        if (spaceBelow >= popupHeightPx) anchorBottom
        else (anchorTop - popupHeightPx).coerceAtLeast(0f)

    // X 위치: 기본 왼쪽 정렬, 오른쪽으로 넘치면 보정
    var finalXPx = anchorLeft
    val overflow = finalXPx + popupWidthPx - screenWidthPx
    if (overflow > 0f) finalXPx -= overflow
    finalXPx = finalXPx.coerceAtLeast(0f)

    Popup(offset = IntOffset(finalXPx.roundToInt(), finalYPx.roundToInt()), onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .width(popupWidthDp)
                .height(popupHeightDp),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            CalendarPopupContent(
                modifier = Modifier.fillMaxSize(),
                onRangeSelected = onRangeSelected
            )
        }
    }
}

/**
 * @param modifier: 부모(Card)에서 넘긴 크기(팝업 너비/높이)를
 * 여기서 BoxWithConstraints로 읽어서 내부 영역 비율대로(캘린더 / 시간영역 / 버튼) 사이즈를 정하고, 달력 셀 크기를 계산
 */
@Composable
fun CalendarPopupContent(
    modifier: Modifier = Modifier,
    onRangeSelected: (LocalDate, LocalDate, LocalTime, LocalTime) -> Unit
) {
    var startDate by remember { mutableStateOf<LocalDate?>(null) }
    var endDate by remember { mutableStateOf<LocalDate?>(null) }

    var startTime by remember { mutableStateOf(LocalTime.of(9, 0)) }
    var endTime by remember { mutableStateOf(LocalTime.of(18, 0)) }

    // BoxWithConstraints로 부모가 실제로 준 maxWidth / maxHeight (dp)를 읽기
    BoxWithConstraints(modifier = modifier) {
        val parentW = maxWidth
        val parentH = maxHeight

        // 내부 패딩
        val horizontalPadding = 8.dp
        val verticalPadding = 6.dp

        // 영역 분배 비율(높이) (총합 1.0)
        val calendarRatio = 0.65f
        val timeRatio = 0.20f
        val buttonRatio = 0.15f

        // 실제 영역 높이 (dp)
        val calendarHeight = (parentH * calendarRatio) - verticalPadding
        val timeAreaHeight = (parentH * timeRatio) - verticalPadding
        val buttonAreaHeight = (parentH * buttonRatio) - verticalPadding

        // 달력 그리드에 실제 사용 가능한 너비 (dp)
        val availableCalendarWidth = (parentW - horizontalPadding * 2f).coerceAtLeast(80.dp)

        // 달력 계산: 몇 행(rows) 필요한지 계산
        val month = java.time.YearMonth.now()
        val first = month.atDay(1)
        val startOffset = first.dayOfWeek.value % 7
        val totalDays = month.lengthOfMonth()
        val rows = ((startOffset + totalDays + 6) / 7) // 정수 행 개수

        // 셀 크기 산정:
        // - cellSizeFromWidth: 가로에 맞춘 셀 크기
        // - cellSizeFromHeight: 세로에 맞춘 셀 크기
        val headerHeight = 28.dp
        val weekdayHeaderHeight = 22.dp
        val interGridSpacing = 8.dp

        val cellSizeFromWidth = (availableCalendarWidth / 7f)
        val cellSizeFromHeight =
            ((calendarHeight - headerHeight - weekdayHeaderHeight - interGridSpacing) / rows.toFloat()).coerceAtLeast(20.dp)

        // 최종 셀 크기: 가로/세로 제약 모두 만족하도록 최소/최대 범위를 둔다.
        val minCell = 20.dp
        val maxCell = cellSizeFromWidth
        val cellSize = cellSizeFromHeight.coerceIn(minCell, maxCell)

        // 만약 cellSize가 너무 작아 보이면, cellSize를 최소로 유지하되
        // 그리드가 실제 차지하는 높이가 calendarHeight를 초과하면 calendarHeight에 맞춰 스케일링
        val gridTotalHeight = cellSize * rows.toFloat()
        val maxGridHeight = calendarHeight - headerHeight - weekdayHeaderHeight - interGridSpacing
        val finalCellSize = if (gridTotalHeight > maxGridHeight) {
            // 스케일 다운
            (maxGridHeight / rows.toFloat()).coerceAtLeast(minCell)
        } else {
            cellSize
        }

        Column(modifier = Modifier.fillMaxSize()) {
            // 캘린더 영역
            Box(
                modifier = Modifier
                    .height(headerHeight + weekdayHeaderHeight + finalCellSize * rows.toFloat() + interGridSpacing)
                    .fillMaxWidth()
            ) {
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
                    },
                    cellSize = finalCellSize,
                    rows = rows
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            // 시간 선택 영역
            Box(
                modifier = Modifier
                    .height(timeAreaHeight)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                TimeRangeRow(
                    startTime = startTime,
                    endTime = endTime,
                    onStartChange = { startTime = it },
                    onEndChange = { endTime = it }
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            // 버튼 영역
            Box(
                modifier = Modifier
                    .height(buttonAreaHeight)
                    .fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Button(
                    enabled = startDate != null && endDate != null,
                    onClick = {
                        onRangeSelected(startDate!!, endDate!!, startTime, endTime)
                    },
                    modifier = Modifier.padding(end = 8.dp, bottom = 4.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MainColor.miruni_green)
                ) {
                    Text(
                        text = "완료",
                        color = Color.White,
                        style = AppTypography.PretendardTextStyle(
                            fontWeight = FontWeight.Medium,
                            fontSize = 10.sp,
                            letterSpacing = 0f.em
                        )
                    )
                }
            }
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
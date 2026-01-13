package com.miruni.feature.aiplanner.presentation.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miruni.core.designsystem.AppTypography
import com.miruni.core.designsystem.MainColor
import com.miruni.feature.aiplanner.R
import java.time.LocalDate
import java.time.YearMonth

/**
 * 범위 선택 달력
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RangeCalendar(
    startDate: LocalDate?,
    endDate: LocalDate?,
    onDateClick: (LocalDate) -> Unit,
    cellSize: Dp, // 부모에서 계산한 셀 크기
    rows: Int // 부모에서 계산한 행 개수
) {
    var month by remember { mutableStateOf(YearMonth.now()) }

    val first = month.atDay(1)
    val startOffset = first.dayOfWeek.value % 7
    val calendarWidth = cellSize * 7 // 캘린더 너비

    val startCellDate = first.minusDays(startOffset.toLong())

    Column(
        modifier = Modifier
            .background(color = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = cellSize, start = cellSize, end = cellSize),
            horizontalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .width(calendarWidth),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(R.drawable.calendar_left_arrow),
                    contentDescription = "이전 달",
                    modifier = Modifier
                        .width(24.dp)
                        .clickable { month = month.minusMonths(1) }
                )

                Text(
                    text = "${month.year}년 ${month.monthValue}월",
                    style = AppTypography.PretendardTextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 7.sp
                    ),
                    color = Color(0xFF494E50)
                )

                Image(
                    painter = painterResource(R.drawable.calendar_right_arrow),
                    contentDescription = "다음 달",
                    modifier = Modifier
                        .width(24.dp)
                        .clickable { month = month.plusMonths(1) }
                )
            }
        }

        Spacer(modifier = Modifier.height(5.dp))

        // 요일 헤더: 각 칸 너비를 cellSize에 맞춤
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = cellSize),
            horizontalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .width(calendarWidth)
                    .background(Color(0xFFF5F7FA)),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                listOf("일", "월", "화", "수", "목", "금", "토").forEach { dow ->
                    Box(
                        modifier = Modifier.width(cellSize),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = dow,
                            style = AppTypography.PretendardTextStyle(
                                fontWeight = FontWeight.Normal,
                                fontSize = 7.sp
                            ),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }


        Spacer(modifier = Modifier.height(6.dp))

        // 날짜 그리드: rows 행, 7 열, 각 셀은 cellSize를 사용
        for (r in 0 until rows) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = cellSize),  // ← 요일 헤더와 동일
                horizontalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier.width(calendarWidth)
                ) {
                    for (c in 0 until 7) {
                        val date = startCellDate.plusDays((r * 7 + c).toLong())
                        val isCurrentMonth = date.month == month.month

                        val isStart = date == startDate
                        val isEnd = date == endDate
                        val inRange = startDate != null && endDate != null && date > startDate && date < endDate
                        val isSingle = startDate != null && endDate == null

                        val shape = when {
                            isSingle && isStart -> RoundedCornerShape(4.dp)
                            isStart -> RoundedCornerShape(topStart = 4.dp, bottomStart = 4.dp)
                            isEnd -> RoundedCornerShape(topEnd = 4.dp, bottomEnd = 4.dp)
                            else -> RoundedCornerShape(0.dp)
                        }

                        val borderColor = if (isStart || isEnd) MainColor.miruni_green else Color.Transparent
                        val bgColor = when {
                            isSingle && isStart -> Color.White
                            isStart || isEnd -> MainColor.miruni_green
                            inRange -> Color(0x1424C354)
                            else -> Color.Transparent
                        }

                        Box(
                            modifier = Modifier
                                .width(cellSize)
                                .height(cellSize)
                                .border(0.5.dp, borderColor, shape)
                                .background(bgColor, shape)
                                .clickable { onDateClick(date) },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = date.dayOfMonth.toString(),
                                style = AppTypography.PretendardTextStyle(
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 7.sp
                                ),
                                color = if (isCurrentMonth) Color(0xFF494E50) else Color(0xFFA0A5B6)
                            )
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun PreviewRangeCalendar() {
    RangeCalendar(
        startDate = LocalDate.of(2026, 1, 11),
        endDate = LocalDate.of(2026, 1, 15),
        onDateClick = {},
        cellSize = 10.dp,
        rows = 6
    )
}
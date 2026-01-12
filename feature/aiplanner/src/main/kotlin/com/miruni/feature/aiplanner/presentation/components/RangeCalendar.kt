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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
    onDateClick: (LocalDate) -> Unit
) {
    var month by remember { mutableStateOf(YearMonth.now()) }

    val first = month.atDay(1)

    val startOffset = first.dayOfWeek.value % 7
    val totalDays = month.lengthOfMonth()
    val cells = ((startOffset + totalDays + 6) / 7) * 7

    val startCellDate = first.minusDays(startOffset.toLong())

    Column(
        modifier = Modifier
            .background(color = Color.White)
    ) {
        // 헤더
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(R.drawable.calendar_left_arrow),
                contentDescription = "이전 달",
                modifier = Modifier
                    .padding(12.dp)
                    .clickable {
                        month = month.minusMonths(1)
                    }
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
                    .padding(12.dp)
                    .clickable {
                        month = month.plusMonths(1)
                    }
            )
        }

        Spacer(modifier = Modifier.height(5.dp))

        // 요일 헤더
        Row(
            Modifier
                .fillMaxWidth()
                .background(Color(0xFFF5F7FA))
        ) {
            listOf("일","월","화","수","목","금","토").forEach {
                Text(
                    text = it,
                    style = AppTypography.PretendardTextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 7.sp
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 날짜 그리드
        for (i in 0 until cells / 7) {
            Row {
                for (j in 0 until 7) {
                    val date = startCellDate.plusDays((i * 7 + j).toLong())
                    val isCurrentMonth = date.month == month.month

                    val isStart = date == startDate
                    val isEnd = date == endDate
                    val inRange =
                        startDate != null && endDate != null && date > startDate && date < endDate

                    val isSingle = startDate != null && endDate == null

                    val shape = when {
                        isSingle && isStart -> RoundedCornerShape(4.dp)
                        isStart -> RoundedCornerShape(topStart = 4.dp, bottomStart = 4.dp)
                        isEnd -> RoundedCornerShape(topEnd = 4.dp, bottomEnd = 4.dp)
                        else -> RoundedCornerShape(0.dp)
                    }
                    val borderColor =
                        if (isStart || isEnd) MainColor.miruni_green
                        else Color.Transparent
                    val bgColor = when {
                        isSingle && isStart -> Color.White
                        isStart || isEnd -> MainColor.miruni_green
                        inRange -> Color(0x1424C354)
                        else -> Color.Transparent
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp)
                            .border(
                                width = 0.5.dp,
                                color = borderColor,
                                shape = shape
                            )
                            .background(
                                color = bgColor,
                                shape = shape
                            )
                            .clickable { onDateClick(date) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = date.dayOfMonth.toString(),
                            style = AppTypography.PretendardTextStyle(
                                fontWeight = FontWeight.Normal,
                                fontSize = 7.sp
                            ),
                            color =
                                if (isCurrentMonth) Color(0xFF494E50)
                                else Color(0xFFA0A5B6)
                        )
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
        onDateClick = {}
    )
}
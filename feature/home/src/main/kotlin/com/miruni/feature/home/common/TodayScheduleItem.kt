package com.miruni.feature.home.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.miruni.core.designsystem.AppTypography
import com.miruni.core.designsystem.MainColor
import com.miruni.feature.home.R
import com.miruni.feature.home.component.CircleText
import com.miruni.feature.home.domain.TodaySchedule

/**
 * 오늘의 일정 아이템 UI
 */
@Composable
fun TodayScheduleItem(
    item: TodaySchedule,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val (backgroundColor, textColor, priorityColor) = remember(isSelected) {
        Triple(
            if (isSelected) MainColor.miruni_green else Color(0xFFF3F3F3),
            if (isSelected) Color.White else Color.Black,
            if (isSelected) Color.Black else MainColor.miruni_green
        )
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(color = backgroundColor, shape = RoundedCornerShape(10.dp))
            .clickable { onClick() }
            .padding(start = 19.dp, end = 13.dp, top = 11.dp, bottom = 20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text( // 시간
                    text = item.time,
                    color = textColor,
                    style = AppTypography.button_regular_9
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier
                        .wrapContentWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text( // 타이틀
                        text = item.title,
                        color = textColor,
                        style = AppTypography.sub_bold_14,
                    )

                    Spacer(modifier = Modifier.width(7.dp))

                    CircleText( // 우선 순위
                        circleSize = 15.dp,
                        text = item.priority,
                        textStyle = AppTypography.button_regular_9,
                        textColor = priorityColor
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text( // 설명
                    text = item.description,
                    color = textColor,
                    style = AppTypography.button_regular_9
                )
            }

            Image(
                painter = painterResource(R.drawable.scheduleplay),
                contentDescription = "go to schedule execution",
                colorFilter = ColorFilter.tint(textColor),
                modifier = Modifier.align(alignment = Alignment.CenterVertically)
            )
        }
    }
}

@Preview
@Composable
private fun PreviewTodayScheduleItem() {
    val item = TodaySchedule(1,"오후 2:20", "UMC 기획안 만들기", "상", "워크북 3페이지 슬라이드 제작")
    val isSelected = true
    TodayScheduleItem(item, isSelected) { }
}
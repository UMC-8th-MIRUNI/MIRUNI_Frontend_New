package com.miruni.feature.aiplanner.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun AiPlannerDatePicker(
    disPlayValue: String? = null, // 선택 날짜 (출력 값)
    onRangeSelected: (LocalDate, LocalDate, LocalTime, LocalTime) -> Unit // 선택 날짜 콜백
) {
    var showPopup by remember { mutableStateOf(false) } // 팝업 열려 있는지 여부
    var anchorBounds by remember { mutableStateOf<Rect?>(null) } // 화면상 Card 위치
    var anchorWidth by remember { mutableStateOf(0f) } // Card 길이

    Box {
        // 선택 날짜 출력 카드
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coords -> // 해당 카드 출력 위치 확인
                    anchorBounds = coords.boundsInRoot() // 해당 카드 좌표
                    anchorWidth = coords.size.width.toFloat() // 해당 카드 가로 길이
                }
                .clickable { showPopup = true }
                .border(1.dp, Color(0xFFB3B3B3), RoundedCornerShape(10.dp)),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Text( // placeholder 및 선택 날짜 출력
                text = disPlayValue ?: "기간을 선택해주세요",
                modifier = Modifier.padding(16.dp),
                color = if (disPlayValue == null) Color.Gray else Color.Black
            )
        }

        // 캘린더 팝업
        if (showPopup && anchorBounds != null) {
            CalendarPopup(
                anchorBounds = anchorBounds!!, // 선택 날짜 출력 카드 위치
                width = with(LocalDensity.current) { anchorWidth.toDp() }, //
                onDismiss = { showPopup = false }, // 외부 클릭 시 닫기
                onRangeSelected = { sd, ed, st, et ->
                    onRangeSelected(sd, ed, st, et)
                    showPopup = false
                }
            )
        }
    }
}
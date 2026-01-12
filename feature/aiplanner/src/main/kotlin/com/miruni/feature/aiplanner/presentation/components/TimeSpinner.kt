package com.miruni.feature.aiplanner.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miruni.core.designsystem.MainColor
import java.time.LocalTime

/**
 * 시간 선택 스피너
 */
@Composable
fun TimeSpinner(
    time: LocalTime,
    onChange: (LocalTime) -> Unit
) {
    var hour by remember { mutableStateOf(if (time.hour % 12 == 0) 12 else time.hour % 12) }
    var minute by remember { mutableStateOf((time.minute / 5) * 5) }
    var isAm by remember { mutableStateOf(time.hour < 12) }

    // 외부 time이 변경되면 내부 상태를 동기화
    LaunchedEffect(time) {
        val newHour = if (time.hour % 12 == 0) 12 else time.hour % 12
        val newMinute = (time.minute / 5) * 5
        val newIsAm = time.hour < 12
        hour = newHour
        minute = newMinute
        isAm = newIsAm
    }

    Box {
        // 선택 영역 (뒤쪽)
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .height(44.dp)
                .padding(horizontal = 12.dp)
                .border(
                    width = 1.dp,
                    color = MainColor.miruni_green,
                    shape = RoundedCornerShape(50)
                )
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(50)
                )
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 시간
            NumberWheel(1..12, hour) { newHour ->
                hour = newHour
                // 즉시 부모로 전달
                val h24 = when {
                    isAm && hour == 12 -> 0
                    !isAm && hour != 12 -> hour + 12
                    else -> hour
                }
                onChange(LocalTime.of(h24, minute))
            }
            // 분
            NumberWheel(0..55 step 5, minute) { newMinute ->
                minute = newMinute
                val h24 = when {
                    isAm && hour == 12 -> 0
                    !isAm && hour != 12 -> hour + 12
                    else -> hour
                }
                onChange(LocalTime.of(h24, minute))
            }
            // 오전, 오후
            AmPmWheel(isAm) { newIsAm ->
                isAm = newIsAm
                val h24 = when {
                    isAm && hour == 12 -> 0
                    !isAm && hour != 12 -> hour + 12
                    else -> hour
                }
                onChange(LocalTime.of(h24, minute))
            }
        }
    }
}

@Composable
fun NumberWheel(
    range: IntProgression,
    selected: Int,
    onSelect: (Int) -> Unit
) {
    val items = range.toList()
    val selectedIndex = items.indexOf(selected).coerceAtLeast(0)

    WheelPicker(
        items = items,
        initialSelectedIndex = selectedIndex,
        modifier = Modifier.width(86.dp).height(220.dp),
        onSelect = { onSelect(items[it]) }
    ) { value, isSelected ->
        Text(
            text = value.toString().padStart(2, '0'),
            fontSize = if (isSelected) 20.sp else 16.sp,
            color = if (isSelected) MainColor.miruni_green else Color.Gray,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun AmPmWheel(
    isAm: Boolean,
    onSelect: (Boolean) -> Unit
) {
    val items = listOf("오전", "오후")
    val selectedIndex = if (isAm) 0 else 1

    WheelPicker(
        items = items,
        initialSelectedIndex = selectedIndex,
        modifier = Modifier.width(86.dp).height(220.dp),
        onSelect = { idx -> onSelect(idx == 0) }
    ) { value, isSelected ->
        Text(
            text = value,
            fontSize = if (isSelected) 18.sp else 14.sp,
            color = if (isSelected) MainColor.miruni_green else Color.Gray,
            textAlign = TextAlign.Center
        )
    }
}

/**
 * 공통 휠 엔진
 * - items: 실제 값 목록
 * - initialSelectedIndex: items 상의 초기 선택 인덱스 (0-based)
 * - onSelect: 스냅 후에 호출되는 실질 선택 인덱스 (0..items.lastIndex)
 *
 * 내부적으로는 위/아래에 null 더미(middle 개수)를 둬서 중앙 정렬을 구현(스냅)
 */
@Composable
fun <T> WheelPicker(
    items: List<T>,
    initialSelectedIndex: Int,
    modifier: Modifier = Modifier,
    onSelect: (Int) -> Unit,
    label: @Composable (T, Boolean) -> Unit
) {
    val visibleCount = 5
    val middle = visibleCount / 2 //
    val padding = middle

    // displayItems: null 패딩 + 실제 items + null 패딩
    val displayItems: List<T?> = List(padding) { null } + items.map { it as T? } + List(padding) { null }

    // initialFirstVisibleIndex: (원래 selectedIndex) — (center - middle) 계산에 의해
    // 우리가 세팅하기 좋은 값: selectedIndex (가운데가 selectedIndex+padding이 되도록)
    val initialFirstVisible = initialSelectedIndex.coerceAtLeast(0)

    val listState = rememberLazyListState(initialFirstVisibleItemIndex = initialFirstVisible)

    // 현재 중앙 디스플레이 인덱스
    val centerIndexState = remember {
        androidx.compose.runtime.derivedStateOf { listState.firstVisibleItemIndex + middle }
    }

    // 스크롤 멈춤 감지하여 스냅 및 선택 전달
    LaunchedEffect(listState) {
        snapshotFlow { listState.isScrollInProgress }
            .collect { scrolling ->
                if (!scrolling) {
                    val center = (listState.firstVisibleItemIndex + middle)
                    val realIndex = (center - padding)
                    if (realIndex in items.indices) {
                        onSelect(realIndex)
                    }
                    // 스냅: center가 잘 정렬되도록 firstVisibleItemIndex 조정
                    listState.animateScrollToItem((center - middle).coerceAtLeast(0))
                }
            }
    }

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(displayItems.size) { displayIdx ->
                val value = displayItems[displayIdx]
                val isSelected = displayIdx == centerIndexState.value
                Box(
                    modifier = Modifier
                        .height(44.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    if (value != null) {
                        label(value, isSelected)
                    } else {
                        // 스페이서
                        Text("", modifier = Modifier.height(44.dp))
                    }
                }
            }
        }
    }
}
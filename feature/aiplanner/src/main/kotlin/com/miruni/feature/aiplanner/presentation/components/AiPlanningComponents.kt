package com.miruni.feature.aiplanner.presentation.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.miruni.core.designsystem.AppTypography
import com.miruni.core.designsystem.Gray
import com.miruni.core.designsystem.MainColor
import com.miruni.feature.aiplanner.R
import com.miruni.core.common.convertBold
import com.miruni.feature.aiplanner.domain.model.PlanInput
import com.miruni.feature.aiplanner.presentation.AiPlannerContract
import com.miruni.feature.aiplanner.presentation.AiPlannerViewModel
import com.miruni.feature.aiplanner.presentation.model.PlanningFormItemUiModel
import java.time.LocalDate
import java.time.LocalTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter

/**
 * 공통 질문 카드
 */
@Composable
fun PlanningQuestionCard(
    title: String,
    onPositionCalculated: (Float, Float) -> Unit
) {
    Card(
        modifier = Modifier
            .widthIn(min = 166.dp)
            .heightIn(min = 55.dp)
            .border(
                width = 1.dp,
                color = Color(0xFFB3B3B3),
                shape = RoundedCornerShape(10.dp)
            )
            .onGloballyPositioned { coords ->
                onPositionCalculated(coords.positionInRoot().y, coords.size.height.toFloat())
            },
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 21.dp, vertical = 18.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = convertBold(text = title),
                style = AppTypography.PretendardTextStyle(
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    letterSpacing = 0.em,
                    lineHeightRatio = 1.32f
                ),
            )
        }
    }
}

/**
 * 공통 결괏값 출력 카드
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PlanningResultCardWrapper(
    item: PlanningFormItemUiModel,
    viewModel: AiPlannerViewModel
) {
    var isExpanded by remember { mutableStateOf(false) }
    // 텍스트 필드 여부 확인
    val isTextInput = item.id != "until" && item.id != "when" && item.id != "priority"

    Column {
        if (isTextInput) {
            // 텍스트 입력 카드
            val textValue = (item.value as? PlanInput.Text)?.text ?: ""
            OutlinedTextField(
                value = textValue,
                onValueChange = { viewModel.setEvent(AiPlannerContract.Event.InputText(item.id, it)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 39.dp),
                placeholder = { Text(item.placeholder, fontSize = 14.sp, color = Color.Gray) },
                shape = RoundedCornerShape(10.dp),
                textStyle =
                    AppTypography.PretendardTextStyle(
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        letterSpacing = 0.em,
                        lineHeightRatio = 1.32f
                    ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MainColor.miruni_green,
                    unfocusedBorderColor = Color(0xFFB3B3B3)
                ),
                singleLine = true
            )

        } else {
            // 날짜 입력 카드, 드롭다운 입력 카드
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 39.dp)
                    .border(
                        width = 1.dp,
                        color = if(isExpanded) MainColor.miruni_green else Color(0xFFB3B3B3),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .clickable { isExpanded = !isExpanded },
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 21.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val displayText = getDisplayText(item)
                    if (displayText.isEmpty()) { // 결괏값 출력 카드 비어 있는 경우
                        Text(
                            text = item.placeholder,
                            style = AppTypography.PretendardTextStyle(
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp,
                                letterSpacing = 0.em,
                                lineHeightRatio = 1.32f
                            ),
                            color = Color.Gray
                        )
                    } else { // 사용자 입력된 경우
                        Text(
                            text = displayText,
                            style = AppTypography.PretendardTextStyle(
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp,
                                letterSpacing = 0.em,
                                lineHeightRatio = 1.32f
                            ),
                            color = Color.Black
                        )
                    }

                    if (item.id == "until") { // 날짜 입력 카드 - 캘린더 이미지 리소스
                        Image(
                            painter = painterResource(id = R.drawable.calendar),
                            contentDescription = "calendar",
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }

        // 선택창 노출 (텍스트 입력 카드는 제외)
        AnimatedVisibility(visible = !isTextInput && isExpanded) {
            Box(modifier = Modifier.padding(top = 4.dp)) {
                when (item.id) {
                    "until" -> PlanningDatePicker(
                        currentValue = item.value as? PlanInput.Date,
                        onComplete = { sd, ed, st, et ->
                            viewModel.setEvent(AiPlannerContract.Event.SelectDate(item.id, sd, ed, st, et))
                            isExpanded = false
                        }
                    )
                    "when", "priority" -> PlanningDropdown(
                        options = getOptionsForId(item.id),
                        onSelect = {
                            viewModel.setEvent(AiPlannerContract.Event.SelectOption(item.id, it))
                            isExpanded = false
                        }
                    )
                }
            }
        }
    }
}

/**
 * 텍스트 표시 헬퍼
 * - Text, Option, Date에 따라 출력값 조정
 */
@RequiresApi(Build.VERSION_CODES.O)
fun getDisplayText(item: PlanningFormItemUiModel): String {
    return when (val v = item.value) {
        is PlanInput.Text -> v.text
        is PlanInput.Option -> v.option
        is PlanInput.Date -> {
            val startStr =
                "${v.startDate.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"))} ${formatTime(v.startTime)}"

            if (v.startDate == v.endDate) {
                "$startStr - ${formatTime(v.endTime)}"
            } else {
                "$startStr - ${v.endDate.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"))} ${formatTime(v.endTime)}"
            }
        }
        null -> ""
    }
}
@RequiresApi(Build.VERSION_CODES.O)
fun formatTime(time: LocalTime): String {
    val ampm = if (time.hour < 12) "오전" else "오후"
    val h = if (time.hour % 12 == 0) 12 else time.hour % 12
    return "$ampm ${String.format("%02d:%02d", h, time.minute)}"
}

/**
 * 드롭다운 옵션
 */
fun getOptionsForId(id: String): List<String> = when(id) {
    "when" -> listOf(
        "랜덤으로 설정",
        "아침 시간(6~9시)",
        "오전 집중 시간 (9~12시)",
        "오후 느슨한 시간 (13~17시)",
        "저녁 시간 (18~21시)",
        "밤 시간 (22~24시)",
        "새벽 (0~6시)"
    )
    "priority" -> listOf("상", "중", "하")
    else -> emptyList()
}

/**
 * 드롭다운 입력창
 */
@Composable
fun PlanningDropdown(options: List<String>, onSelect: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .border(
                width = 1.dp,
                color = Color(0xFFB3B3B3),
                shape = RoundedCornerShape(4.dp)
            )
    ) {
        options.forEach { option -> // 선택 옵션
            Text(
                text = option,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSelect(option) }
                    .padding(12.dp),
                style = AppTypography.PretendardTextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    letterSpacing = 0.em,
                    lineHeightRatio = 1.45f
                ),
                color = Color(0xFF61646B)
            )
            HorizontalDivider(color = Color(0xFFF5F7FA))
        }
    }
}

/**
 * 날짜 선택 입력
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PlanningDatePicker(
    currentValue: PlanInput.Date?,
    onComplete: (LocalDate, LocalDate, LocalTime, LocalTime) -> Unit
) {
    // 상태 관리
    var yearMonth by remember { mutableStateOf(YearMonth.now()) }
    var startDate by remember { mutableStateOf(currentValue?.startDate ?: LocalDate.now()) }
    var endDate by remember { mutableStateOf(currentValue?.endDate ?: LocalDate.now()) }
    var startTime by remember { mutableStateOf(currentValue?.startTime ?: LocalTime.of(9, 0)) }
    var endTime by remember { mutableStateOf(currentValue?.endTime ?: LocalTime.of(18, 0)) }

    // 날짜 선택 로직 (단일 -> 범위)
    var selectionMode by remember { mutableStateOf(
        if(currentValue?.startDate == currentValue?.endDate) "SINGLE"
        else "RANGE"
    ) }

    Column(
        modifier = Modifier
            .width(286.dp)
            .background(Color.White)
            .border(1.dp, Color(0xFFE5E7F0), RoundedCornerShape(8.dp))
            .padding(top = 10.dp, bottom = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 월 선택 헤더
        CalendarHeader(yearMonth) { yearMonth = yearMonth.plusMonths(it.toLong()) }

        // 요일 헤더
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 7.dp, vertical = 5.dp)
                .background(Color(0xFFF5F7FA)),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            listOf("일", "월", "화", "수", "목", "금", "토").forEach {
                Text(
                    text = it,
                    modifier = Modifier.padding(vertical = 2.dp),
                    style = AppTypography.PretendardTextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 10.sp,
                        letterSpacing = (0.1).em
                    ),
                    color = Color(0xFF424242)
                )
            }
        }

        // 날짜 그리드
        CalendarGrid(
            yearMonth = yearMonth,
            startDate = startDate,
            endDate = endDate,
            onDateClick = { date ->
                if (selectionMode == "SINGLE") {
                    if (date != startDate) {
                        // 두번째 클릭: 범위 선택 시작
                        if (date.isBefore(startDate)) {
                            endDate = startDate
                            startDate = date
                        } else {
                            endDate = date
                        }
                        selectionMode = "RANGE"
                    } else {
                        // 같은 날짜 클릭: 유지
                        startDate = date
                        endDate = date
                    }
                } else {
                    // 범위 모드에서 클릭: 초기화 후 단일 선택
                    startDate = date
                    endDate = date
                    selectionMode = "SINGLE"
                }
            }
        )

        Spacer(modifier = Modifier.height(10.dp))
        HorizontalDivider(color = Color(0xFFF5F7FA))
        Spacer(modifier = Modifier.height(10.dp))

        // 시간 선택 영역
        TimeSelectionArea(
            startTime = startTime,
            endTime = endTime,
            onStartTimeChanged = { startTime = it },
            onEndTimeChanged = { endTime = it }
        )

        Spacer(modifier = Modifier.height(10.dp))

        // 완료 버튼
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(MainColor.miruni_green)
                    .clickable { onComplete(startDate, endDate, startTime, endTime) }
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "완료",
                    style = AppTypography.PretendardTextStyle(
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp,
                        letterSpacing = 0.em
                    ),
                    color = Color.White
                )
            }
        }
    }
}

/** 캘린더 헤더 (연월 선택 헤더) */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarHeader(yearMonth: YearMonth, onMove: (Int) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 7.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.calendar_left_arrow),
            contentDescription = null,
            modifier = Modifier
                .size(11.dp)
                .clickable { onMove(-1) }
        )
        Text(
            text = "${yearMonth.year}년 ${yearMonth.monthValue}월",
            fontWeight = FontWeight.Medium,
            fontSize = 11.sp,
            color = Color(0xFF494E50)
        )
        Icon(
            painter = painterResource(R.drawable.calendar_right_arrow),
            contentDescription = null,
            modifier = Modifier
                .size(11.dp)
                .clickable { onMove(1) }
        )
    }
}

/** 달력 그리드 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarGrid(
    yearMonth: YearMonth,
    startDate: LocalDate,
    endDate: LocalDate,
    onDateClick: (LocalDate) -> Unit
) {
    val firstDayOfMonth = yearMonth.atDay(1)
    val lastDayOfPrevMonth = yearMonth.minusMonths(1).atEndOfMonth()
    val daysInMonth = yearMonth.lengthOfMonth()
    val dayOfWeekOffset = firstDayOfMonth.dayOfWeek.value % 7

    Column(modifier = Modifier.padding(horizontal = 7.dp)) {
        var dayCounter = 1
        var nextMonthDayCounter = 1

        for (row in 0 until 6) {
            Row(modifier = Modifier.fillMaxWidth()) { // Arrangement 제거
                for (col in 0 until 7) {
                    val cellIndex = row * 7 + col
                    val date: LocalDate
                    val isCurrentMonth: Boolean

                    if (cellIndex < dayOfWeekOffset) {
                        date = lastDayOfPrevMonth.minusDays((dayOfWeekOffset - cellIndex - 1).toLong())
                        isCurrentMonth = false
                    } else if (dayCounter <= daysInMonth) {
                        date = yearMonth.atDay(dayCounter)
                        dayCounter++
                        isCurrentMonth = true
                    } else {
                        date = yearMonth.plusMonths(1).atDay(nextMonthDayCounter)
                        nextMonthDayCounter++
                        isCurrentMonth = false
                    }

                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                        CalendarDayTile(
                            date = date,
                            isCurrentMonth = isCurrentMonth,
                            isSelectedStart = date == startDate,
                            isSelectedEnd = date == endDate,
                            isInRange = date.isAfter(startDate) && date.isBefore(endDate),
                            onClick = { onDateClick(date) }
                        )
                    }
                }
            }
            // 행 사이 간격도 줄여서 연결성 강화 (원할 경우 0.dp)
            Spacer(modifier = Modifier.height(2.dp))
        }
    }
}

/** 날짜 타일 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarDayTile(
    date: LocalDate,
    isCurrentMonth: Boolean,
    isSelectedStart: Boolean,
    isSelectedEnd: Boolean,
    isInRange: Boolean,
    onClick: () -> Unit
) {
    val bgColor = when {
        isSelectedStart || isSelectedEnd -> MainColor.miruni_green
        isInRange -> Color(0xFFEEFBF2)
        else -> Color.Transparent
    }
    val textColor = when {
        isSelectedStart || isSelectedEnd -> Color.White
        isCurrentMonth -> Color(0xFF494E50)
        else -> Color(0xFFA0A5B6)
    }

    val shape = when {
        // 시작일인데 종료일과 다를 때 (왼쪽만 둥글게)
        isSelectedStart && !isSelectedEnd && date.isBefore(date.plusDays(1)) ->
            RoundedCornerShape(topStart = 7.dp, bottomStart = 7.dp)
        // 종료일인데 시작일과 다를 때 (오른쪽만 둥글게)
        isSelectedEnd && !isSelectedStart ->
            RoundedCornerShape(topEnd = 7.dp, bottomEnd = 7.dp)
        // 범위 중간 (각진 사각형)
        isInRange -> RoundedCornerShape(0.dp)
        // 단일 선택 (전체 둥글게)
        isSelectedStart || isSelectedEnd -> RoundedCornerShape(7.dp)
        else -> RoundedCornerShape(0.dp)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(22.dp)
            .background(bgColor, shape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = date.dayOfMonth.toString(),
            style = AppTypography.PretendardTextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 8.sp,
                letterSpacing = 0.em
            ),
            color = textColor
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TimeSelectionArea(
    startTime: LocalTime,
    endTime: LocalTime,
    onStartTimeChanged: (LocalTime) -> Unit,
    onEndTimeChanged: (LocalTime) -> Unit
) {
    var activeSpinner by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.padding(horizontal = 47.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            // 시작 시간 박스
            TimeBox(time = startTime, isActive = activeSpinner == "START") {
                activeSpinner = if (activeSpinner == "START") null else "START"
            }

            Text(
                text = "-",
                modifier = Modifier.padding(horizontal = 13.dp),
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp
            )

            // 종료 시간 박스
            TimeBox(time = endTime, isActive = activeSpinner == "END") {
                activeSpinner = if (activeSpinner == "END") null else "END"
            }
        }

        // 스피너 노출
        if (activeSpinner != null) {
            Spacer(modifier = Modifier.height(5.dp))
            val initialTime = if (activeSpinner == "START") startTime else endTime
            TimeSpinner(
                initialTime = initialTime,
                onTimeSelected = {
                    if (activeSpinner == "START") onStartTimeChanged(it) else onEndTimeChanged(it)
                }
            )
        }
    }
}

/** 선택 시간 노출 박스 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TimeBox(time: LocalTime, isActive: Boolean, onClick: () -> Unit) {
    val borderColor = if (isActive) MainColor.miruni_green else Color(0xFFE5E7F0)
    val elevation = if (isActive) 3.dp else 0.dp

    Surface(
        shape = RoundedCornerShape(0.dp),
        border = BorderStroke(1.dp, borderColor),
        shadowElevation = elevation,
        color = Color.White,
        modifier = Modifier
            .size(64.dp, 17.dp)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 3.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = customFormatTime(time),
                style = AppTypography.PretendardTextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 10.sp,
                    letterSpacing = 0.em
                ),
                textAlign = TextAlign.Center
            )
            Icon(painterResource(R.drawable.bottom_arrow), contentDescription = null, modifier = Modifier.size(8.dp))
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun customFormatTime(time: LocalTime): String {
    val ampm = if (time.hour < 12) "오전" else "오후"
    val h = if (time.hour % 12 == 0) 12 else time.hour % 12
    return "$h:${String.format("%02d", time.minute)} $ampm"
}

/** 시간 선택 스피너 ㄺ*/
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TimeSpinner(
    initialTime: LocalTime,
    onTimeSelected: (LocalTime) -> Unit
) {
    val ampmList = listOf("오전", "오후")
    val hourList = (1..12).toList()
    val minuteList = (0..55 step 5).toList()

    var selectedAmPm by remember { mutableStateOf(if (initialTime.hour < 12) "오전" else "오후") }
    var selectedHour by remember { mutableStateOf(if (initialTime.hour % 12 == 0) 12 else initialTime.hour % 12) }
    var selectedMinute by remember { mutableStateOf((initialTime.minute / 5) * 5) }

    // 값 변경 시 즉시 콜백 호출
    LaunchedEffect(selectedAmPm, selectedHour, selectedMinute) {
        val hour24 = when {
            selectedAmPm == "오전" && selectedHour == 12 -> 0
            selectedAmPm == "오전" -> selectedHour
            selectedAmPm == "오후" && selectedHour == 12 -> 12
            else -> selectedHour + 12
        }
        onTimeSelected(LocalTime.of(hour24, selectedMinute))
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp) // 텍스트 5줄 정도 높이
            .background(Color(0xFFF9F9F9))
    ) {
        // 중앙 하이라이트
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .height(26.dp)
                .padding(horizontal = 4.dp)
                .background(Color.White, RoundedCornerShape(20.dp))
                .border(1.dp, MainColor.miruni_green, RoundedCornerShape(20.dp))
        )

        Row(modifier = Modifier.fillMaxSize()) {
            WheelColumn(
                items = ampmList,
                initialItem = selectedAmPm,
                modifier = Modifier.weight(1f),
                onItemSelected = { selectedAmPm = it }
            )
            WheelColumn(
                items = hourList,
                initialItem = selectedHour,
                modifier = Modifier.weight(1f),
                onItemSelected = { selectedHour = it }
            )
            WheelColumn(
                items = minuteList,
                initialItem = selectedMinute,
                modifier = Modifier.weight(1f),
                format = { String.format("%02d", it) },
                onItemSelected = { selectedMinute = it }
            )
        }
    }
}

/**
 * 공통 스피너 컴포넌트
 */
@Composable
fun <T> WheelColumn(
    items: List<T>,
    initialItem: T,
    modifier: Modifier = Modifier,
    format: (T) -> String = { it.toString() },
    onItemSelected: (T) -> Unit
) {
    val itemHeight = 22.dp
    val listState = rememberLazyListState(
        initialFirstVisibleItemIndex = items.indexOf(initialItem)
    )
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)

    // 스크롤 멈춤 감지 및 데이터 업데이트
    LaunchedEffect(listState.isScrollInProgress) {
        if (!listState.isScrollInProgress) {
            val centerIndex = listState.firstVisibleItemIndex
            onItemSelected(items[centerIndex % items.size])
        }
    }

    LazyColumn(
        state = listState,
        flingBehavior = flingBehavior,
        modifier = modifier.height(itemHeight * 5),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(vertical = itemHeight * 2) // 상하 2줄씩 비움
    ) {
        items(items.size) { index ->
            val distance = Math.abs(index - listState.firstVisibleItemIndex)
            val textColor = when (distance) {
                0 -> Color(0xFF040404) // 선택됨
                1 -> Gray.gray_700
                else -> Gray.gray_500
            }

            Box(
                modifier = Modifier
                    .height(itemHeight)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = format(items[index]),
                    style = AppTypography.PretendardTextStyle(
                        fontWeight = if (distance == 0) FontWeight.Medium else FontWeight.Normal,
                        fontSize = 8.sp,
                        letterSpacing = 0.em
                    ),
                    color = textColor,
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun PreviewComponents() {
    TimeBox(
        time = LocalTime.of(10, 30),
        isActive = true,
        onClick = {}
    )
}

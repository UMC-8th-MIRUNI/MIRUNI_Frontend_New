package com.miruni.feature.calendar.presentation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.miruni.core.designsystem.AppTypography
import com.miruni.core.designsystem.Black
import com.miruni.core.designsystem.Gray
import com.miruni.core.designsystem.MainColor
import com.miruni.core.designsystem.MiruniTheme
import com.miruni.core.designsystem.White
import com.miruni.feature.calendar.CalendarContract
import com.miruni.feature.calendar.CalendarViewModel
import com.miruni.feature.calendar.R
import com.miruni.feature.calendar.presentation.components.AddScheduleBottomSheet
import com.miruni.feature.calendar.presentation.components.AiPlanningButton
import com.miruni.feature.calendar.presentation.components.ScheduleBottomSheet
import com.miruni.feature.calendar.presentation.components.ScheduleItem
import com.miruni.feature.calendar.presentation.components.YearMonthPickerDialog
import com.miruni.feature.calendar.domain.model.PlanPriority
import com.miruni.feature.calendar.presentation.components.EmptySchedule
import com.miruni.feature.calendar.presentation.model.AddScheduleState
import com.miruni.feature.calendar.presentation.model.ScheduleUiModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.YearMonth

@Composable
fun CalendarRoute(
    viewModel: CalendarViewModel = hiltViewModel(),
) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()

    CalendarScreen(
        state = state,
        onMonthChanged = { yearMonth ->
            viewModel.onMonthChanged(yearMonth)
        },
        onAiPlanningClicked = {
            viewModel.setEvent(CalendarContract.Event.AiPlannerClicked)
        },
        onDayClicked = { date ->
            viewModel.setEvent(CalendarContract.Event.DayClicked(date))
        },
        onPlanClicked = { plan ->
            viewModel.setEvent(CalendarContract.Event.PlanClicked(plan))
        },
        onCheckBoxClicked = { plan ->
            viewModel.setEvent(CalendarContract.Event.PlanChecked(plan = plan, expectedTime = plan.expectedTime))
        },
        onAddConfirmClicked = { addScheduleState ->
            viewModel.setEvent(CalendarContract.Event.SubmitPlan(addScheduleState))
        },
        changeIsPlanCreationSheetOpened = {
            viewModel.setEvent(CalendarContract.Event.ChangeIsPlanCreationOpened)
        }
    )
}

@SuppressLint("NewApi")
@Composable
fun CalendarScreen(
    state: CalendarContract.State,
    modifier: Modifier = Modifier,
    onMonthChanged: (YearMonth) -> Unit,
    onAiPlanningClicked: () -> Unit,
    onDayClicked: (java.time.LocalDate) -> Unit = {},
    onPlanClicked: (ScheduleUiModel) -> Unit,
    onCheckBoxClicked: (ScheduleUiModel) -> Unit,
    onAddConfirmClicked: (AddScheduleState) -> Unit,
    changeIsPlanCreationSheetOpened: () -> Unit
) {
    val scope = rememberCoroutineScope()
    var showYearMonthPicker by remember { mutableStateOf(false) }
    var selectedSchedule by remember { mutableStateOf<ScheduleUiModel?>(null) }

    val startMonth = remember { YearMonth.now().minusMonths(24) }
    val endMonth = remember { YearMonth.now().plusMonths(24) }

    val calendarState = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = state.currentMonth,
        firstDayOfWeek = DayOfWeek.SUNDAY
    )

    LaunchedEffect(calendarState) {
        snapshotFlow { calendarState.firstVisibleMonth.yearMonth }
            .distinctUntilChanged()
            .collect { yearMonth ->
                onMonthChanged(yearMonth)
            }
    }

    LaunchedEffect(state.unfinishedDailyPlans) {
        Log.d("Calendar", "List changed: ${state.unfinishedDailyPlans.size}")
    }


    if (showYearMonthPicker) {
        YearMonthPickerDialog(
            currentYearMonth = state.currentMonth,
            onDismiss = { showYearMonthPicker = false },
            onConfirm = {
                onMonthChanged(it)
            }
        )
    }

    if (state.isAddScheduleSheetOpened) {
        AddScheduleBottomSheet(
            isLoading = state.isLoading,
            selectedDate = state.selectedDate,
            onConfirm = {
                onAddConfirmClicked(it)
            },
            onDismiss = { changeIsPlanCreationSheetOpened() }
        )
    }

    selectedSchedule?.let { schedule ->
        ScheduleBottomSheet(
            title = "타이틀",
            description = "설명",
            priority = PlanPriority.MEDIUM,
            onDismiss = {
                selectedSchedule = null
            },
            onEdit = {},
            onDelete = {}
        )
    }

    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        item {
            CalendarHeader(
                currentMonth = state.currentMonth,
                onYearMonthClick = {
                    showYearMonthPicker = true
                },
                onPrevMonthClick = {
                    scope.launch {
                        calendarState.animateScrollToMonth(
                            calendarState.firstVisibleMonth.yearMonth.minusMonths(1)
                        )
                    }
                },
                onNextMonthClick = {
                    scope.launch {
                        calendarState.animateScrollToMonth(
                            calendarState.firstVisibleMonth.yearMonth.plusMonths(1)
                        )
                    }
                },
                onAddCalendarClick = { changeIsPlanCreationSheetOpened() }
            )
        }
        item {
            HorizontalCalendar(
                state = calendarState,
                monthHeader = {
                    WeekDayHeader()
                },
                dayContent = { day ->
                    DayCell(
                        day = day,
                        isSelected = state.selectedDate == day.date,
                        isToday = state.today == day.date,
                        onClick = { onDayClicked(day.date) }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
            )
        }

        item {

        }

        item {
            val unfinished = state.unfinishedDailyPlans
            val finished = state.finishedDailyPlans

            when {
                unfinished.isEmpty() && finished.isEmpty() -> {
                    EmptySchedule("오늘은 일정이 없어요.")
                }

                unfinished.isNotEmpty() -> {
                    unfinished.forEach { plan ->
                        ScheduleItem(
                            schedule = plan,
                            onClick = { onPlanClicked(plan) },
                            onCheckedChange = { onCheckBoxClicked(plan) }
                        )
                    }

                    if (finished.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(25.dp))
                        Text(
                            text = "완료",
                            style = AppTypography.sub_bold_14,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                        finished.forEach { plan ->
                            ScheduleItem(
                                schedule = plan,
                                onClick = { onPlanClicked(plan) },
                                onCheckedChange = { onCheckBoxClicked(plan) }
                            )
                        }
                    } else {
                        Text(
                            text = "완료된 일정이 없어요.",
                            style = AppTypography.body_regular_12,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@SuppressLint("NewApi")
@Composable
fun CalendarHeader(
    currentMonth: YearMonth,
    modifier: Modifier = Modifier,
    onYearMonthClick: () -> Unit,
    onPrevMonthClick: () -> Unit,
    onNextMonthClick: () -> Unit,
    onAddCalendarClick: () -> Unit,
) {
    val currentMonthLabel = remember(currentMonth) {
        "${currentMonth.year}년 ${currentMonth.monthValue}월"
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = currentMonthLabel,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.clickable { onYearMonthClick() }
        )

        Row {
            IconButton(onClick = onPrevMonthClick) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = "이전 달"
                )
            }
            IconButton(onClick = onNextMonthClick) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "다음 달"
                )
            }
            IconButton(onClick = onAddCalendarClick) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.btn_plus),
                    contentDescription = "일정 추가"
                )
            }
        }
    }
}

@Composable
fun WeekDayHeader(
    modifier: Modifier = Modifier,
) {
    val daysOfWeek = listOf("일", "월", "화", "수", "목", "금", "토")

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
    ) {
        daysOfWeek.forEach { day ->
            Text(
                text = day,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                color = Gray.gray_500
            )
        }
    }
}

@SuppressLint("NewApi")
@Composable
fun DayCell(
    day: CalendarDay,
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    isToday: Boolean,
    onClick: () -> Unit,
) {
    val isCurrentMonth = day.position == DayPosition.MonthDate
    val textColor = when {
        isToday -> MainColor.miruni_green
        !isCurrentMonth -> Gray.gray_500
        isCurrentMonth -> Black
        else -> White
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(2.dp)
            .clip(CircleShape)
            .clickable(
                enabled = isCurrentMonth,
                onClick = onClick
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // 날짜 숫자 + 원형 배경
        Box(
            modifier = Modifier
                .size(36.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = day.date.dayOfMonth.toString(),
                style = AppTypography.sub_medium_14,
                color = textColor
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
    }
}

@Preview
@Composable
fun CalendarScreenPreview() {
    MiruniTheme {
        CalendarScreen(
            state = CalendarContract.State(),
            onMonthChanged = {},
            onAiPlanningClicked = {},
            onPlanClicked = {},
            onCheckBoxClicked = {},
            onAddConfirmClicked = {},
            changeIsPlanCreationSheetOpened = {}
        )
    }
}
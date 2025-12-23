package com.miruni.feature.calendar.model

import android.annotation.SuppressLint
import com.miruni.feature.calendar.components.Priority
import java.time.LocalDate

@SuppressLint("NewApi")
data class AddScheduleState(
    val title: String = "",
    val dateTimeRange: DateTimeRangeState = DateTimeRangeState(
        startDate = LocalDate.now(),
        endDate = LocalDate.now()
    ),
    val priority: Priority = Priority.MEDIUM,
    val description: String = "",
)
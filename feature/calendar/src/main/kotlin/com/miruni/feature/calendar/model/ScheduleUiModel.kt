package com.miruni.feature.calendar.model

import android.annotation.SuppressLint
import com.miruni.feature.calendar.components.Priority
import java.time.LocalTime

data class ScheduleUiModel(
    val id: String,
    val title: String,
    val description: String = "",
    val startTime: LocalTime,
    val endTime: LocalTime,
    val priority: Priority,
    val isCompleted: Boolean = false,
) {
    val timeRange: String
        get() = "${formatTime(startTime)} - ${formatTime(endTime)}"

    @SuppressLint("NewApi")
    private fun formatTime(time: LocalTime): String {
        val hour = when {
            time.hour == 0 -> 12
            time.hour == 12 -> 12
            time.hour > 12 -> time.hour - 12
            else -> time.hour
        }
        val minute = time.minute.toString().padStart(2, '0')
        val amPm = if (time.hour < 12) "오전" else "오후"
        return "$amPm $hour:$minute"
    }
}
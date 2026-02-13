package com.miruni.feature.calendar.data.dto.request

data class PatchPlanRequest(
    val title: String,
    val description: String,
    val date: String, // yyyy-MM-dd
    val startTime: String, // HH:mm
    val endTime: String, // HH:mm
    val priority: String
)

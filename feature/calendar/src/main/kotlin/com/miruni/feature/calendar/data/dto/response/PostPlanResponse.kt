package com.miruni.feature.calendar.data.dto.response

data class BasicPlanResponse(
    val id: Int,
    val userId: Int,
    val title: String,
    val description: String,
    val startDateTime: String,
    val endDateTime: String,
    val expectedDuration: Int,
    val status: String,
    val priority: String
)
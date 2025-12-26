package com.miruni.feature.home.domain

/**
 * 오늘의 일정
 */
data class TodaySchedule(
    val id: Long,
    val time: String,
    val title: String,
    val priority: String,
    val description: String
)
package com.miruni.feature.calendar.domain.model

data class Plan(
    val planId: Int,
    val planType: PlanType,
    val title: String,
    val subTitle: String,
    val description: String = "",
    val startTime: String,
    val endTime: String,
    val priority: PlanPriority,
    val isDone: Boolean = false
)
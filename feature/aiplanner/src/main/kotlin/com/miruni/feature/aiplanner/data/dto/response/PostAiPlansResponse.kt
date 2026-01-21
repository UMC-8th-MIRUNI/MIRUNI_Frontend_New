package com.miruni.feature.aiplanner.data.dto.response

data class PostAiPlansResponse(
    val planId: Long,
    val aiPlanId: Long,
    val title: String,
    /** yyyy-MM-dd */
    val deadline: String,
    val taskRange: String,
    val priority: String,
    /** yyyy-MM-dd */
    val scheduledDate: String,
    val description: String,
    val expectedDuration: Int,
    /** hh:mm:ss */
    val startTime: String,
    /** hh:mm:ss */
    val endTime: String
)
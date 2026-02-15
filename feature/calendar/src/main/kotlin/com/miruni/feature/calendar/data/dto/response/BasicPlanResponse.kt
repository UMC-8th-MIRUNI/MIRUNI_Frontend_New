package com.miruni.feature.calendar.data.dto.response

import com.miruni.feature.calendar.domain.model.BasicPlan
import com.miruni.feature.calendar.domain.model.PlanPriority
import com.miruni.feature.calendar.domain.model.PlanStatus

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
) {
    fun toDomain(): BasicPlan {
        return BasicPlan(
            id = id,
            userId = userId,
            title = title,
            description = description,
            startDateTime = startDateTime,
            endDateTime = endDateTime,
            expectedDuration = expectedDuration,
            status = PlanStatus.fromServer(status),
            priority = PlanPriority.fromServer(priority)
        )
    }
}
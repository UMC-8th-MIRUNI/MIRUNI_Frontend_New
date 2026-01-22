package com.miruni.feature.home.data.dto.response

import com.miruni.feature.home.domain.model.DailyPlan
import com.miruni.feature.home.domain.model.PlanPriority
import com.miruni.feature.home.domain.model.PlanType

data class DailyPlanDto(
    val planType: String, // 일정 유형
    val planId: Int, // AI 상위 일정 or 일반 일정 ID
    val title: String, // AI 상위 일정 or 일반 일정 제목
    val subTitle: String?, // 하위 일정 제목
    val startTime: String, // 시작 시간 "오전(오후) HH:mm"
    val endTime: String, // 종료 시간
    val priority: String, // 우선순위
    val isDone: Boolean // 완료 여부
) {
    fun toDomain(): DailyPlan {
        return DailyPlan(
            planType = PlanType.fromServer(planType),
            planId = planId,
            title = title,
            subTitle = subTitle,
            startTime = startTime,
            endTime = endTime,
            priority = PlanPriority.fromServer(priority),
            isDone = isDone
        )
    }
}

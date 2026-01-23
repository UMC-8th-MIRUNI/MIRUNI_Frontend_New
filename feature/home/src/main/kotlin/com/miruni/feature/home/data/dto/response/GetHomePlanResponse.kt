package com.miruni.feature.home.data.dto.response

import com.miruni.feature.home.domain.model.HomePlanInfo

data class GetHomePlanResponse(
    val progressRate: Int,
    val todayPlans: List<DailyPlanDto>?
) {
    fun toDomain(): HomePlanInfo {
        return HomePlanInfo(
            progressRate = progressRate,
            todayPlans = todayPlans?.map { it.toDomain() }
        )
    }
}
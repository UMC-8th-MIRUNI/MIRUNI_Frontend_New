package com.miruni.feature.home.domain.model

data class HomePlanInfo(
    val progressRate: Int,
    val todayPlans: List<DailyPlan>?
)

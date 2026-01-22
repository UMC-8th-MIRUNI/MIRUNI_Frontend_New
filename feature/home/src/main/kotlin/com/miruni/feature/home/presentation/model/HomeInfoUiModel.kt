package com.miruni.feature.home.presentation.model

import com.miruni.feature.home.domain.model.HomePlanInfo

data class HomeInfoUiModel(
    val progressRate: Int,
    val todayPlans: List<TodayPlanUiModel>?
)
fun HomePlanInfo.toUiModel(): HomeInfoUiModel {
    return HomeInfoUiModel(
        progressRate = progressRate,
        todayPlans = todayPlans?.map { it.toUiModel() }
    )
}
package com.miruni.feature.aiplanner.presentation

import com.miruni.feature.aiplanner.common.ViewEvent
import com.miruni.feature.aiplanner.common.ViewSideEffect
import com.miruni.feature.aiplanner.common.ViewState
import com.miruni.feature.aiplanner.domain.AiPlannerUiModel

object AiPlannerContract {
    sealed class Event : ViewEvent {
        object CompleteOnboarding : Event() // 온보딩 종료
    }

    data class State(
        val isLoading: Boolean = false, // AI 플래너 서버 데이터 로딩중 여부
        val aiPlans: List<AiPlannerUiModel> = emptyList(), // AI 플랜
        val remain: Int = 0 // AI 플래닝 사용 잔여 횟수
    ) : ViewState

    sealed class Effect : ViewSideEffect
}
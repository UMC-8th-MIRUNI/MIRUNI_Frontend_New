package com.miruni.feature.aiplanner.presentation

import com.miruni.feature.aiplanner.common.ViewEvent
import com.miruni.feature.aiplanner.common.ViewSideEffect
import com.miruni.feature.aiplanner.common.ViewState
import com.miruni.feature.aiplanner.presentation.model.AiPlannerUiModel
import com.miruni.feature.aiplanner.presentation.model.PlanningFormItemUiModel
import java.time.LocalDate
import java.time.LocalTime

object AiPlannerContract {
    sealed class Event : ViewEvent {
        /** AI 플래너 온보딩 */
        object CompleteOnboarding : Event() // 온보딩 종료

        /** AI 플래너 플래닝 */
        data class InputText(val id: String, val text: String) : Event()
        data class SelectDate(
            val id: String,
            val startDate: LocalDate,
            val endDate: LocalDate,
            val startTime: LocalTime,
            val endTime: LocalTime,
        ) : Event()
        data class SelectOption(val id: String, val option: String) : Event()
    }

    data class State(
        /** AI 플래너 메인 */
        val isLoading: Boolean = false, // AI 플래너 서버 데이터 로딩중 여부
        val aiPlans: List<AiPlannerUiModel> = emptyList(), // AI 플랜
        val remain: Int = 0, // AI 플래닝 사용 잔여 횟수

        /** AI 플래너 플래닝 */
        val forms: List<PlanningFormItemUiModel> = emptyList()
    ) : ViewState

    sealed class Effect : ViewSideEffect
}
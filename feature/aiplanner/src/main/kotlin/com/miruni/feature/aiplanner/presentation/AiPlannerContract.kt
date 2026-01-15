package com.miruni.feature.aiplanner.presentation

import com.miruni.core.common.ViewEvent
import com.miruni.core.common.ViewSideEffect
import com.miruni.core.common.ViewState
import com.miruni.feature.aiplanner.presentation.model.AiPlannerUiModel
import com.miruni.feature.aiplanner.presentation.model.PlanningFormItemUiModel
import java.time.LocalDate
import java.time.LocalTime

object AiPlannerContract {
    sealed class Event : ViewEvent {
        /** 공통 */
        object ClickBack : Event() // 돌아가기

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
        object ClickSubmit : Event() // 다음 버튼 클릭

        /** AI 플래너 로딩 */
        object ShowPlanningLoading : Event() // 로딩 출력
        object ClickConfirm : Event() // 로딩 화면 완료 버튼 클릭
    }

    data class State(
        /** AI 플래너 메인 */
        val isMainLoading: Boolean = false, // AI 플래너 서버 데이터 로딩중 여부
        val aiPlans: List<AiPlannerUiModel> = emptyList(), // AI 플랜
        val remain: Int = 0, // AI 플래닝 사용 잔여 횟수

        /** AI 플래너 플래닝 */
        val forms: List<PlanningFormItemUiModel> = emptyList(),

        /** AI 플래너 로딩 */
        val isPlanningLoading: Boolean = false, // AI 플래닝 로딩중 여부
        val isFinishedPlanningLoading: Boolean = false, // AI 플래닝 로딩 완료
        val userName: String? = null
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object ToSchedule : Effect() // 스케줄 표 화면 이동
            object ToLoading : Effect() // 플래닝 로딩 화면 이동
        }
        object PopBack : Effect() // 돌아가기
    }
}
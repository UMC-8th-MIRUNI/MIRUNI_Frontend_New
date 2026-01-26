package com.miruni.feature.aiplanner.presentation

import com.miruni.core.common.ViewEvent
import com.miruni.core.common.ViewSideEffect
import com.miruni.core.common.ViewState
import com.miruni.feature.aiplanner.domain.model.PlanPreview
import com.miruni.feature.aiplanner.presentation.model.AiPlanUiModel
import com.miruni.feature.aiplanner.presentation.model.PlanUiModel
import com.miruni.feature.aiplanner.presentation.model.PlanningFormItemUiModel
import com.miruni.feature.aiplanner.presentation.model.ScheduleSource
import java.time.LocalDate
import java.time.LocalTime

object AiPlannerContract {
    sealed class Event : ViewEvent {
        /** 공통 */
        object ClickBack : Event() // 돌아가기
        object OnMain : Event()

        /** AI 플래너 온보딩 */
        object CompleteOnboarding : Event() // 온보딩 종료

        /** AI 플래너 플래닝 */
        object ClearForm : Event() // 폼 초기화
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

        /** AI 플래너 스케줄 표 */
        data class EnterSchedule( // 스케줄 화면 진입
            val from: ScheduleSource, // 어디서 왔는지
            val planId: Int? = null
        ) : Event()
        object ClickMenu : Event()
        object ClickEdit : Event()
        object ClickDeleteAll : Event() // 스케줄표 전체 삭제
        data class ClickDeleteItem( // AI 플랜 개별 삭제
            val planId: Int,
            val aiPlanIds: List<Int>
        ) : Event()
        data class ClickCompleteEdit(
            val planId: Int,
            val title: String,
            // 상위 일정 정보
            val deadline: String,
            val taskRange: String,
            val priority: String,
            // 하위 세부 일정 리스트
            val aiPlans: List<AiPlanUiModel>
        ) : Event()
    }

    data class State(
        /** AI 플래너 메인 */
        val isMainLoading: Boolean = false, // AI 플래너 서버 데이터 로딩중 여부
        val aiPlans: List<PlanPreview> = emptyList(), // AI 플랜 미리보기
        val remain: Int = 0, // AI 플래닝 사용 잔여 횟수

        /** AI 플래너 플래닝 */
        val forms: List<PlanningFormItemUiModel> = emptyList(),

        /** AI 플래너 로딩 */
        val isPlanningLoading: Boolean = false, // AI 플래닝 로딩중 여부
        val isFinishedPlanningLoading: Boolean = false, // AI 플래닝 로딩 완료
        val userName: String? = null,

        /** AI 플래너 스케줄표 */
        val plan: PlanUiModel? = null, // AI 플랜
        val showMenu: Boolean = false, // 메뉴 노출 여부
        val isEditMode: Boolean = false // 수정 모드 여부
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object ToSchedule : Effect() // 스케줄 표 화면 이동
            object ToLoading : Effect() // 플래닝 로딩 화면 이동
            object ToMain : Effect() // 메인 화면 이동
        }
        data class ShowToast(val message: String) : Effect()
        object PopBack : Effect() // 돌아가기
    }
}
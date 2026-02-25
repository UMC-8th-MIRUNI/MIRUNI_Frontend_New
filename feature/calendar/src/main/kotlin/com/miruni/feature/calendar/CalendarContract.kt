package com.miruni.feature.calendar

import android.annotation.SuppressLint
import com.miruni.core.common.ViewEvent
import com.miruni.core.common.ViewSideEffect
import com.miruni.core.common.ViewState
import com.miruni.feature.calendar.presentation.model.AddScheduleState
import com.miruni.feature.calendar.presentation.model.ScheduleUiModel
import java.time.LocalDate
import java.time.YearMonth

object CalendarContract {
    sealed class Event : ViewEvent {
        object YearMonthClicked : Event() // 연월 클릭
        object BeforeMonthClicked : Event() // 이전 달로 가기
        object NextMonthClicked : Event() // 다음 달로 가기

        object ChangeIsPlanCreationOpened : Event() // 일정 등록하기 바텀시트 열고 닫기
        data class DayClicked(val date: LocalDate) : Event() // 날짜 클릭
        object AiPlannerClicked : Event() // AI 플래닝 하러 가기
        data class PlanClicked(val plan: ScheduleUiModel) : Event() // 일정 클릭 -> 일정 설명 바텀시트 출력
        data class PlanChecked(val plan: ScheduleUiModel, val expectedTime: String) : Event() // 일정 완료 여부 변경
        data class SubmitPlan(val state: AddScheduleState) : Event() // 일정 작성 완료

        /** 일정 설명 바텀 시트 */
        object ChangeIsPlanSheetOpened : Event() // 일정 설명 바텀시트 열고 닫기
        object PlanMenuClicked : Event() // 메뉴 클릭
        data class ShowDetailClicked(val plan: ScheduleUiModel) : Event() // 일정 전체보기 클릭
        data class PlanEditClicked(val plan: ScheduleUiModel) : Event() // 수정 클릭
        data class PlanDeleteClicked(val plan: ScheduleUiModel) : Event() // 삭제 클릭
        data class PlanDeleteConfirmClicked(val plan: ScheduleUiModel) : Event() // 삭제 확인 클릭
        object PlanDeleteCancelClicked : Event() // 삭제 취소 클릭
    }

    @SuppressLint("NewApi")
    data class State(
        val currentMonth: YearMonth = YearMonth.now(),
        val selectedDate: LocalDate = LocalDate.now(),
        val today: LocalDate = LocalDate.now(),
        val isLoading: Boolean = false,

        val unfinishedDailyPlans: List<ScheduleUiModel> = emptyList(),
        val finishedDailyPlans: List<ScheduleUiModel> = emptyList(),

        val selectedPlan: ScheduleUiModel? = null,
        val isPlanSheetOpened: Boolean = false, // 일정 설명 바텀시트 열렸는지
        val isAddScheduleSheetOpened: Boolean = false
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        object NavigateToAiPlanner : Effect() // AI 플래닝으로 이동
        data class NavigateToScheduleTable(val planId: Int) : Effect() // 스케줄표로 이동
        data class ShowToast(val message: String) : Effect() // 토스트 메세지 출력
    }
}
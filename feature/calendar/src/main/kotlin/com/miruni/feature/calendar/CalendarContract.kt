package com.miruni.feature.calendar

import android.annotation.SuppressLint
import com.miruni.core.common.ViewEvent
import com.miruni.core.common.ViewSideEffect
import com.miruni.core.common.ViewState
import java.time.LocalDate
import java.time.YearMonth

object CalendarContract {
    sealed class Event : ViewEvent {
        object YearMonthClicked : Event() // 연월 클릭
        object BeforeMonthClicked : Event() // 이전 달로 가기
        object NextMonthClicked : Event() // 다음 달로 가기
        object OpenPlanCreation : Event() // 일정 등록하기 바텀시트 열기
        object DayClicked : Event() // 날짜 클릭
        object AiPlannerClicked : Event() // AI 플래닝 하러 가기
        data class PlanClicked(val planId: Int) : Event() // 일정 클릭 -> 일정 설명 바텀시트 출력
        object SubmitPlan : Event() // 일정 작성 완료

        /** 일정 설명 바텀 시트 */
        object PlanMenuClicked : Event() // 메뉴 클릭
        data class ShowDetailClicked(val planId: Int) : Event() // 일정 전체보기 클릭
        object PlanEditClicked : Event() // 수정 클릭
        object PlanDeleteClicked : Event() // 삭제 클릭
        object PlanDeleteConfirmClicked : Event() // 삭제 확인 클릭
        object PlanDeleteCancelClicked : Event() // 삭제 취소 클릭
    }

    @SuppressLint("NewApi")
    data class State(
        val currentMonth: YearMonth = YearMonth.now(),
        val selectedDate: LocalDate = LocalDate.now(),
        val today: LocalDate = LocalDate.now()
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        object NavigateToAiPlanner : Effect() // AI 플래닝으로 이동
        data class NavigateToScheduleTable(val planId: Int) : Effect() // 스케줄표로 이동
        data class ShowToast(val message: String) : Effect() // 토스트 메세지 출력
    }
}
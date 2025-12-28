package com.miruni.feature.home

import com.miruni.feature.home.common.ViewEvent
import com.miruni.feature.home.common.ViewSideEffect
import com.miruni.feature.home.common.ViewState
import com.miruni.feature.home.domain.TodaySchedule

class HomeContract {
    sealed class Event : ViewEvent {
        object OnAlarmClick : Event() // 알람 기록 클릭
        object OnAiPlannerClick : Event() // AI 플래너 바로가기 클릭
        object OnDndClick : Event() // 방해금지 모드 바로가기 클릭
        data class OnScheduleClick(val scheduleId: Long) : Event() // 일정 클릭 이벤트
    }

    data class State(
        val schedules: List<TodaySchedule>, // 오늘의 일정 리스트
        val selectedScheduleId: Long? // 선택한 일정 ID
    ) : ViewState

    sealed class Effect: ViewSideEffect {
        /** Navigation */
        sealed class Navigation : Effect() {
            object ToAlarms : Navigation()
            object ToAiPlanner : Navigation()
            object ToDnd : Navigation()
            data class ToExecution(val scheduleId: Long) : Navigation()
        }
    }
}
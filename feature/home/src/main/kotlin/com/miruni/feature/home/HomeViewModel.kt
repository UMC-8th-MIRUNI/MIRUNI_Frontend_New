package com.miruni.feature.home

import com.miruni.feature.home.common.BaseViewModel
import com.miruni.feature.home.domain.TodaySchedule
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : BaseViewModel<HomeContract.Event, HomeContract.State, HomeContract.Effect>() {

    /** State 초기화 */
    override fun setInitialState(): HomeContract.State = HomeContract.State(
        schedules = createDummySchedules(),
        selectedScheduleId = null
    )

    /** 이벤트 핸들링 */
    override fun handleEvents(event: HomeContract.Event) {
        when (event) {
            HomeContract.Event.OnAlarmClick -> setEffect { HomeContract.Effect.Navigation.ToAlarms }
            HomeContract.Event.OnAiPlannerClick -> setEffect { HomeContract.Effect.Navigation.ToAiPlanner }
            HomeContract.Event.OnDndClick -> setEffect { HomeContract.Effect.Navigation.ToDnd }
            is HomeContract.Event.OnScheduleClick -> handleScheduleClick(event.scheduleId)
        }
    }

    /**
     * 일정 클릭 처리
     */
    private fun handleScheduleClick(scheduleId: Long) {
        val currentSelected = viewState.value.selectedScheduleId

        if (currentSelected == scheduleId) {
            setEffect {
                HomeContract.Effect.Navigation.ToExecution(scheduleId)
            }
        } else {
            setState {
                copy(selectedScheduleId = scheduleId)
            }
        }
    }

    /**
     * 더미 데이터
     */
    private fun createDummySchedules(): List<TodaySchedule> =
        listOf(
            TodaySchedule(1,"오후 2:20", "UMC 기획안 만들기", "상", "워크북 3페이지 슬라이드 제작"),
            TodaySchedule(2,"오후 3:20", "CMC 기획안 만들기", "중", "워크북 2페이지 슬라이드 제작"),
            TodaySchedule(3,"오후 4:20", "DMC 기획안 만들기", "하", "워크북 1페이지 슬라이드 제작"),
            TodaySchedule(4,"오후 5:20", "EMC 기획안 만들기", "상", "워크북 45페이지 슬라이드 제작"),
        )
}
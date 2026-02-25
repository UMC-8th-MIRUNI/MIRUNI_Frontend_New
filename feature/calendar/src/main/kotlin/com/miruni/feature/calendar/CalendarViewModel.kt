package com.miruni.feature.calendar

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.miruni.core.common.BaseViewModel
import com.miruni.core.result.DataError
import com.miruni.core.result.DataResult
import com.miruni.feature.calendar.domain.model.DailyPlans
import com.miruni.feature.calendar.domain.repository.CalendarRepository
import com.miruni.feature.calendar.presentation.model.AddScheduleState
import com.miruni.feature.calendar.presentation.model.ScheduleUiModel
import com.miruni.feature.calendar.presentation.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val calendarRepository: CalendarRepository
) : BaseViewModel<CalendarContract.Event, CalendarContract.State, CalendarContract.Effect>() {

    override fun setInitialState() = CalendarContract.State()

    private val _selectedDateFlow = MutableStateFlow(setInitialState().selectedDate)
    val selectedDateFlow: StateFlow<LocalDate> = _selectedDateFlow.asStateFlow()

    override fun handleEvents(event: CalendarContract.Event) {
        when (event) {
            // AI 플래닝하러 가기
            CalendarContract.Event.AiPlannerClicked -> setEffect { CalendarContract.Effect.NavigateToAiPlanner }

            // 날짜 변경 관련
            CalendarContract.Event.YearMonthClicked -> printYearMonthPicker()
            CalendarContract.Event.BeforeMonthClicked -> printBeforeMonth()
            CalendarContract.Event.NextMonthClicked -> printNextMonth()
            // 날짜 타일 클릭
            is CalendarContract.Event.DayClicked -> setSelectedDay(event.date)

            // 일정 생성
            CalendarContract.Event.ChangeIsPlanCreationOpened -> changeIsPlanCreationOpened()
            is CalendarContract.Event.SubmitPlan -> createPlan(event.state)

            // 일정 클릭
            is CalendarContract.Event.PlanClicked -> selectPlan(event.plan)
            // 일정 완료 설정
            is CalendarContract.Event.PlanChecked -> finishPlan(event.plan, event.expectedTime)
            // 일정 메뉴
            CalendarContract.Event.PlanMenuClicked -> printMenu()
            // - 전체 보기
            is CalendarContract.Event.ShowDetailClicked -> setEffect { CalendarContract.Effect.NavigateToScheduleTable(event.plan.id.toInt()) }
            // - 수정하기
            is CalendarContract.Event.PlanEditClicked -> printPlanCreationBottomSheet(event.plan)
            // - 삭제하기
            is CalendarContract.Event.PlanDeleteClicked -> printDeleteConfirmationDialog()
            is CalendarContract.Event.PlanDeleteConfirmClicked -> confirmDelete(event.plan)
            CalendarContract.Event.PlanDeleteCancelClicked -> cancelDelete()
        }
    }

    init {
        observeDailyPlans()
        refreshDailyPlans()
    }

    fun onMonthChanged(yearMonth: YearMonth) {
        viewModelScope.launch {
            setState { copy(currentMonth = yearMonth) }
        }
    }

    /**
     * 오늘의 일정 구독
     * - 선택 날짜 변동시 DailyPlan 반영
     */
    private fun observeDailyPlans() {
        viewModelScope.launch {
            selectedDateFlow
                .flatMapLatest { date ->
                    calendarRepository.observeDailyPlans(date)
                        .catch { e ->
                            Log.d("CalendarViewModel", "observeDailyPlans: $e")
                            emit(DailyPlans(unfinishedPlan = emptyList(), finishedPlan = emptyList()))
                        }
                }
                .collect { dailyPlans ->
                    Log.d("PLAN_DEBUG", "8️⃣ ViewModel collect됨: ${dailyPlans.unfinishedPlan.size} / ${dailyPlans.finishedPlan.size}")

                    val unfinished = dailyPlans.unfinishedPlan.mapNotNull { plan ->
                        runCatching { plan.toUiModel() }.getOrNull()
                    }
                    val finished = dailyPlans.finishedPlan.mapNotNull { plan ->
                        runCatching { plan.toUiModel() }.getOrNull()
                    }

                    setState {
                        copy(
                            unfinishedDailyPlans = unfinished,
                            finishedDailyPlans = finished,
                            isLoading = false
                        )
                    }
                }
        }
    }

    private fun refreshDailyPlans() {
        viewModelScope.launch {
            val today = viewState.value.selectedDate
            calendarRepository.refreshDailyPlans(today)
        }
    }

    private fun printBeforeMonth() {

    }
    private fun printNextMonth() {

    }
    /** 날짜 선택 처리 */
    private fun setSelectedDay(date: LocalDate) {
        setState { copy(selectedDate = date) }
        _selectedDateFlow.value = date

        viewModelScope.launch { calendarRepository.refreshDailyPlans(date) }
    }
    /** 일정 완료 처리 */
    private fun finishPlan(plan: ScheduleUiModel, expectedTime: String) {
        viewModelScope.launch {
            val result = calendarRepository.postPlanFinish(
                planType = plan.planType,
                planId = plan.id.toInt(),
                expectedTime = expectedTime
            )

            when (result) {
                is DataResult.Success -> {
                    calendarRepository.refreshDailyPlans(viewState.value.selectedDate)
                }
                is DataResult.Error -> setEffect {
                    CalendarContract.Effect.ShowToast("완료 처리 실패")
                }
            }
        }
    }
    /** 일정 생성 처리 */
    private fun createPlan(state: AddScheduleState) {
        Log.d("PLAN_DEBUG", "2️⃣ ViewModel.createPlan 시작")
        setState { copy(isAddScheduleSheetOpened = false) }

        viewModelScope.launch {
            setState { copy(isLoading = true) }

            val draft = state.toDomain()
            val result = calendarRepository.createPlan(draft)
            Log.d("PLAN_DEBUG", "3️⃣ Repository.createPlan 반환: $result")

            when (result) {
                is DataResult.Success -> {
                    setEffect { CalendarContract.Effect.ShowToast("일정이 생성되었습니다.") }
                }
                is DataResult.Error -> {
                    setEffect { CalendarContract.Effect.ShowToast("일정 생성 실패") }
                }
            }
        }
    }
    private fun changeIsPlanCreationOpened() {
        val isAddScheduleSheetOpened = viewState.value.isAddScheduleSheetOpened
        setState { copy(isAddScheduleSheetOpened = !isAddScheduleSheetOpened) }
    }
    private fun selectPlan(plan: ScheduleUiModel) {

    }
    private fun printDeleteConfirmationDialog() {

    }
    private fun cancelDelete() {

    }
    private fun confirmDelete() {

    }
    private fun printPlanCreationBottomSheet() {

    private fun printPlanCreationBottomSheet(plan: ScheduleUiModel) {
        setState { copy(isAddScheduleSheetOpened = true, selectedPlan = plan) }
    }
    private fun printMenu() {

    }
    private fun printYearMonthPicker() {

    }

    private fun showErrorMessage(error: DataError?) {
        val message = when(error) {
            is DataError.CustomError -> error.msg
            is DataError.Unknown -> error.errorMessage
            else -> "네트워크 연결을 확인해주세요."
        }

        setEffect { CalendarContract.Effect.ShowToast(message) }
    }
}
package com.miruni.feature.calendar

import androidx.lifecycle.viewModelScope
import com.miruni.core.common.BaseViewModel
import com.miruni.core.result.DataError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(

) : BaseViewModel<CalendarContract.Event, CalendarContract.State, CalendarContract.Effect>() {

    override fun setInitialState(): CalendarContract.State {
        TODO("Not yet implemented")
        // 오늘 날짜 혹은 클릭한 날짜의 미완료/완료 일정 리스트 이니셜라이징
    }

    override fun handleEvents(event: CalendarContract.Event) {
        when (event) {
            CalendarContract.Event.AiPlannerClicked -> setEffect { CalendarContract.Effect.NavigateToAiPlanner }
            CalendarContract.Event.BeforeMonthClicked -> printBeforeMonth()
            CalendarContract.Event.NextMonthClicked -> printNextMonth()
            CalendarContract.Event.DayClicked -> setSelectedDay()
            CalendarContract.Event.OpenPlanCreation -> openPlanCreation()
            is CalendarContract.Event.PlanClicked -> selectPlan(event.planId)
            CalendarContract.Event.PlanDeleteClicked -> printDeleteConfirmationDialog()
            CalendarContract.Event.PlanDeleteCancelClicked -> cancelDelete()
            CalendarContract.Event.PlanDeleteConfirmClicked -> confirmDelete()
            CalendarContract.Event.PlanEditClicked -> printPlanCreationBottomSheet()
            CalendarContract.Event.PlanMenuClicked -> printMenu()
            is CalendarContract.Event.ShowDetailClicked -> setEffect { CalendarContract.Effect.NavigateToScheduleTable(event.planId) }
            CalendarContract.Event.SubmitPlan -> submitPlan()
            CalendarContract.Event.YearMonthClicked -> printYearMonthPicker()
        }
    }

    fun onMonthChanged(yearMonth: YearMonth) {
        viewModelScope.launch {
            setState { copy(currentMonth = yearMonth) }
        }
    }

    private fun printBeforeMonth() {

    }
    private fun printNextMonth() {

    }
    private fun setSelectedDay() {

    }
    private fun openPlanCreation() {

    }
    private fun selectPlan(planId: Int) {

    }
    private fun printDeleteConfirmationDialog() {

    }
    private fun cancelDelete() {

    }
    private fun confirmDelete() {

    }
    private fun printPlanCreationBottomSheet() {

    }
    private fun printMenu() {

    }
    private fun submitPlan() {

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
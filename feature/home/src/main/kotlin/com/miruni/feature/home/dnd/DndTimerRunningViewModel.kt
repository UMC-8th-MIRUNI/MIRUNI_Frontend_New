package com.miruni.feature.home.dnd

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miruni.feature.home.dnd.model.DndTimerRunningEvent
import com.miruni.feature.home.dnd.model.DndTimerRunningSideEffect
import com.miruni.feature.home.dnd.model.DndTimerRunningState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DndTimerRunningViewModel(
    initialHour: Int,
    initialMinute: Int
) : ViewModel() {

    private val events = Channel<DndTimerRunningEvent>(Channel.BUFFERED)
    private val sideEffects = Channel<DndTimerRunningSideEffect>(Channel.BUFFERED)

    val sideEffect = sideEffects.receiveAsFlow()

    val state: StateFlow<DndTimerRunningState> =
        events
            .receiveAsFlow()
            .runningFold(
                DndTimerRunningState(
                    hour = initialHour,
                    minute = initialMinute
                ),
                ::reduceState
            )
            .stateIn(
                viewModelScope,
                SharingStarted.Eagerly,
                DndTimerRunningState(initialHour, initialMinute)
            )

    init {
        startTimer()
    }

    fun onEvent(event: DndTimerRunningEvent) {
        viewModelScope.launch {
            events.send(event)
        }
    }

    private fun startTimer() {
        viewModelScope.launch {
            while (true) {
                delay(60_000L)
                events.send(DndTimerRunningEvent.Tick)
            }
        }
    }

    private fun emitSideEffect(effect: DndTimerRunningSideEffect) {
        viewModelScope.launch {
            sideEffects.send(effect)
        }
    }

    private fun reduceState(
        current: DndTimerRunningState,
        event: DndTimerRunningEvent
    ): DndTimerRunningState {
        return when (event) {

            DndTimerRunningEvent.Tick -> {
                // ✅ 이미 00:00이면 아무것도 안 함 (중복 방지)
                if (current.hour == 0 && current.minute == 0) {
                    current
                }
                // ✅ 지금 Tick으로 00:00이 되는 순간
                else if (current.hour == 0 && current.minute == 1) {
                    emitSideEffect(DndTimerRunningSideEffect.NavigateToComplete)
                    current.copy(hour = 0, minute = 0)
                }
                // 일반 감소
                else if (current.minute > 0) {
                    current.copy(minute = current.minute - 1)
                } else {
                    current.copy(
                        hour = current.hour - 1,
                        minute = 59
                    )
                }
            }

            DndTimerRunningEvent.StopClicked -> {
                emitSideEffect(DndTimerRunningSideEffect.NavigateToPause)
                current
            }

            DndTimerRunningEvent.CompleteClicked -> {
                emitSideEffect(
                    DndTimerRunningSideEffect.NavigateToEarlyEnd(
                        hour = current.hour,
                        minute = current.minute
                    )
                )
                current
            }
        }
    }
}


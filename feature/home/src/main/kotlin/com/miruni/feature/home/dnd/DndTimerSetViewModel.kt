package com.miruni.feature.home.dnd

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miruni.feature.home.dnd.model.DndTimerSetEvent
import com.miruni.feature.home.dnd.model.DndTimerSetSideEffect
import com.miruni.feature.home.dnd.model.DndTimerSetState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DndTimerSetViewModel : ViewModel() {

    private val events = Channel<DndTimerSetEvent>(Channel.BUFFERED)
    private val _sideEffect = Channel<DndTimerSetSideEffect>(Channel.BUFFERED)

    val sideEffect = _sideEffect.receiveAsFlow()

    val state: StateFlow<DndTimerSetState> =
        events
            .receiveAsFlow()
            .runningFold(DndTimerSetState(), ::reduceState)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = DndTimerSetState()
            )

    fun onEvent(event: DndTimerSetEvent) {
        viewModelScope.launch {
            events.send(event)
        }
    }

    private fun reduceState(
        current: DndTimerSetState,
        event: DndTimerSetEvent
    ): DndTimerSetState {
        return when (event) {

            is DndTimerSetEvent.TimeChanged -> {
                current.copy(
                    hour = event.hour,
                    minute = event.minute
                )
            }

            DndTimerSetEvent.ConfirmClicked -> {
                emitSideEffect(
                    DndTimerSetSideEffect.NavigateToRunning(
                        hour = current.hour,
                        minute = current.minute
                    )
                )
                current.copy(isTimeConfirmed = true)
            }

            DndTimerSetEvent.CloseClicked -> {
                emitSideEffect(DndTimerSetSideEffect.NavigateToHome)
                current
            }
        }
    }

    private fun emitSideEffect(effect: DndTimerSetSideEffect) {
        viewModelScope.launch {
            _sideEffect.send(effect)
        }
    }
}
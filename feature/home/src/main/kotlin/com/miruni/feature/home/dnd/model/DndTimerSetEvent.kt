package com.miruni.feature.home.dnd.model

sealed interface DndTimerSetEvent {
    data class TimeChanged(val hour: Int, val minute: Int) : DndTimerSetEvent
    object ConfirmClicked : DndTimerSetEvent
    object CloseClicked : DndTimerSetEvent
}
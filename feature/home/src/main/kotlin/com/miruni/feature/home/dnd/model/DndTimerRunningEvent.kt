package com.miruni.feature.home.dnd.model

sealed interface DndTimerRunningEvent {
    object Tick : DndTimerRunningEvent
    object StopClicked : DndTimerRunningEvent
    object CompleteClicked : DndTimerRunningEvent
}
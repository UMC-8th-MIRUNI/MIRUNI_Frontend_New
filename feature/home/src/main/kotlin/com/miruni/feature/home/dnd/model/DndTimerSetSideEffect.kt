package com.miruni.feature.home.dnd.model

interface DndTimerSetSideEffect {
    data class NavigateToRunning(val hour: Int, val minute: Int) : DndTimerSetSideEffect
    object NavigateToHome : DndTimerSetSideEffect
}
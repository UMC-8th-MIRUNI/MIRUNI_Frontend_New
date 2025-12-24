package com.miruni.feature.home.dnd.model

sealed interface DndTimerRunningSideEffect {
    object NavigateToComplete : DndTimerRunningSideEffect
    object NavigateToPause : DndTimerRunningSideEffect
    data class NavigateToEarlyEnd(val hour: Int, val minute: Int) : DndTimerRunningSideEffect
}
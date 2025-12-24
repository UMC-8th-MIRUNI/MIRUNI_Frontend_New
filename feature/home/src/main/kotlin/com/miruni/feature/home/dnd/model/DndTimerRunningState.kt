package com.miruni.feature.home.dnd.model

data class DndTimerRunningState(
    val hour: Int,
    val minute: Int,
    val isFinished: Boolean = false
)
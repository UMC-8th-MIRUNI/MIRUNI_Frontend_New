package com.miruni.feature.home.dnd.model

data class DndTimerSetState(
    val hour: Int = 0,
    val minute: Int = 0,
    val isTimeConfirmed: Boolean = false
)

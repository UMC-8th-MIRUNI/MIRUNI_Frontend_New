package com.miruni.feature.home.dnd.model

// 사용자가 View에서 할 수 있는 모든 행동을 정의
sealed interface DndTimerSetEvent {
    // 시간 설정 의도
    data class SetTime(val hour: Int, val minute: Int) : DndTimerSetEvent

    // 타이머 시작 의도
    object Start : DndTimerSetEvent

    // 타이머 중지 의도
    object Pause : DndTimerSetEvent
}
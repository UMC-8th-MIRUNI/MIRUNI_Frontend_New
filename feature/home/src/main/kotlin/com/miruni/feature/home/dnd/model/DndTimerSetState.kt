package com.miruni.feature.home.dnd.model

// 타이머 화면을 그리기 위한 모든 상태를 담는 State
data class DndTimerSetState(
    val remainingMinute: Int = 0, // 남아있는 분
    val isRunning: Boolean = false, // 타이머가 현재 실행 중인지 여부
    val isDone: Boolean = true // 타이머가 끝났는지 확인
) {
    // 파생 상태 (Derived State)
    // State를 직접 바꾸지 않고 계산으로만 사용
    val hours: Int get() = remainingMinute / 60
    val minutes: Int get() = remainingMinute % 60
}
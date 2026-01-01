package com.miruni.feature.home.dnd.model

/**
 * Dnd 타이머 실행 화면에서 ViewModel로 전달되는 입력 이벤트.
 *
 * - 사용자 액션 (중지, 완료 버튼 클릭)
 * - 시스템 이벤트 (1분마다 발생하는 Tick)
 * - 화면 진입 시 초기화 이벤트 (Init)
 *
 * 모든 상태 변화의 원인은 이 Event를 통해서만 발생한다.
 */
sealed interface DndTimerRunningEvent {
    /** 화면 진입 시 초기 시간 설정 */
//    data class Init(val hour: Int, val minute: Int) : DndTimerRunningEvent

    /** 1분마다 발생하는 타이머 Tick */
    data object Tick : DndTimerRunningEvent

    /** 사용자가 타이머 중지 버튼을 클릭 */
    data object StopClicked : DndTimerRunningEvent

    /** 사용자가 타이머 조기 종료 버튼을 클릭 */
    data object CompleteClicked : DndTimerRunningEvent
}
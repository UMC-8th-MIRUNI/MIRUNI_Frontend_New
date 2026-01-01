package com.miruni.feature.home.dnd.model

/**
* Dnd 타이머 실행 화면에서 발생하는 단발성 UI 효과.
*
* State로 표현하면 안 되는 행동들을 정의한다.
* (네비게이션, 토스트, 다이얼로그 등)
*
* 모든 SideEffect는 UI 레이어에서 1회만 소비된다.
*/

sealed interface DndTimerRunningSideEffect {

    /** 타이머 중지 시 일시정지 화면으로 이동 */
    data class NavigateToPause(
        val hour: Int,
        val minute: Int
    ) : DndTimerRunningSideEffect

    /** 타이머 정상 완료 시 완료 화면으로 이동 */
    data class NavigateToComplete(
        val hour: Int,
        val minute: Int
    ) : DndTimerRunningSideEffect

    /** 사용자가 조기 종료했을 때 완료 화면으로 이동 */
    data class NavigateToEarlyEnd(
        val hour: Int,
        val minute: Int
    ) : DndTimerRunningSideEffect
}
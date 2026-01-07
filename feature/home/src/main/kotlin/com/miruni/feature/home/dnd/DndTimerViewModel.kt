package com.miruni.feature.home.dnd

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.miruni.feature.home.common.BaseViewModel
import com.miruni.feature.home.common.ViewEvent
import com.miruni.feature.home.common.ViewSideEffect
import com.miruni.feature.home.common.ViewState
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable.isActive
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class TimerMode {
    SET,      // 시간 설정 화면
    RUNNING,  // 타이머 실행 화면
    PAUSED    // 일시정지 화면
}

class DndContract {

    sealed class Event : ViewEvent {
        data class SetTime(val hour: Int, val minute: Int) : Event()
        object Start : Event()
        object Pause : Event()
        object Resume : Event()
    }

    data class State(
        val remainingMinute: Int = 0, // 남아있는 분
        val isRunning: Boolean = false, // 타이머가 현재 실행 중인지 여부
        val isDone: Boolean = true, // 타이머가 끝났는지 확인
        val mode: TimerMode = TimerMode.SET
    ) : ViewState {
        // 파생 상태 (Derived State)
        // State를 직접 바꾸지 않고 계산으로만 사용
        val hours: Int get() = remainingMinute / 60
        val minutes: Int get() = remainingMinute % 60
    }

    sealed class Effect : ViewSideEffect {
        object TimeFinished : Effect()
    }
}

class DndTimerViewModel :
    BaseViewModel<DndContract.Event, DndContract.State, DndContract.Effect>() {

    // 타이머 코루틴 Job
    private var timerJob: Job? = null

    override fun setInitialState() = DndContract.State()

    override fun handleEvents(event: DndContract.Event) {
        when (event) {
            is DndContract.Event.SetTime -> setTime(event.hour, event.minute)
            DndContract.Event.Start -> start()
            DndContract.Event.Pause -> pause()
            DndContract.Event.Resume -> resume()
        }
    }

    // 사용자가 시간을 세팅
    private fun setTime(hour: Int, minute: Int) {
        val total = hour * 60 + minute
        val totalMinute = (hour * 60 + minute).coerceAtLeast(0)
        Log.d("DndTimerViewModel", "setTime: $total")

        setState {
            copy(
                remainingMinute = total,
                isRunning = false,
                isDone = false,
                mode = TimerMode.SET
            )
        }

        Log.d("DndTimerViewModel", "start 함수 호출")

        start()
    }

    // 타이머 실행
    private fun start() {
        val current = viewState.value

        Log.d(
            "DndTimer",
            "▶ start() called, remaining=${current.remainingMinute}"
        )

        // 이미 실행 중이거나 시간이 없으면 무시
        if (current.isRunning || current.remainingMinute <= 0) return

        // 실행상태로 변경
        setState {
            copy(
                isRunning = true,
                isDone = false,
                mode = TimerMode.RUNNING
            )
        }

        startTimer()
    }

    private fun startTimer() {
        timerJob?.cancel()

        timerJob = viewModelScope.launch {
            Log.d("DndTimerViewModel", "start timer")

            // 타이머가 살아 있고, 시간이 남아 있는 동안 반복
            while (isActive && viewState.value.remainingMinute > 0) {
                delay(1_000) // test
//                delay(60_000) // realtime

                val before = viewState.value.remainingMinute

                // 1분 감소
                setState {
                    copy(remainingMinute = remainingMinute - 1)
                }

                val after = viewState.value.remainingMinute

                Log.d("DndTimerViewModel", "1분 감소 : $before -> $after")
            }

            // 시간이 끝나면 실행 상태 해제
            setEffect { DndContract.Effect.TimeFinished }
        }
    }

    // 일시정지
    private fun pause() {
        timerJob?.cancel() // 코루틴 중단

        // 실행 상태 해제
        setState {
            copy(
                isRunning = false,
                mode = TimerMode.PAUSED
            )
        }
    }

    private fun resume() {
        if (viewState.value.remainingMinute <= 0) return
        start()
    }
}
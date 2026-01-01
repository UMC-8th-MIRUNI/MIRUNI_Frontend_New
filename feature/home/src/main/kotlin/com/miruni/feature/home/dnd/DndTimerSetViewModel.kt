package com.miruni.feature.home.dnd

import android.R.attr.mode
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miruni.feature.home.dnd.model.DndTimerSetEvent
import com.miruni.feature.home.dnd.model.DndTimerSetState
import com.miruni.feature.home.dnd.model.TimerMode
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable.isActive
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DndTimerSetViewModel : ViewModel() {

    // 내부에서만 변경 가능한 State
    private val _state = MutableStateFlow(DndTimerSetState())

    // View에 노출되는 읽기 전용 State
    val state: StateFlow<DndTimerSetState> = _state

    // 타이머 코루틴 Job
    private var timerJob: Job? = null



    // View 에서 들어온 Intent 를 처리하는 단일 진입점
    fun processEvent(event: DndTimerSetEvent) {
        when (event) {
            is DndTimerSetEvent.SetTime -> setTime(event.hour, event.minute)

            DndTimerSetEvent.Start -> start()
            DndTimerSetEvent.Pause -> pause()
        }
    }

    // 사용자가 시간을 세팅
    private fun setTime(
        hour: Int,
        minute: Int,
    ) {

        // 시 + 분을 분 단위로 변환
        val total = (hour * 60 + minute).coerceAtLeast(0)

        // 실행 상태로 변경
        _state.update {
            it.copy(
                remainingMinute = total,
                isDone = false,
                isRunning = false,
                mode = TimerMode.SET
            )
        }

        start()
    }

    // 타이머 실행
    private fun start() {
        val current = _state.value
        // 이미 실행 중이거나 시간이 없으면 무시
        if (current.isRunning || current.remainingMinute <= 0) return

        // 실행 상태로 변경
        _state.update {
            it.copy(
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
            // 타이머가 살아 있고, 시간이 남아 있는 동안 반복
            while (isActive && _state.value.remainingMinute > 0) {
                delay(60_000)

                // 1분 감소
                _state.update {
                    it.copy(remainingMinute = it.remainingMinute - 1)
                }
                Log.d("DndTimerSetViewModel", "1분 감소")
            }

            // 시간이 끝나면 실행 상태 해제
            _state.update {
                it.copy(isRunning = false, isDone = true)
            }
        }
    }

    private fun pause() {
        timerJob?.cancel() // 코루틴 중단
//        timerJob = null

        // 실행 상태 해제
        _state.update {
            it.copy(
                isRunning = false,
                mode = TimerMode.PAUSED
            )
        }
    }
}
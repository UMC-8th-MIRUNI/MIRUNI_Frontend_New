package com.miruni.feature.home.runSchedule

import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.miruni.core.designsystem.MiruniTheme
import com.miruni.core.navigation.MiruniRoute
import com.miruni.feature.home.dnd.DndContract
import com.miruni.feature.home.dnd.DndTimerViewModel
import com.miruni.feature.home.dnd.component.screen.TimerScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RunScheduleTimerScreen(
    navController: NavHostController,
    viewModel: DndTimerViewModel = viewModel()
) {
    // viewmodel 이 노출한 단일 UI state
    val state by viewModel.viewState.collectAsState()

    Log.d(
        "RunScheduleTimerUI",
        "Recompose | remaining=${state.remainingMinute} " +
                "(${state.hours}:${"%02d".format(state.minutes)})"
    )

    // UI 전용 상태 (TimePickerState 내부 상태)
    val timePickerState = rememberTimePickerState(
        is24Hour = true,
        initialHour = state.hours,
        initialMinute = state.minutes
    )

    Log.d(
        "RunScheduleTimer", "Composable Recomposition."
    )

    // 일회성 sideEffect 수신 (Navigation)
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                DndContract.Effect.TimeFinished -> {
                    navController.navigate(
                        MiruniRoute.HomeDndComplete.createRoute(
                            hour = timePickerState.hour,
                            minute = timePickerState.minute
                        )
                    ) {
                        // 타이머 화면 스택에서 제거
                        popUpTo(MiruniRoute.HomeDndTimerSetting.route) {
                            inclusive = true
                        }
                    }
                }

                DndContract.Effect.NavigateToPause -> {
                    navController.navigate(
                        MiruniRoute.HomeDndPause.createRoute(
                            hour = state.hours,
                            minute = state.minutes
                        )
                    )
                }

                DndContract.Effect.NavigateToEarlyEnd -> {
                    navController.navigate(
                        MiruniRoute.HomeDndEarlyEnd.createRoute(
                            hour = state.hours,
                            minute = state.minutes
                        )
                    )
                }

                DndContract.Effect.NavigateToHome -> {
                    navController.navigate(
                        MiruniRoute.Home.route
                    )
                }
            }
        }
    }

    TimerScreen(
        state = state,
        timePickerState = timePickerState,
        onSetTime = { hour, minute ->
            viewModel.setEvent(
                DndContract.Event.SetTime(
                    hour = hour,
                    minute = minute
                )
            )
        },
        onPauseClick = {
            viewModel.setEvent(
                DndContract.Event.Pause
            )
        },
        onCompleteClick = {
            viewModel.setEvent(
                DndContract.Event.End
            )
        },
        onStartClick = {
            viewModel.setEvent(
                DndContract.Event.Start
            )
        },
        navController = navController,
        showSettingTime = true,
        showTopBar = false
    )
}

@Preview(showBackground = true)
@Composable
fun RunScheduleTimerScreenPreview() {
    MiruniTheme {
        RunScheduleTimerScreen(
            navController = rememberNavController(),
        )
    }
}

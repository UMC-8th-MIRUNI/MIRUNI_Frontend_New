package com.miruni.feature.home.dnd

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.miruni.core.designsystem.AppTypography
import com.miruni.core.designsystem.MiruniTheme
import com.miruni.core.navigation.MiruniRoute
import com.miruni.feature.home.R
import com.miruni.feature.home.dnd.component.DndTopBar
import com.miruni.feature.home.dnd.component.InputTimeView
import com.miruni.feature.home.dnd.model.DndTimerSetEvent
import com.miruni.feature.home.dnd.model.DndTimerSetSideEffect
import com.miruni.feature.home.dnd.model.DndTimerSetState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DndTimerSetScreen(
    navController: NavHostController,
    viewModel: DndTimerSetViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    val timePickerState = rememberTimePickerState(
        is24Hour = true,
        initialHour = state.hour,
        initialMinute = state.minute
    )

    Log.d(
        "DndTimerSet", "Composable Recomposition."
    )

    // SideEffect 처리 (Navigation)
    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is DndTimerSetSideEffect.NavigateToRunning -> {
                    navController.navigate(
                        MiruniRoute.HomeDndTimerRunning.createRoute(
                            hour = effect.hour,
                            minute = effect.minute
                        )
                    )
                }

                DndTimerSetSideEffect.NavigateToHome -> {
                    navController.navigate(MiruniRoute.Home.route) {
                        popUpTo(MiruniRoute.Home.route) { inclusive = false }
                        launchSingleTop = true
                    }
                }
            }
        }
    }

    // TimePicker → Event 변환
    LaunchedEffect(timePickerState.hour, timePickerState.minute) {
        viewModel.onEvent(
            DndTimerSetEvent.TimeChanged(
                hour = timePickerState.hour,
                minute = timePickerState.minute
            )
        )
    }

    DndTimerSetContent(
        state = state,
        timePickerState = timePickerState,
        onCloseClick = {
            viewModel.onEvent(DndTimerSetEvent.CloseClicked)
        },
        onConfirmClick = {
            viewModel.onEvent(DndTimerSetEvent.ConfirmClicked)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DndTimerSetContent(
    state: DndTimerSetState,
    timePickerState: TimePickerState,
    onCloseClick: () -> Unit,
    onConfirmClick: () -> Unit,
) {
    Log.d("DndTimerSet", "Composable Recomposition.")

    Scaffold(
        topBar = {
            DndTopBar(onClose = onCloseClick)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 20.dp)
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Spacer(Modifier.height(20.dp))

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color(0x1A24C354)
                ),
                modifier = Modifier.height(65.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("다른 어플 사용을 제한")
                            }
                            append("하고, 할 일에 더 집중해보세요.")
                        },
                        textAlign = TextAlign.Center,
                        style = AppTypography.body_regular_14
                    )
                }
            }

            Spacer(Modifier.height(60.dp))

            Image(
                painter = painterResource(id = R.drawable.miruni_basic),
                contentDescription = null,
                modifier = Modifier.size(126.dp)
            )

            Spacer(Modifier.height(100.dp))

            InputTimeView(
                timePickerState = timePickerState,
                isTimeConfirmed = state.isTimeConfirmed
            )

            Spacer(Modifier.height(50.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(49.dp),
                shape = RoundedCornerShape(10.dp),
                onClick = onConfirmClick
            ) {
                Text("확인")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DndTimerSetScreenPreview() {
    MiruniTheme {
        DndTimerSetScreen(
            navController = rememberNavController()
        )
    }
}
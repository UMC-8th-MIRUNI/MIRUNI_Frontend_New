package com.miruni.feature.aiplanner.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.miruni.core.designsystem.AppTypography
import com.miruni.core.designsystem.Black
import com.miruni.core.designsystem.MainColor
import com.miruni.core.navigation.MiruniRoute
import com.miruni.feature.aiplanner.R
import com.miruni.feature.aiplanner.presentation.AiPlannerContract
import com.miruni.feature.aiplanner.presentation.AiPlannerViewModel

@Composable
fun AiPlannerLoadingScreen(
    navController: NavHostController,
    viewModel: AiPlannerViewModel = hiltViewModel()
) {
    val state by viewModel.viewState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.setEvent(AiPlannerContract.Event.ShowPlanningLoading) // 로딩 화면 출력

        // 화면 네비게이트
        viewModel.effect.collect { effect ->
            when(effect) {
                AiPlannerContract.Effect.Navigation.ToSchedule -> {
                    navController.navigate("${MiruniRoute.AiPlannerSchedule.route}?from=LOADING&planId=${state.plan?.planId}")
                }
                AiPlannerContract.Effect.PopBack -> {
                    navController.popBackStack()
                }
                else -> Unit
            }
        }
    }

    AiPlannerLoadingContent(
        isFinished = state.isFinishedPlanningLoading,
        userName = state.userName ?: "",
        onBack = { viewModel.setEvent(AiPlannerContract.Event.ClickBack) },
        onConfirm = { viewModel.setEvent(AiPlannerContract.Event.ClickConfirm) }
    )
}

// 로딩 화면 UI 구성
@Composable
fun AiPlannerLoadingContent(
    isFinished: Boolean,
    userName: String,
    onBack: () -> Unit,
    onConfirm: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
            .background(color = Color.White)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            /** 뒤로 가기 */
            Image(
                painter = painterResource(R.drawable.left_arrow),
                contentDescription = "뒤로가기",
                modifier = Modifier
                    .size(17.dp)
                    .clickable { onBack() }
            )
        }

        Spacer(modifier = Modifier.height(129.dp))

        /** 미루니 이미지 */
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.miruni_pencil),
                contentDescription = null,
                modifier = Modifier.size(
                    width = 222.dp,
                    height = 178.dp
                )
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 137.dp, end = 86.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.miruni_shadow),
                contentDescription = null,
                modifier = Modifier.size(
                    width = 107.dp,
                    height = 23.dp
                )
            )
        }

        Spacer(modifier = Modifier.height(66.dp))

        /** 안내 텍스트 */
        GuideTextRow(
            isFinished = isFinished,
            userName = userName
        )

        Spacer(modifier = Modifier.height(124.dp))

        /** 확인 버튼 */
        if (isFinished) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MainColor.miruni_green,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(vertical = 5.dp)
                    .clickable { onConfirm() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "확인",
                    style = AppTypography.button_semibold_16,
                    color = Color(0xFFF9F9F9),
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    }
}

@Composable
fun GuideTextRow(
    isFinished: Boolean,
    userName: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text =
                if (!isFinished) "일정을 짜는 중이에요.."
                else "일정짜기가 완료되었어요!",
            style = AppTypography.header_bold_20,
            color = Black
        )
    }

    Spacer(modifier = Modifier.height(22.dp))

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text =
                if (!isFinished) "AI 플래너 기능을 활용하여 ${userName}님이 지치지 않도록,\n딱 맞는 일정을 짜드릴게요!"
                else "미루니가 짜준 스케줄을\n확인해보세요.",
            style = AppTypography.PretendardTextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                letterSpacing = (-0.05f).em,
                lineHeightRatio = 1.67f
            ),
            color = Color(0xFFBBBBBB),
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun PreviewAiPlannerLoadingScreen() {
    AiPlannerLoadingScreen(navController = rememberNavController())
}
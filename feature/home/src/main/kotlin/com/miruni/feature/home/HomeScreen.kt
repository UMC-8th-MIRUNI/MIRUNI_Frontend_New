package com.miruni.feature.home

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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.miruni.core.designsystem.AppTypography
import com.miruni.core.designsystem.Gray
import com.miruni.core.designsystem.MainColor
import com.miruni.core.designsystem.MiruniTheme
import com.miruni.core.navigation.MiruniRoute
import com.miruni.feature.home.component.TodayScheduleItem
import com.miruni.feature.home.common.convertBold
import com.miruni.feature.home.component.LinearProgressBar

/** 더미 데이터 */
val achievementRate: Float = 0.12f // 임시
val peanut: Int = 0

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state = viewModel.viewState.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                HomeContract.Effect.Navigation.ToAiPlannerOnboarding -> navController.navigate(MiruniRoute.AiPlannerOnboarding.route) // AI 플래너 온보딩
                HomeContract.Effect.Navigation.ToAiPlanner -> navController.navigate(MiruniRoute.AiPlanner.route) // AI 플래너
                HomeContract.Effect.Navigation.ToAlarms -> navController.navigate(MiruniRoute.AlarmLogs.route) // 알람 기록
                HomeContract.Effect.Navigation.ToDnd -> navController.navigate(MiruniRoute.Dnd.route) // 방해금지 모드
                is HomeContract.Effect.Navigation.ToExecution -> navController.navigate(MiruniRoute.Execution.route) // 일정 실행
            }
        }
    }

    Scaffold(
        containerColor = MainColor.miruni_green,
        topBar = {
            HeaderRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp, vertical = 16.dp),
                viewModel = viewModel
            )
        }
    ) { innerPadding ->
        HomeContent(
            state = state,
            onEvent = viewModel::setEvent,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding()),
            viewModel = viewModel
        )
    }
}

@Composable
fun HomeContent(
    state: HomeContract.State,
    onEvent: (HomeContract.Event) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel
) {
    LazyColumn (
        modifier = modifier.fillMaxSize(),
//  만약 바텀 네비게이션으로 UI 가려지면 해당 주석 풀기
//        contentPadding = PaddingValues(
//            bottom = WindowInsets.navigationBars
//                .asPaddingValues()
//                .calculateBottomPadding()
//        )
    ) {
        item {
            TopSection(
                modifier = Modifier.wrapContentHeight(),
                viewModel = viewModel
            )
        }

        item {
            BottomSection(
                state = state,
                onEvent = onEvent,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun TopSection(
    modifier: Modifier,
    viewModel: HomeViewModel
) {
    Box (modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 18.dp)
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
        ) {
            DescriptionSection()
            ProgressBarSection()
            ButtonSection(viewModel = viewModel)
        }
    }
}

@Composable
fun BottomSection(
    state: HomeContract.State,
    onEvent: (HomeContract.Event) -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .background(
                color = Color.White,
                shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp))
    ) {
        Text(
            text = "오늘의 일정",
            style = AppTypography.PretendardTextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            ),
            color = Color.Black,
            modifier = Modifier.padding(start = 21.dp, top = 27.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        state.schedules.forEach { schedule ->
            TodayScheduleItem(
                item = schedule,
                isSelected = state.selectedScheduleId == schedule.id,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 17.dp),
                onClick = {
                    onEvent(HomeContract.Event.OnScheduleClick(schedule.id))
                }
            )

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

/**
 * 헤더
 */
@Composable
fun HeaderRow(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "logo"
        )

        Text(
            text = "MIRUNI",
            fontSize = 18.sp,
            style = AppTypography.AlexandriaTextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp,
                letterSpacing = 0f.em
            ),
            modifier = Modifier
                .padding(start = 8.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        Image(
            painter = painterResource(id = R.drawable.peanut),
            contentDescription = "peanut"
        )
        Text(
            text = peanut.toString(),
            style = AppTypography.header_bold_16,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.bell),
            contentDescription = "bell",
            modifier = Modifier.clickable {
                viewModel.setEvent(HomeContract.Event.OnAlarmClick)
            }
        )
    }
}

/**
 * 메인 설명 영역
 */
@Composable
fun DescriptionSection(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(270.dp)
            .padding(bottom = 26.dp)
    ) {
        Text(
            text = convertBold("'가영'님,\n오늘은 더 나은 하루가 될거예요.\n'미루니가 함께해요!'"),
            style = AppTypography.PretendardTextStyle(
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp,
                lineHeightRatio = 1.23f
            ),
            color = Color.White,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 40.dp)
        )

        // Miruni 이미지
        Column (
            modifier = Modifier
                .align(Alignment.BottomEnd),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.miruni_basic),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.miruni_shadow),
                contentDescription = null,
                modifier = Modifier
                    .width(57.dp)
                    .height(12.dp)
                    .offset(x = 20.dp)
            )
        }
    }
}

/**
 * 진행률 박스
 */
@Composable
fun ProgressBarSection(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.White, shape = RoundedCornerShape(10.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = "진행률",
                color = Color.Black,
                style = AppTypography.sub_bold_14
            )
            Text(
                text = "오늘 목표의 " + (achievementRate * 100).toInt() + "%를 달성했어요!",
                color = Gray.gray_500,
                style = AppTypography.description_regular_9
            )

            Spacer(modifier = Modifier.height(16.dp))

            LinearProgressBar(
                progress = achievementRate,
                modifier = Modifier
                    .fillMaxWidth(),
                height = 12.dp,
                progressColor = MainColor.miruni_green,
                backgroundColor = Color(0xFFE5E7EB)
            )
        }
    }
}

@Composable
fun ButtonSection(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 17.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .height(126.dp)
                .background(color = Color(0xFFB9E8C6), shape = RoundedCornerShape(10.dp))
                .padding(start = 25.dp, end = 15.dp, top = 15.dp, bottom = 15.dp)
                .clickable { viewModel.setEvent(HomeContract.Event.OnAiPlannerClick) }
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.TopStart),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.miruni_pencil),
                    contentDescription = "AI Planner",
                    modifier = Modifier
                        .width(65.dp)
                        .height(52.dp)
                )
                Image(
                    painter = painterResource(R.drawable.miruni_shadow),
                    contentDescription = null,
                    modifier = Modifier
                        .width(30.dp)
                        .height(5.dp)
                        .offset(x = (-10).dp)
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "AI 플래너 바로가기",
                style = AppTypography.PretendardTextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp
                ),
                color = Gray.gray_700,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
            )
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .height(126.dp)
                .background(color = Color(0xFFB9E8C6), shape = RoundedCornerShape(10.dp))
                .padding(start = 25.dp, end = 15.dp, top = 15.dp, bottom = 15.dp)
                .clickable { viewModel.setEvent(HomeContract.Event.OnDndClick) }
        ) {
            Column(
                modifier = Modifier.align(Alignment.TopStart),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.miruni_lock),
                    contentDescription = "DND",
                    modifier = Modifier
                        .width(65.dp)
                        .height(52.dp)
                )
                Image(
                    painter = painterResource(R.drawable.miruni_shadow),
                    contentDescription = null,
                    modifier = Modifier
                        .width(30.dp)
                        .height(5.dp)
                        .offset(x = (-10).dp)
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "방해금지 모드 바로가기",
                style = AppTypography.PretendardTextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp
                ),
                color = Gray.gray_700,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    MiruniTheme {
        HomeScreen(navController = rememberNavController())
    }
}
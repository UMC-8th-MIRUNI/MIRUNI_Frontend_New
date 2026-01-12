package com.miruni.feature.aiplanner.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.miruni.core.designsystem.AppTypography
import com.miruni.core.designsystem.Gray
import com.miruni.core.designsystem.MainColor
import com.miruni.core.navigation.MiruniRoute
import com.miruni.feature.aiplanner.R
import com.miruni.feature.aiplanner.common.convertBold
import com.miruni.feature.aiplanner.presentation.AiPlannerContract
import com.miruni.feature.aiplanner.presentation.AiPlannerViewModel
import com.miruni.feature.aiplanner.presentation.components.ScheduleItem

@Composable
fun AiPlannerMainScreen(
    navController: NavController,
    viewModel: AiPlannerViewModel = hiltViewModel()
) {
    val state by viewModel.viewState.collectAsState()

    Scaffold(
        containerColor = Color.White,
    ) { innerPadding ->
        AiPlannerMainContent(
            state = state,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding()),
            onClickAiPlanning = {
                navController.navigate(MiruniRoute.AiPlannerPlanning.route)
            }
        )
    }
}

/**
 * AI 플래너 메인
 */
@Composable
fun AiPlannerMainContent(
    state: AiPlannerContract.State,
    modifier: Modifier = Modifier,
    onClickAiPlanning: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 19.dp)
    ) {
        item {
            ToAiPlanning(
                state = state,
                onClickAiPlanning = onClickAiPlanning
            )
        }

        item { Spacer(modifier = Modifier.height(41.dp)) }

        this.AiSchedules(state = state)
    }
}

@Composable
fun ToAiPlanning(
    state: AiPlannerContract.State,
    onClickAiPlanning: () -> Unit
) {
    Text(
        text = "AI 플래너",
        style = AppTypography.header_bold_16
    )
    Spacer(modifier = Modifier.height(25.dp))
    AiPlannerButton(
        onClick = onClickAiPlanning
    )
    Spacer(modifier = Modifier.height(11.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
    ) {
        Text(
            text = "잔여 횟수: ${state.remain}회",
            style = AppTypography.PretendardTextStyle(
                fontWeight = FontWeight.Medium,
                fontSize = 11.sp,
                letterSpacing = (-0.005f).em,
                lineHeightRatio = 1f
            )
        )
    }
}

@Composable
fun AiPlannerButton(
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .border(
                width = 1.dp,
                color = MainColor.miruni_green,
                shape = RoundedCornerShape(9.dp)
            )
            .background(
                color = Gray.gray_300,
                shape = RoundedCornerShape(9.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 11.dp,
                    vertical = 14.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = convertBold("'AI 플래닝' 하러 가기"),
                style = AppTypography.body_regular_14
            )
            Image(
                painter = painterResource(R.drawable.right_arrow),
                colorFilter = ColorFilter.tint(Color.Gray),
                contentDescription = null
            )
        }
    }
}

fun LazyListScope.AiSchedules(
    state: AiPlannerContract.State
) {
    item {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "스케줄",
                style = AppTypography.header_bold_16
            )

            Spacer(modifier = Modifier.width(13.dp))
            CompleteTagButton()
            Spacer(modifier = Modifier.width(6.dp))
            ProgressingTagButton()
        }

        Spacer(modifier = Modifier.height(24.dp))
    }

    items(state.aiPlans.size) { index ->
        val plan = state.aiPlans[index]
        ScheduleItem(
            title = plan.title,
            doneCount = plan.doneCount,
            totalCount = plan.totalCount,
            progressRate = plan.progressRate
        )

        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
fun CompleteTagButton() {
    Box(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = Gray.gray_400,
                shape = RoundedCornerShape(9.dp)
            )
            .background(
                color = Gray.gray_300,
                shape = RoundedCornerShape(9.dp)
            )
            .padding(horizontal = 8.dp, vertical = 2.dp)
    ) {
        Text(
            text = "완료",
            style = AppTypography.button_regular_9,
            color = Gray.gray_700
        )
    }
}

@Composable
fun ProgressingTagButton() {
    Box(
        modifier = Modifier
            .border(
                width = 1.dp,
//                color = Color(0xFFC9F0CD),
                color = Gray.gray_400,
                shape = RoundedCornerShape(9.dp)
            )
            .background(
//                color = Color(0xFFE8F7EC),
                color = Gray.gray_300,
                shape = RoundedCornerShape(9.dp)
            )
            .padding(horizontal = 8.dp, vertical = 2.dp)
    ) {
        Text(
            text = "진행중",
            style = AppTypography.button_regular_9,
            color = Gray.gray_700
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewAiPlannerScreen() {
    AiPlannerMainScreen(navController = rememberNavController())
//    AiPlannerOnboardingScreen(navController = rememberNavController())
}
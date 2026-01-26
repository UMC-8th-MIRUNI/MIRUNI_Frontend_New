package com.miruni.feature.home.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.miruni.core.designsystem.AppTypography
import com.miruni.core.designsystem.Black
import com.miruni.core.designsystem.Gray
import com.miruni.feature.home.HomeContract
import com.miruni.feature.home.HomeViewModel
import com.miruni.feature.home.R
import com.miruni.feature.home.presentation.model.AlarmLogItemUiModel

@Composable
fun AlarmLogScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state = viewModel.viewState.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.effect.collect { event ->
            when (event) {
                HomeContract.Effect.PopBack -> { navController.popBackStack() }
                else -> {}
            }
        }
    }

    AlarmLogContent(
        state = state,
        onClickBack = { viewModel.setEvent(HomeContract.Event.OnBackClick) }
    )
}

@Composable
fun AlarmLogContent(
    state: HomeContract.State,
    onClickBack: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Gray.background_gray)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White)
                .padding(start = 15.dp, top = 59.dp, bottom = 18.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.left_arrow),
                contentDescription = "back",
                modifier = Modifier
                    .clickable { onClickBack() }
                    .align(Alignment.CenterStart)
            )

            Text(
                text = "알림",
                style = AppTypography.header_bold_16.copy(
                    letterSpacing = (-0.05).em
                ),
                color = Color(0xFF19191B)
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {

        }
    }
}

@Composable
fun AlertAlarmLogRow(
    item: AlarmLogItemUiModel.Alert
) {
// 일정 예정 알림 아이템
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(93.dp)
            .background(color = Gray.background_gray)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Gray.background_gray)
                .padding(start = 35.dp, end = 30.dp, top = 35.dp, bottom = 18.dp)
        ) {
            Text(
                text = "10분 뒤 '${item.planTitle}'가 예정되어 있어요!",
                style = AppTypography.sub_bold_14,
                color = Black,
                modifier = Modifier.align(Alignment.TopStart)
            )

            Text(
                text = "${item.minutesAgo}분 전",
                style = AppTypography.button_regular_9,
                color = Gray.gray_500,
                modifier = Modifier.align(Alignment.TopEnd)
            )

            Text(
                text = "10분 뒤 '${item.planTitle}'일정을 시작하고, 땅콩 3개를 획득하세요!",
                style = AppTypography.description_regular_9.copy(
                    lineHeight = (1.5f * 9).sp,
                    letterSpacing = (-0.01).em
                ),
                color = Gray.gray_500,
                modifier = Modifier.align(Alignment.BottomStart)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(horizontal = 17.dp)
                .background(color = Gray.gray_500)
                .align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun WarnAlarmLogRow(
    item: AlarmLogItemUiModel.Warn
) {
    // 경고 아이템
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(93.dp)
            .background(color = Gray.background_gray)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Gray.background_gray)
                .padding(start = 35.dp, end = 30.dp, top = 35.dp, bottom = 18.dp)
        ) {
            Text(
                text = "${item.userName}님, '${item.planTitle}'를 얼른 시작하세요!",
                style = AppTypography.sub_bold_14,
                color = Black,
                modifier = Modifier.align(Alignment.TopStart)
            )

            Text(
                text = "${item.minutesAgo}분 전",
                style = AppTypography.button_regular_9,
                color = Gray.gray_500,
                modifier = Modifier.align(Alignment.TopEnd)
            )

            Text(
                text = "지금 바로 '${item.planTitle}'일정을 시작하지 않으면, 땅콩을 잃을 거예요!",
                style = AppTypography.description_regular_9.copy(
                    lineHeight = (1.5f * 9).sp,
                    letterSpacing = (-0.01).em
                ),
                color = Gray.gray_500,
                modifier = Modifier.align(Alignment.BottomStart)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(horizontal = 17.dp)
                .background(color = Gray.gray_500)
                .align(Alignment.BottomCenter)
        )
    }
}

@Preview
@Composable
fun PreviewAlarmLogScreen(){
//    AlarmLogContent()
    Column {
        AlertAlarmLogRow(
            item = AlarmLogItemUiModel.Alert(
                id = 1,
                planTitle = "밥 먹기",
                minutesAgo = 10
            )
        )
        WarnAlarmLogRow(
            item = AlarmLogItemUiModel.Warn(
                id = 1,
                planTitle = "밥 먹기",
                minutesAgo = 10,
                userName = "미루니"
            )
        )
    }
}
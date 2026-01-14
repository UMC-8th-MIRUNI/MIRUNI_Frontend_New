package com.miruni.feature.aiplanner.presentation.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.miruni.core.designsystem.MainColor
import com.miruni.feature.aiplanner.R
import com.miruni.feature.aiplanner.presentation.AiPlannerContract
import com.miruni.feature.aiplanner.presentation.AiPlannerViewModel
import com.miruni.feature.aiplanner.presentation.components.DotToDot
import com.miruni.feature.aiplanner.presentation.components.PlanningQuestionCard
import com.miruni.feature.aiplanner.presentation.components.PlanningResultCardWrapper
import com.miruni.feature.aiplanner.presentation.model.PlanningFormItemUiModel
import com.miruni.feature.aiplanner.presentation.model.YInformation
import kotlinx.coroutines.delay

@Composable
fun AiPlannerPlanningScreen(
    viewModel: AiPlannerViewModel = hiltViewModel(),
    navController: NavController
) {
    val state by viewModel.viewState.collectAsState()
    val dotPositions = remember { mutableStateListOf<YInformation>() }
    var firstItemTop by remember { mutableStateOf<Float?>(null) }

    val listState = rememberLazyListState()

    // 화면 진입 시 다음 요소가 추가되면 자동으로 스크롤
    LaunchedEffect(state.forms.count { it.visible }) {
        if (state.forms.any { it.visible }) {
            delay(300)
            listState.animateScrollToItem(state.forms.indexOfLast { it.visible } + 1) // +1 for Header
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 60.dp), // 하단 버튼 영역 확보
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp)
        ) {
            // 상단 X 버튼
            item {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Image(
                        painter = painterResource(R.drawable.cancel),
                        contentDescription = "닫기",
                        modifier = Modifier
                            .size(26.dp)
                            .clickable { navController.popBackStack() }
                    )
                }
            }

            // 사용자 입력 영역
            itemsIndexed(state.forms) { index, item ->
                AnimatedVisibility(
                    visible = item.visible,
                    enter = fadeIn() + expandVertically()
                ) {
                    PlanningItemRow(
                        index = index,
                        item = item,
                        onPositionCalculated = { top, height ->
                            if (index == 0) firstItemTop = top
                            if (dotPositions.size <= index) dotPositions.add(YInformation(top, height))
                            else dotPositions[index] = YInformation(top, height)
                        },
                        viewModel = viewModel
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(100.dp)) }
        }

        // 점 연결 선 그리기
        DotToDot(dotPositions, firstItemTop)

        // "다음" 버튼
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .fillMaxWidth()
                .background(Color.White.copy(alpha = 0.9f)) // 살짝 투명 배경
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .clickable { viewModel.setEvent(AiPlannerContract.Event.Submit) },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "다음",
                    fontSize = 14.sp,
                    color = MainColor.miruni_green,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(4.dp))
                Image(
                    painter = painterResource(R.drawable.right_arrow),
                    contentDescription = "다음",
                    colorFilter = ColorFilter.tint(MainColor.miruni_green),
                    modifier = Modifier.size(17.dp)
                )
            }
        }
    }
}

@Composable
fun PlanningItemRow(
    index: Int,
    item: PlanningFormItemUiModel,
    onPositionCalculated: (Float, Float) -> Unit,
    viewModel: AiPlannerViewModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp)
    ) {
        // 점 찍히는 공간
        Spacer(modifier = Modifier.width(32.dp))

        Column(modifier = Modifier.weight(1f)) {
            // 질문 카드
            PlanningQuestionCard(
                title = item.title,
                onPositionCalculated = onPositionCalculated
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 결괏값 출력(및 입력) 카드
            PlanningResultCardWrapper(
                item = item,
                viewModel = viewModel
            )
        }
    }
}

@Preview
@Composable
fun PreviewAiPlannerPlanningScreen() {
    AiPlannerPlanningScreen(navController = rememberNavController())
}
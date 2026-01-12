package com.miruni.feature.aiplanner.presentation.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.miruni.core.designsystem.AppTypography
import com.miruni.core.designsystem.MainColor
import com.miruni.feature.aiplanner.R
import com.miruni.feature.aiplanner.common.convertBold
import com.miruni.feature.aiplanner.domain.model.PlanInput
import com.miruni.feature.aiplanner.presentation.AiPlannerContract
import com.miruni.feature.aiplanner.presentation.AiPlannerViewModel
import com.miruni.feature.aiplanner.presentation.components.AiPlannerDatePicker
import com.miruni.feature.aiplanner.presentation.components.AiPlannerDropdown
import com.miruni.feature.aiplanner.presentation.components.AiPlannerTextInput
import com.miruni.feature.aiplanner.presentation.components.DotToDot
import com.miruni.feature.aiplanner.presentation.model.YInformation

@Composable
fun AiPlannerPlanningScreen(
    viewModel: AiPlannerViewModel = hiltViewModel(),
    navController: NavController
) {
    val state by viewModel.viewState.collectAsState()
    val dotPositions = remember { mutableStateListOf<YInformation>() }
    var firstItemTop by remember { mutableStateOf<Float?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 32.dp, end = 16.dp),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Image(
                        painter = painterResource(R.drawable.cancel),
                        contentDescription = "이전 페이지로"
                    )
                }
            }

            items(state.forms.size) { index ->
                val item = state.forms[index]

                AnimatedVisibility(
                    visible = item.visible,
                    enter = fadeIn(animationSpec = tween(500)) + slideInVertically(animationSpec = tween(500), { it / 2 }),
                    exit = fadeOut()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Spacer(modifier = Modifier.width(24.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            // 제목
                            Card(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .border(
                                        width = 1.dp,
                                        color = Color(0xFFB3B3B3),
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    .onGloballyPositioned { coords ->
                                        val itemTop = coords.positionInRoot().y
                                        val itemHeight = coords.size.height.toFloat()

                                        if (index == 0) firstItemTop = itemTop

                                        if (dotPositions.size <= index) dotPositions.add(YInformation(itemTop, itemHeight))
                                        else dotPositions[index] = YInformation(itemTop, itemHeight)
                                    },
                                shape = RoundedCornerShape(10.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White)
                            ) {
                                Text(
                                    text = convertBold(item.title),
                                    style = AppTypography.body_regular_14,
                                    modifier = Modifier
                                        .padding(horizontal = 35.dp, vertical = 19.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(
                                        width = 1.dp,
                                        color = Color(0xFFB3B3B3),
                                        shape = RoundedCornerShape(10.dp)
                                    ),
                                shape = RoundedCornerShape(10.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White)
                            ) {
                                when(item.id) {
                                    "until" -> AiPlannerDatePicker(
                                        disPlayValue = (item.value as? PlanInput.Date)?.let {
                                            "${it.startDate} ${it.startTime} - ${it.endDate} ${it.endTime}"
                                        },
                                        onRangeSelected = { sd, ed, st, et ->
                                            viewModel.setEvent(
                                                AiPlannerContract.Event.SelectDate(
                                                    item.id,
                                                    sd,
                                                    ed,
                                                    st,
                                                    et
                                                )
                                            )
                                        }
                                    )

                                    "when" -> AiPlannerDropdown(
                                        options = listOf(
                                            "랜덤으로 설정",
                                            "아침 시간 (6~9시)",
                                            "오전 집중 시간 (9~12시)",
                                            "오후 느슨한 시간 (13~17시)",
                                            "저녁 시간 (18~21시)",
                                            "밤 시간 ((22~24시)",
                                            "새벽 (0~6시)"
                                        ),
                                        selected = (item.value as? PlanInput.Option)?.option,
                                        onSelect = {
                                            viewModel.setEvent(
                                                AiPlannerContract.Event.SelectOption(item.id, it)
                                            )
                                        }
                                    )

                                    "priority" -> AiPlannerDropdown(
                                        options = listOf(
                                            "상",
                                            "중",
                                            "하"
                                        ),
                                        selected = (item.value as? PlanInput.Option)?.option,
                                        onSelect = {
                                            viewModel.setEvent(
                                                AiPlannerContract.Event.SelectOption(item.id, it)
                                            )
                                        }
                                    )

                                    else -> AiPlannerTextInput(
                                        value = (item.value as? PlanInput.Text)?.text ?: "",
                                        placeholder = item.placeholder,
                                        onValueChange = {
                                            viewModel.setEvent(
                                                AiPlannerContract.Event.InputText(item.id, it)
                                            )
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Image(
                        painter = painterResource(R.drawable.right_arrow),
                        contentDescription = "다음",
                        colorFilter = ColorFilter.tint(MainColor.miruni_green)
                    )

                    Text(
                        text = "다음",
                        style = AppTypography.PretendardTextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            lineHeightRatio = 1f
                        ),
                        color = MainColor.miruni_green
                    )
                }
            }
        }

        DotToDot(dotPositions, firstItemTop)
    }
}

@Preview
@Composable
fun PreviewAiPlannerPlanningScreen() {
    AiPlannerPlanningScreen(navController = rememberNavController())
}
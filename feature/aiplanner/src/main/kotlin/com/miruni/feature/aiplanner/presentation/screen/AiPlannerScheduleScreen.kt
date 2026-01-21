package com.miruni.feature.aiplanner.presentation.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.Popup
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.miruni.core.designsystem.AppTypography
import com.miruni.core.designsystem.Gray
import com.miruni.core.designsystem.MainColor
import com.miruni.core.navigation.MiruniRoute
import com.miruni.feature.aiplanner.R
import com.miruni.feature.aiplanner.presentation.AiPlannerContract
import com.miruni.feature.aiplanner.presentation.AiPlannerViewModel
import com.miruni.feature.aiplanner.presentation.components.DateUtils
import com.miruni.feature.aiplanner.presentation.components.filterDateInput
import com.miruni.feature.aiplanner.presentation.components.filterTimeInput
import com.miruni.feature.aiplanner.presentation.model.AiPlanUiModel
import com.miruni.feature.aiplanner.presentation.model.PlanUiModel
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun AiPlannerScheduleScreen(
    navController: NavController,
    viewModel: AiPlannerViewModel = hiltViewModel()
) {
    val state by viewModel.viewState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                AiPlannerContract.Effect.Navigation.ToMain -> navController.navigate(MiruniRoute.AiPlannerMain.route) {
                    popUpTo(MiruniRoute.AiPlannerMain.route) { inclusive = true }
                }
                else -> Unit
            }
        }
    }

    state.plan?.let { plan ->
        AiPlannerScheduleContent(
            plan = plan,
            isEditMode = state.isEditMode,
            showMenu = state.showMenu,
            onBack = { viewModel.setEvent(AiPlannerContract.Event.OnMain) },
            onMenu = { viewModel.setEvent(AiPlannerContract.Event.ClickMenu) },
            onEdit = { viewModel.setEvent(AiPlannerContract.Event.ClickEdit) },
            onDelete = { viewModel.setEvent(AiPlannerContract.Event.ClickDelete) },
            onCompleteEdit = { updatedPlan, updatedAiPlans ->
                viewModel.setEvent(
                    AiPlannerContract.Event.ClickCompleteEdit(
                        planId = plan.planId,
                        title = updatedPlan.title,
                        deadline = updatedPlan.deadline,
                        taskRange = updatedPlan.taskRange,
                        priority = updatedPlan.priority,
                        aiPlans = updatedAiPlans
                    )
                )
            }
        )
    }
}

@Composable
fun AiPlannerScheduleContent(
    plan: PlanUiModel,
    isEditMode: Boolean,
    showMenu: Boolean,
    onBack: () -> Unit,
    onMenu: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onCompleteEdit: (PlanUiModel, List<AiPlanUiModel>) -> Unit
) {
    // 수정을 위한 상태
    var draftTitle by remember(plan) { mutableStateOf(plan.title) }
    var draftDeadline by remember(plan) { mutableStateOf(plan.deadline) }
    var draftRange by remember(plan) { mutableStateOf(plan.taskRange) }
    var draftPriority by remember(plan) { mutableStateOf(plan.priority) }
    val draftAiPlans = remember(plan) { mutableStateListOf(*plan.aiPlans.toTypedArray()) }

    Scaffold(
        bottomBar = {
            /** 완료 버튼 */
            if (isEditMode) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp)
                        .padding(bottom = 20.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(49.dp)
                            .background(
                                color = MainColor.miruni_green,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .clickable {
                                onCompleteEdit(
                                    plan.copy(
                                        title = draftTitle,
                                        deadline = draftDeadline,
                                        taskRange = draftRange,
                                        priority = draftPriority
                                    ),
                                    draftAiPlans.toList()
                                )
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "완료",
                            color = Color(0xFFF9F9F9),
                            style = AppTypography.button_semibold_16,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
                .padding(innerPadding)
                .padding(horizontal = 15.dp, vertical = 20.dp)
        ) {

            /** 제목 영역 */
            Box(modifier = Modifier
                .fillMaxWidth()
            ) {
                // 뒤로 가기
                Image(
                    painter = painterResource(R.drawable.left_arrow),
                    contentDescription = null,
                    modifier = Modifier
                        .size(17.dp)
                        .align(Alignment.CenterStart)
                        .clickable { onBack() }
                )

                // 상위 일정 제목
                if (isEditMode) {
                    BasicTextField(
                        value = draftTitle,
                        onValueChange = { draftTitle = it },
                        textStyle = AppTypography.header_bold_20.copy(textAlign = TextAlign.Center),
                        modifier = Modifier.align(Alignment.Center)
                    )
                } else {
                    Text(
                        text = draftTitle,
                        style = AppTypography.header_bold_20,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                // 메뉴 버튼
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 9.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.dots_menu),
                        contentDescription = null,
                        modifier = Modifier
                            .size(width = 3.dp, height = 13.dp)
                            .clickable { onMenu() }
                    )

                    if (showMenu) {
                        Dialog(
                            onDismissRequest = { onMenu() },
                            properties = DialogProperties(
                                usePlatformDefaultWidth = false // 윈도우가 화면 전체를 쓰도록 허용
                            )
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clickable { onMenu() }
                            ) {
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .padding(top = 60.dp, end = 20.dp)
                                ) {
                                    MenuPopup(onEdit, onDelete)
                                }
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(22.dp))

            /** 설명 영역 */
            DescriptionSection(
                deadline = draftDeadline,
                taskRange = draftRange,
                priority = draftPriority,
                isEditMode = isEditMode,
                onDeadlineChange = { draftDeadline = it },
                onRangeChange = { draftRange = it },
                onPriorityChange = { draftPriority = it }
            )

            Spacer(Modifier.height(20.dp))

            /** 스케줄 표 */
            ScheduleTable(
                aiPlans = draftAiPlans,
                isEditMode = isEditMode,
                modifier = Modifier.fillMaxWidth(),
                onPlanChange = { index, updatedPlan ->
                    draftAiPlans[index] = updatedPlan
                }
            )

        }

    }

}

@Composable
fun MenuPopup(
    onEdit: () -> Unit,
    onDelete: () -> Unit
){
    val popupShape = RoundedCornerShape(5.dp)

    Column(
        modifier = Modifier
            .width(102.dp)
            .graphicsLayer {
                shape = popupShape
                clip = true
                shadowElevation = 8f
            }
            .background(color = Color(0xFFF8F8F8))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onEdit() }
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "수정하기", fontSize = 12.sp, color = Color.Black)
        }
        HorizontalDivider(color = Color(0xFFE7E7E7))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onDelete() }
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "삭제하기", fontSize = 12.sp, color = Color.Black)
        }
    }
}

@Composable
fun DescriptionSection(
    deadline: String,
    taskRange: String,
    priority: String,
    isEditMode: Boolean,
    onDeadlineChange: (String) -> Unit,
    onRangeChange: (String) -> Unit,
    onPriorityChange: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(color = Color(0xFFF3F3F3), shape = RoundedCornerShape(10.dp))
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(top = 22.dp, bottom = 11.dp)
        ) {
            // 기한
            DescriptionRow(
                iconId = R.drawable.deadline,
                label = "기한",
                value = deadline,
                isEditMode = isEditMode,
                onValueChange = onDeadlineChange
            )
            Spacer(modifier = Modifier.height(11.dp))
            // 범위
            DescriptionRow(
                iconId = R.drawable.task_range,
                label = "범위",
                value = taskRange,
                isEditMode = isEditMode,
                onValueChange = onRangeChange
            )
            Spacer(modifier = Modifier.height(11.dp))
            // 우선순위
            DescriptionRow(
                iconId = R.drawable.priority,
                label = "우선순위",
                value = priority,
                isEditMode = isEditMode,
                onValueChange = onPriorityChange
            )
        }

        // 우측 원형 이미지 (30% 출력) -> 역할 모호. 진행률?
        Box(modifier = Modifier.align(Alignment.CenterEnd)) {
            Canvas(modifier = Modifier.size(53.dp)) {
                // 회색 배경 원
                drawArc(
                    color = MainColor.miruni_green,
                    startAngle = 0f,
                    sweepAngle = 360f,
                    useCenter = false,
                    style = Stroke(width = 1f, cap = StrokeCap.Round)
                )
                // 초록색 진행도 원 (30% = 108도)
//                drawArc(
//                    color = MainColor.miruni_green,
//                    startAngle = -90f,
//                    sweepAngle = 108f, // 30%
//                    useCenter = false,
//                    style = Stroke(width = 15f, cap = StrokeCap.Round)
//                )
            }
            Text(
                text = "30%",
                style = AppTypography.sub_medium_14,
                color = MainColor.miruni_green,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
fun DescriptionRow(
    iconId: Int,
    label: String,
    value: String,
    isEditMode: Boolean,
    onValueChange: (String) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 아이콘
        Image(
            painter = painterResource(iconId),
            contentDescription = null,
            modifier = Modifier
                .size(15.dp)
        )

        Spacer(Modifier.width(18.dp))

        // 항목명
        Text(
            text = label,
            style = AppTypography.body_regular_12,
            color = Color(0xFF626262)
        )

        Spacer(Modifier.width(39.dp))

        if (isEditMode) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                textStyle = AppTypography.body_regular_12,
                modifier = Modifier
                    .padding(horizontal = 4.dp)
            )
        } else {
            Text(
                text = value,
                style = AppTypography.body_regular_12
            )
        }
    }
}

@Composable
fun ScheduleTable(
    aiPlans: List<AiPlanUiModel>,
    isEditMode: Boolean,
    modifier: Modifier = Modifier,
    onPlanChange: (Int, AiPlanUiModel) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Color(0xFFBBBBBB),
                shape = RoundedCornerShape(10.dp)
            )
            .clip(RoundedCornerShape(10.dp))
    ) {
        // 헤더
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(43.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(10.dp)
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "날짜",
                modifier = Modifier.width(70.dp),
                textAlign = TextAlign.Center,
                style = AppTypography.sub_semibold_12,
                color = Gray.gray_500
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(color = Color(0xFFF4FCF6)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "세부 일정",
                    textAlign = TextAlign.Center,
                    style = AppTypography.sub_semibold_12,
                    color = Gray.gray_500
                )
            }


            Text(
                "예상 소요 시간",
                modifier = Modifier.width(94.dp),
                textAlign = TextAlign.Center,
                style = AppTypography.sub_semibold_12,
                color = Gray.gray_500
            )
        }

        Divider(color = Color(0xFFBBBBBB), thickness = 1.dp)

        // 내용 리스트
        LazyColumn {
            itemsIndexed(aiPlans) { index, plan ->
                ScheduleRow(
                    plan = plan,
                    isEditMode = isEditMode,
                    onUpdate = { updatedPlan -> onPlanChange(index, updatedPlan) }
                )
                if (index < aiPlans.lastIndex) {
                    Divider(color = Color(0xFFBBBBBB), thickness = 0.5.dp)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScheduleRow(
    plan: AiPlanUiModel,
    isEditMode: Boolean,
    onUpdate: (AiPlanUiModel) -> Unit
) {
    var showDetail by remember { mutableStateOf(false) } // 길이가 길어진 세부 일정의 경우, 클릭 시 모달로 상세 내용을 확인할 수 있게 함

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically
    ) {

        /** 날짜 + 시간 영역 */
        Column(
            modifier = Modifier
                .width(70.dp)
                .padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            DateRow(
                text = plan.scheduledDate,
                isEditMode = isEditMode,
                onChange = {
                    onUpdate(plan.copy(scheduledDate = it))
                }
            )

            Spacer(modifier = Modifier.height(4.dp))

            TimeRow(
                start = plan.startTime,
                end = plan.endTime,
                isEditMode = isEditMode,
                onChange = { s, e ->
                    onUpdate(plan.copy(startTime = s, endTime = e))
                }
            )
        }

        // 2. 세부 일정 열
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(color = Color(0xFFF4FCF6))
                .clickable { if (!isEditMode) showDetail = true } // 클릭 시 모달
                .padding(horizontal = 14.dp, vertical = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            if (isEditMode) {
                BasicTextField(
                    value = plan.content,
                    onValueChange = { onUpdate(plan.copy(content = it)) },
                    textStyle = AppTypography.body_regular_12,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            } else {
                Text(
                    text = plan.content,
                    style = AppTypography.body_regular_12,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        // 3. 예상 소요 시간 열
        Box(
            modifier = Modifier
                .width(94.dp)
                .fillMaxHeight()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            // 시간 계산 표시
            val hours = plan.expectedDuration / 60
            val minutes = plan.expectedDuration % 60
            val durationText = "${hours}시간 ${minutes}분"

            Text(
                text = durationText,
                style = AppTypography.sub_bold_14,
                color = Gray.gray_700,
                textAlign = TextAlign.Center
            )
        }
    }

    // 상세 내용 모달
    if (showDetail) {
        Dialog(onDismissRequest = { showDetail = false }) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(10.dp))
                    .padding(20.dp)
            ) {
                Text(text = plan.content, style = AppTypography.body_regular_12)
            }
        }
    }
}

@Composable
private fun DateRow(
    text: String,
    isEditMode: Boolean,
    onChange: (String) -> Unit
) {
    val dateText = try {
        LocalDate.parse(text, DateUtils.serverDateFmt) .format(DateUtils.uiDateFmt)
    } catch(e: Exception) {
        text
    }
    if (!isEditMode) {
        Text(
            text = dateText,
            style = AppTypography.sub_bold_14.copy(color = Gray.gray_700),
            textAlign = TextAlign.Center
        )
        return
    }

    BasicTextField(
        value = dateText,
        onValueChange = {
            onChange(filterDateInput(it))
        },
        singleLine = true,
        textStyle = AppTypography.sub_bold_14.copy(
            color = Gray.gray_700,
            textAlign = TextAlign.Center
        ),
    )
}

@Composable
private fun TimeRow(
    start: String,
    end: String,
    isEditMode: Boolean,
    onChange: (String, String) -> Unit
) {
    val timeText = remember(start, end) {
            try {
                val s = LocalTime
                    .parse(start, DateUtils.serverTimeFmt)
                    .format(DateUtils.uiTimeFmt)
                val e = LocalTime
                    .parse(end, DateUtils.serverTimeFmt)
                    .format(DateUtils.uiTimeFmt)
                "$s - $e"
            } catch (e: Exception) {
                "$start - $end"
            }
        }

    if (!isEditMode) {
        Text(
            text = timeText,
            style = AppTypography.PretendardTextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 9.sp
            ).copy(color = Gray.gray_500),
            textAlign = TextAlign.Center
        )
        return
    }

    /** 수정 모드 */
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center) {
            TimeText(start) { onChange(it, end) }
        }
        Text(
            text = " - ",
            style = AppTypography.PretendardTextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 9.sp
            ).copy(color = Gray.gray_500),
            textAlign = TextAlign.Center
        )
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            TimeText(end) { onChange(start, it) }
        }
    }
}

@Composable
private fun TimeText(
    value: String,
    onChange: (String) -> Unit
) {
    BasicTextField(
        value = value,
        onValueChange = { onChange(filterTimeInput(it)) },
        singleLine = true,
        textStyle = AppTypography.PretendardTextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 9.sp
        ).copy(color = Gray.gray_500, textAlign = TextAlign.Center),
    )
}


@Preview
@Composable
fun PreviewAiPlannerSchedule() {
    AiPlannerScheduleContent(
        plan = PlanUiModel(
            planId = 1,
            title = "기말고사 준비",
            deadline = "2026-01-25",
            taskRange = "1장부터 3장까지",
            priority = "중",
            aiPlans = listOf(
                AiPlanUiModel(
                    aiPlanId = 1,
                    scheduledDate = "2026-01-20",
                    startTime = "10:00",
                    endTime = "11:00",
                    content = "자료정리 및 요약",
                    expectedDuration = 120
                ),AiPlanUiModel(
                    aiPlanId = 2,
                    scheduledDate = "2026-01-20",
                    startTime = "10:00",
                    endTime = "11:00",
                    content = "자료정리 및 요약",
                    expectedDuration = 120
                ),AiPlanUiModel(
                    aiPlanId = 3,
                    scheduledDate = "2026-01-20",
                    startTime = "10:00",
                    endTime = "11:00",
                    content = "자료정리 및 요약",
                    expectedDuration = 120)
//                ),AiPlanUiModel(
//                    aiPlanId = 4,
//                    scheduledDate = "2026-01-20",
//                    startTime = "10:00",
//                    endTime = "11:00",
//                    content = "자료정리 및 요약",
//                    expectedDuration = 120
//                ),AiPlanUiModel(
//                    aiPlanId = 5,
//                    scheduledDate = "2026-01-20",
//                    startTime = "10:00",
//                    endTime = "11:00",
//                    content = "자료정리 및 요약",
//                    expectedDuration = 120
//                ),AiPlanUiModel(
//                    aiPlanId = 6,
//                    scheduledDate = "2026-01-20",
//                    startTime = "10:00",
//                    endTime = "11:00",
//                    content = "자료정리 및 요약",
//                    expectedDuration = 120
//                ),AiPlanUiModel(
//                    aiPlanId = 7,
//                    scheduledDate = "2026-01-20",
//                    startTime = "10:00",
//                    endTime = "11:00",
//                    content = "자료정리 및 요약",
//                    expectedDuration = 120
//                ),AiPlanUiModel(
//                    aiPlanId = 8,
//                    scheduledDate = "2026-01-20",
//                    startTime = "10:00",
//                    endTime = "11:00",
//                    content = "자료정리 및 요약",
//                    expectedDuration = 120
//                ),AiPlanUiModel(
//                    aiPlanId = 9,
//                    scheduledDate = "2026-01-20",
//                    startTime = "10:00",
//                    endTime = "11:00",
//                    content = "자료정리 및 요약",
//                    expectedDuration = 120
//                ),AiPlanUiModel(
//                    aiPlanId = 10,
//                    scheduledDate = "2026-01-20",
//                    startTime = "10:00",
//                    endTime = "11:00",
//                    content = "자료정리 및 요약",
//                    expectedDuration = 120
//                ),
            )
        ),
        isEditMode = false,
        showMenu = false,
        onBack = {},
        onMenu = {},
        onEdit = {},
        onDelete = {},
        onCompleteEdit = { planUiModel, aiPlanUiModels ->  
            
        }
    )
}
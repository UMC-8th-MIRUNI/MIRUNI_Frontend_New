package com.miruni.feature.survey

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.miruni.core.designsystem.AppTypography
import com.miruni.core.designsystem.Gray
import com.miruni.core.designsystem.MainColor
import com.miruni.core.designsystem.MiruniTheme
import com.miruni.core.designsystem.StrokeColor
import com.miruni.feature.survey.component.SingleGreenButton

enum class SurveyType {
    MULTI, SINGLE
}

data class SurveyQuestion(
    val title: String,
    val subTitle: String,
    val options: List<String>,
    val type: SurveyType
)

@Composable
private fun SurveyScreen(
    navController: NavHostController
) {
    val questions = remember {
        listOf(
            SurveyQuestion(
                title = "주로 어떤 상황에\n일을 미루게 되나요?",
                subTitle = "해당되는 항목을 모두 선택해주세요!",
                options = listOf(
                    "휴대폰을 사용할 때 (SNS, 게임 등)",
                    "넷플릭스, 드라마, 유튜브 등 영상 시청",
                    "친구, 사람들을 만날 때",
                    "다른 일이 너무 많을 때",
                    "너무 피곤할 때"
                ),
                type = SurveyType.MULTI
            ),
            SurveyQuestion(
                title = "본인의 미루는 정도는\n어느 정도라고 생각하시나요?",
                subTitle = "숫자를 하나만 선택해주세요!",
                options = listOf(
                    "거의 미루지 않는다",
                    "가끔 미룬다",
                    "보통이다",
                    "자주 미룬다",
                    "항상 미룬다"
                ),
                type = SurveyType.SINGLE
            ),
            SurveyQuestion(
                title = "주로 어떤 이유로\n일을 미루게 되나요?",
                subTitle = "해당되는 항목을 모두 선택해주세요!",
                options = listOf(
                    "귀찮아서",
                    "일이 너무 커 보여서(부담)",
                    "무엇부터 해야 할지 몰라서",
                    "완벽하게 해내고 싶어서",
                    "집중이 안 돼서",
                    "재미없거나 하기 싫어서"
                ),
                type = SurveyType.MULTI
            )
        )
    }

    var currentStep by remember { mutableStateOf(0) }
    var selectedOptions by remember { mutableStateOf(setOf<Int>()) }

    val question = questions[currentStep]
    val progress = (currentStep + 1) / questions.size.toFloat()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {

        Spacer(Modifier.height(12.dp))

        SurveyTopBar(
            onBackClick = {
                navController.popBackStack()
            }
        )

        Spacer(Modifier.height(16.dp))

        LinearProgressIndicator(
            progress = progress,
            color = MainColor.miruni_green,
            trackColor = Gray.gray_400,
            strokeCap = StrokeCap.Butt,
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .clip(RoundedCornerShape(2.dp))
        )

        Spacer(Modifier.height(32.dp))

        Text(
            text = question.title,
            style = AppTypography.header_bold_20,
        )

        Spacer(Modifier.height(48.dp))

        Text(
            text = question.subTitle,
            style = AppTypography.body_regular_14,
        )

        Spacer(Modifier.height(24.dp))

        OptionList(
            options = question.options,
            selected = selectedOptions,
            isMulti = question.type == SurveyType.MULTI,
            onSelect = { index ->
                selectedOptions =
                    if (question.type == SurveyType.MULTI) {
                        selectedOptions.toggle(index)
                    } else {
                        setOf(index)
                    }
            }
        )

        Spacer(modifier = Modifier.weight(1f))

        BottomButton(
            text = if (currentStep == questions.lastIndex) "확인" else "다음",
            onClick = {
                if (currentStep < questions.lastIndex) {
                    currentStep++
                    selectedOptions = emptySet()
                }
            }
        )

        Spacer(Modifier.height(16.dp))
    }
}

@Composable
fun OptionList(
    options: List<String>,
    selected: Set<Int>,
    isMulti: Boolean,
    onSelect: (Int) -> Unit
) {
    Column {
        options.forEachIndexed { index, text ->
            OptionItem(
                text = text,
                selected = selected.contains(index),
                onClick = { onSelect(index) }
            )
            Spacer(Modifier.height(12.dp))
        }
    }
}

@Composable
fun OptionItem(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 48.dp)
            .border(1.dp, Color(0xFFE9E9E9), RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyMedium
        )
        RadioButton(
            selected = selected,
            onClick = onClick
        )
    }
}

@Composable
fun SurveyTopBar(
    modifier: Modifier = Modifier,
    onBackClick: (() -> Unit)
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        IconButton(
            onClick = {
                onBackClick()
            },
            modifier = Modifier.testTag("backButton")
        ) {
            androidx.compose.material3.Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = "back"
            )
        }
    }
}

@Composable
fun BottomButton(
    text: String,
    onClick: () -> Unit
) {
    SingleGreenButton(
        onClick = onClick,
        text = text
    )
}

private fun Set<Int>.toggle(index: Int): Set<Int> =
    if (contains(index)) this - index else this + index

@Preview(showBackground = true)
@Composable
private fun SurveyScreenPreview() {
    MiruniTheme {
        SurveyScreen(
            navController = rememberNavController()
        )
    }
}
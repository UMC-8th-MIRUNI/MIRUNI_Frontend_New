package com.miruni.feature.signup.component.step

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.miruni.designsystem.AppTypography
import com.miruni.designsystem.Gray
import com.miruni.designsystem.MainColor
import com.miruni.feature.signup.R
import com.miruni.signup.SignUpViewModel
import com.miruni.signup.component.textfield.UnderlineTextField
import com.miruni.signup.model.Term

@Composable
fun SignUpTermStep(
    onPrevStep: () -> Unit,
    onNextStep: () -> Unit,
    onSelectTermContent: (Term) -> Unit = {},
) {
    // 임시 상태(나중에 UiState로 옮겨도 됨)
    var agreeProfile by remember { mutableStateOf(false) }
    var agreeAll by remember { mutableStateOf(false) }
    var agreeTerms by remember { mutableStateOf(false) }
    var agreePrivacy by remember { mutableStateOf(false) }
    var agreeMarketing by remember { mutableStateOf(false) }

    // 전체동의 -> 하위 동기화(간단 버전)
    LaunchedEffect(agreeAll) {
        agreeTerms = agreeAll
        agreePrivacy = agreeAll
        agreeMarketing = agreeAll
    }
    // 하위 -> 전체동의 동기화
    LaunchedEffect(agreeTerms, agreePrivacy, agreeMarketing) {
        agreeAll = agreeTerms && agreePrivacy && agreeMarketing
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(101.dp)
                    .clip(CircleShape)
                    .background(MainColor.miruni_green),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_miruni_default), // 준비된 리소스로 바꿔줘
                    contentDescription = "profile",
                    modifier = Modifier
                        .size(84.dp, 77.dp)
                        .offset(x = 4.dp)
                )
            }
            Spacer(modifier = Modifier.height(26.dp))
            Text(
                "앞으로 어떻게 불러드리면 좋을까요?",
                style = AppTypography.header_bold_16,
                color = Gray.gray_500
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
        ) {
            UnderlineTextField(
                state = rememberTextFieldState(""),
                placeholder = "닉네임",
                textStyle = AppTypography.sub_regular_16,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                modifier = Modifier.fillMaxWidth(),
                onTextChange = { nickname ->

                }
            )
            CheckBoxRow(
                title = "실명으로 불러주세요!",
                checked = false,
                onCheckedChange = {

                }
            )
            Spacer(Modifier.weight(1f))

            CheckBoxRow(
                title = "전체 동의",
                checked = agreeAll,
                onCheckedChange = { agreeAll = it },
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 10.dp),
                thickness = 1.dp,
                color = Gray.gray_500.copy(alpha = 0.5f)
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                CheckBoxRow(
                    title = "이용약관 동의(필수)",
                    checked = agreeTerms,
                    onCheckedChange = { agreeTerms = it },
                    trailingContent = {
                        TextButton(
                            modifier = Modifier.size(65.dp,21.dp),
                            contentPadding = PaddingValues(horizontal = 0.dp, vertical = 0.dp),
                            onClick = {
                                onSelectTermContent(Term.Service)
                            },
                        ) {
                            Text("내용 보기", style = AppTypography.body_regular_14, color = Gray.gray_500)
                        }
                    }
                )
                CheckBoxRow(
                    title = "개인정보 수집 및 이용 동의(필수)",
                    checked = agreePrivacy,
                    onCheckedChange = { agreePrivacy = it },
                    trailingContent = {
                        TextButton(
                            modifier = Modifier.size(65.dp,21.dp),
                            contentPadding = PaddingValues(horizontal = 0.dp, vertical = 0.dp),
                            onClick = {
                                onSelectTermContent(Term.Privacy)
                            },
                        ) {
                            Text("내용 보기", style = AppTypography.body_regular_14, color = Gray.gray_500)
                        }
                    }
                )

                CheckBoxRow(
                    title = "마케팅 정보 수신 동의(선택)",
                    checked = agreeMarketing,
                    onCheckedChange = { agreeMarketing = it },
                    trailingContent = {
                        TextButton(
                            modifier = Modifier.size(65.dp,21.dp),
                            contentPadding = PaddingValues(horizontal = 0.dp, vertical = 0.dp),
                            onClick = {
                                onSelectTermContent(Term.Marketing)
                            },
                        ) {
                            Text("내용 보기", style = AppTypography.body_regular_14, color = Gray.gray_500)
                        }
                    }
                )
            }
            Spacer(modifier = Modifier.height(40.dp))
        }

    }
}

@Composable
private fun CheckBoxRow(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    trailingContent: (@Composable () -> Unit)? = null
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides  0.dp) {
            Checkbox(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = CheckboxDefaults.colors(
                    checkedColor = MainColor.miruni_green,
                )
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = title,
            style = AppTypography.body_regular_14,
        )
        Spacer(Modifier.weight(1f))
        trailingContent?.invoke()
    }
}


@Preview(showBackground = true)
@Composable
fun SignUpTermStepPreview(
    viewModel: SignUpViewModel = viewModel()
) {
    SignUpTermStep(
        onPrevStep = {},
        onNextStep = {},
    )
}
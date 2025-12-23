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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.miruni.core.designsystem.AppTypography
import com.miruni.core.designsystem.Gray
import com.miruni.core.designsystem.MainColor
import com.miruni.feature.signup.R
import com.miruni.feature.signup.SignUpContract
import com.miruni.feature.signup.SignupViewModel
import com.miruni.feature.signup.component.textfield.UnderlineTextField
import com.miruni.feature.signup.model.TextInputField
import com.miruni.feature.signup.model.Term

@Composable
fun SignUpTermStepRoute(
    uiState: SignUpContract.State,
    onEvent: (SignUpContract.Event) -> Unit,
) {
    SignUpTermStep(
        uiState = uiState,
        onNickNameChange = { onEvent(SignUpContract.Event.OnNickNameChanged(it)) },
        onAgreeRealNameChange = { onEvent(SignUpContract.Event.OnAgreeRealNameChanged(it)) },
        onAgreeAllChange = { onEvent(SignUpContract.Event.OnAgreeAllChanged(it)) },
        onAgreeTermsChange = { onEvent(SignUpContract.Event.OnAgreeTermsChanged(it)) },
        onAgreePrivacyChange = { onEvent(SignUpContract.Event.OnAgreePrivacyChanged(it)) },
        onAgreeMarketingChange = { onEvent(SignUpContract.Event.OnAgreeMarketingChanged(it)) },
        onTermContentClick = { term ->
            onEvent(SignUpContract.Event.OnSelectedTermChanged(term))
        }
    )
}

@Composable
fun SignUpTermStep(
    uiState: SignUpContract.State,
    onNickNameChange: (String) -> Unit,
    onAgreeRealNameChange: (Boolean) -> Unit,
    onAgreeAllChange: (Boolean) -> Unit,
    onAgreeTermsChange: (Boolean) -> Unit,
    onAgreePrivacyChange: (Boolean) -> Unit,
    onAgreeMarketingChange: (Boolean) -> Unit,
    onTermContentClick: (Term) -> Unit,
) {
    val agreeAll = uiState.agreeTerms && uiState.agreePrivacy && uiState.agreeMarketing
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
                value = uiState.nickName.value,
                onValueChange = onNickNameChange,
                placeholder = "닉네임",
                textStyle = AppTypography.sub_regular_16,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                modifier = Modifier.fillMaxWidth(),
            )

            CheckBoxRow(
                title = "실명으로 불러주세요!",
                checked = uiState.agreeRealName,
                onCheckedChange = onAgreeRealNameChange
            )
            Spacer(Modifier.weight(1f))

            CheckBoxRow(
                title = "전체 동의",
                checked = agreeAll,
                onCheckedChange = onAgreeAllChange
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
                    checked = uiState.agreeTerms,
                    onCheckedChange = onAgreeTermsChange,
                    trailingContent = {
                        TextButton(
                            modifier = Modifier.size(65.dp, 21.dp),
                            contentPadding = PaddingValues(0.dp),
                            onClick = { onTermContentClick(Term.Service) },
                        ) {
                            Text("내용 보기", style = AppTypography.body_regular_14, color = Gray.gray_500)
                        }
                    }
                )

                CheckBoxRow(
                    title = "개인정보 수집 및 이용 동의(필수)",
                    checked = uiState.agreePrivacy,
                    onCheckedChange = onAgreePrivacyChange,
                    trailingContent = {
                        TextButton(
                            modifier = Modifier.size(65.dp, 21.dp),
                            contentPadding = PaddingValues(0.dp),
                            onClick = { onTermContentClick(Term.Privacy) },
                        ) {
                            Text("내용 보기", style = AppTypography.body_regular_14, color = Gray.gray_500)
                        }
                    }
                )

                CheckBoxRow(
                    title = "마케팅 정보 수신 동의(선택)",
                    checked = uiState.agreeMarketing,
                    onCheckedChange = onAgreeMarketingChange,
                    trailingContent = {
                        TextButton(
                            modifier = Modifier.size(65.dp, 21.dp),
                            contentPadding = PaddingValues(0.dp),
                            onClick = { onTermContentClick(Term.Marketing) },
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
private fun SignUpTermStepPreview() {
    // Preview에서는 viewModel() 쓰면 환경에 따라 터질 수 있어서 간단히 직접 생성

    val state = SignUpContract.State(
        nickName = TextInputField(value = "미루니"),
        agreeRealName = true,
        agreeTerms = true,
        agreePrivacy = false,
        agreeMarketing = true,
    )

    SignUpTermStep(
        uiState = state,
        onNickNameChange = {},
        onAgreeRealNameChange = {},
        onAgreeAllChange = {},
        onAgreeTermsChange = {},
        onAgreePrivacyChange = {},
        onAgreeMarketingChange = {},
        onTermContentClick = {},
    )
}
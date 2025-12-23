package com.miruni.feature.login

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.miruni.core.designsystem.AppTypography
import com.miruni.core.designsystem.Black
import com.miruni.core.designsystem.Gray
import com.miruni.core.designsystem.MainColor
import com.miruni.core.designsystem.MiruniTheme
import com.miruni.core.designsystem.White
import com.miruni.core.designsystem.Yellow
import com.miruni.feature.login.component.MiruniOutlinedTextField
import com.miruni.feature.login.extension.noRippleClickable
import com.miruni.feature.login.model.TextInputField
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginRoute(
    onLoginSuccess: () -> Unit,
    onSignUpClick: () -> Unit,
    onResetPasswordClick: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val uiState = viewModel.viewState.value
    val onEvent: (LoginContract.Event) -> Unit = viewModel::setEvent

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is LoginContract.Effect.Navigation.ToHome -> onLoginSuccess()
                is LoginContract.Effect.Navigation.ToSignUp -> onSignUpClick()
                is LoginContract.Effect.Navigation.ToResetPassword -> onResetPasswordClick()

                is LoginContract.Effect.Message.Toast -> {
                }
            }
        }
    }

    LoginScreen(
        uiState = uiState,
        onIdChange = { onEvent(LoginContract.Event.OnIdChanged(it)) },
        onPwChange = { onEvent(LoginContract.Event.OnPwChanged(it)) },
        onTogglePasswordVisible = { onEvent(LoginContract.Event.OnTogglePasswordVisible) },
        onAutoLoginChange = { onEvent(LoginContract.Event.OnAutoLoginChanged(it)) },
        onClearError = { onEvent(LoginContract.Event.OnClearError) },
        onLoginClick = { onEvent(LoginContract.Event.OnLoginClicked) },
        onSignUpClick = { onEvent(LoginContract.Event.OnSignUpClicked) },
        onResetPasswordClick = { onEvent(LoginContract.Event.OnResetPasswordClicked) },
        onGoogleLoginClick = { /*TODO */ },
        onKakaoLoginClick = { /* TODO */ },
    )
}

@Composable
fun LoginScreen(
    uiState: LoginContract.State,
    onIdChange: (String) -> Unit,
    onPwChange: (String) -> Unit,
    onTogglePasswordVisible: () -> Unit,
    onAutoLoginChange: (Boolean) -> Unit,
    onClearError: () -> Unit,
    onLoginClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onResetPasswordClick: () -> Unit,
    onGoogleLoginClick: () -> Unit,
    onKakaoLoginClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
    ) {
        // 상단 로고 영역
        Row(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.ic_miruni),
                contentDescription = "logo",
            )
            Spacer(Modifier.width(15.dp))
            Image(
                painter = painterResource(R.drawable.tx_miruni),
                contentDescription = "logo",
            )
        }

        // 입력 영역
        Column(modifier = Modifier.weight(3f)) {

            // 이메일 라벨
            Text(
                text = "이메일",
                style = AppTypography.body_regular_14,
                color = Gray.gray_700,
                modifier = Modifier.padding(start = 5.dp, bottom = 2.dp)
            )

            // 이메일 입력
            MiruniOutlinedTextField(
                value = uiState.id.value,
                onValueChange = onIdChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(76.dp),
                singleLine = true,
                isError = uiState.id.isError,
                supportingText = {
                    uiState.id.errorMessage?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { }),
            )

            // 비밀번호 라벨
            Text(
                text = "비밀번호",
                style = AppTypography.body_regular_14,
                color = Gray.gray_700,
                modifier = Modifier.padding(start = 5.dp, bottom = 2.dp)
            )

            // 비밀번호 visualTransformation
            val pwVisualTransformation =
                if (uiState.passwordVisible) VisualTransformation.None
                else PasswordVisualTransformation()

            // 비밀번호 입력
            MiruniOutlinedTextField(
                value = uiState.password.value,
                onValueChange = onPwChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(88.dp),
                singleLine = true,
                visualTransformation = pwVisualTransformation,
                trailingIcon = {
                    val (icon, description) =
                        if (uiState.passwordVisible) R.drawable.ic_ep to "비밀번호 숨기기"
                        else R.drawable.ic_ep_hide to "비밀번호 보기"

                    IconButton(onClick = onTogglePasswordVisible) {
                        Icon(
                            painter = painterResource(icon),
                            contentDescription = description,
                            tint = Gray.gray_500
                        )
                    }
                },
                isError = uiState.password.isError,
                supportingText = {
                    if (uiState.password.isError) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Info,
                                contentDescription = "error",
                                tint = MaterialTheme.colorScheme.error
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = uiState.password.errorMessage ?: "",
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { onLoginClick() }),
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 3.dp)
                    .noRippleClickable { onClearError() },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides 0.dp) {
                    Checkbox(
                        checked = uiState.autoLogin,
                        onCheckedChange = onAutoLoginChange,
                        colors = CheckboxDefaults.colors(
                            checkedColor = MainColor.miruni_green
                        )
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "자동 로그인",
                    style = AppTypography.body_regular_14,
                )
            }

            Spacer(modifier = Modifier.height(42.dp))

            // 로그인 버튼
            Button(
                onClick = onLoginClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(49.dp),
                enabled = uiState.canLogin,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MainColor.miruni_green,
                    contentColor = White
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("로그인")
            }

            Spacer(modifier = Modifier.height(10.dp))

            // 회원가입 / 비밀번호 변경
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                TextButton(onClick = onSignUpClick) {
                    Text(
                        text = "회원가입",
                        style = AppTypography.body_regular_12,
                        color = Gray.gray_500
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                TextButton(onClick = onResetPasswordClick) {
                    Text(
                        text = "비밀번호 변경",
                        style = AppTypography.body_regular_12,
                        color = Gray.gray_500
                    )
                }
            }

            Spacer(modifier = Modifier.height(50.dp))

            // 구분선
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    color = Black.copy(alpha = 0.25f),
                    thickness = 1.dp
                )
                Text(
                    text = "또는",
                    style = AppTypography.body_regular_12,
                    color = Black.copy(alpha = 0.25f),
                    modifier = Modifier.padding(horizontal = 10.dp)
                )
                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    color = Black.copy(alpha = 0.25f),
                    thickness = 1.dp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 구글 로그인 버튼
            Button(
                onClick = onGoogleLoginClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Gray.gray_300,
                    contentColor = Black
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_google),
                        contentDescription = "google",
                    )
                    Text("구글 로그인")
                    Box {}
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 카카오 로그인 버튼
            Button(
                onClick = onKakaoLoginClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Yellow.kakao,
                    contentColor = Black
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_kakao),
                        contentDescription = "kakao",
                    )
                    Text("카카오 로그인")
                    Box {}
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LoginScreenPreview() {
    val previewState = LoginContract.State(
        id = TextInputField(value = "test@miruni.com"),
        password = TextInputField(value = "password123"),
        passwordVisible = false,
        autoLogin = false,
        isLoading = false
    )

    MiruniTheme {
        LoginScreen(
            uiState = previewState,
            onIdChange = {},
            onPwChange = {},
            onTogglePasswordVisible = {},
            onAutoLoginChange = {},
            onClearError = {},
            onLoginClick = {},
            onSignUpClick = {},
            onResetPasswordClick = {},
            onGoogleLoginClick = {},
            onKakaoLoginClick = {},
        )
    }
}

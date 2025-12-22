package com.miruni.feature.signup.component.step

import android.util.Patterns
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.miruni.core.designsystem.AppTypography
import com.miruni.core.designsystem.MainColor
import com.miruni.core.designsystem.White
import com.miruni.feature.signup.R
import com.miruni.feature.signup.SignUpContract
import com.miruni.feature.signup.SignupViewModel
import com.miruni.feature.signup.component.textfield.UnderlineTextField

@Composable
fun SignUpAccountStepRoute(
    uiState: SignUpContract.State,
    viewModel: SignupViewModel = viewModel(),
) {
    SignUpAccountStep(
        uiState = uiState,
        onEmailChange = { viewModel.setEvent(SignUpContract.Event.OnEmailChanged(it)) },
        onOtpChange = { viewModel.setEvent(SignUpContract.Event.OnOtpChanged(it)) },
        onPasswordChange = { viewModel.setEvent(SignUpContract.Event.OnPasswordChanged(it)) },
        onPasswordCheckChange = { viewModel.setEvent(SignUpContract.Event.OnPasswordCheckChanged(it)) },

        onRequestOtp = { /* TODO: 이벤트 연결 */ },
        onVerifyOtp = { /* TODO: 이벤트 연결 */ },
    )
}

@Composable
fun SignUpAccountStep(
    uiState: SignUpContract.State,
    onEmailChange: (String) -> Unit,
    onOtpChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordCheckChange: (String) -> Unit,
    onRequestOtp: () -> Unit,
    onVerifyOtp: () -> Unit,
) {
    val passwordVisible = false
    val pwTransformation =
        if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()

    val btnSize = 70.dp to 30.dp
    val email = uiState.email.value
    val otp = uiState.otp.value
    val password = uiState.password.value
    val passwordCheck = uiState.passwordCheck.value

    val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    val isPasswordMismatch =
        password.isNotBlank() && passwordCheck.isNotBlank() && password != passwordCheck

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp),
        verticalArrangement = Arrangement.spacedBy(22.dp)
    ) {
        UnderlineTextField(
            value = email,
            onValueChange = onEmailChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = "이메일을 입력해주세요.",
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Email
            ),
            leading = {
                Icon(
                    painter = painterResource(R.drawable.ic_person),
                    contentDescription = "email",
                )
            },
            trailing = {
                Button(
                    modifier = Modifier.size(btnSize.first, btnSize.second),
                    contentPadding = PaddingValues(vertical = 8.dp, horizontal = 0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MainColor.miruni_green,
                        contentColor = White
                    ),
                    shape = RoundedCornerShape(2.dp),
                    enabled = isEmailValid,
                    onClick = onRequestOtp,
                ) {
                    Text("인증코드 받기", style = AppTypography.button_regular_9)
                }
            },
        )

        UnderlineTextField(
            value = otp,
            onValueChange = onOtpChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = "인증코드를 입력해주세요.",
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            trailing = {
                Button(
                    modifier = Modifier.size(btnSize.first, btnSize.second),
                    contentPadding = PaddingValues(vertical = 8.dp, horizontal = 0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MainColor.miruni_green,
                        contentColor = White
                    ),
                    shape = RoundedCornerShape(2.dp),
                    enabled = otp.isNotBlank(),
                    onClick = onVerifyOtp,
                ) {
                    Text("인증하기", style = AppTypography.button_regular_9)
                }
            }
        )

        UnderlineTextField(
            value = password,
            onValueChange = onPasswordChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = "비밀번호를 입력해주세요.",
            visualTransformation = pwTransformation,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Password
            ),
            leading = {
                Icon(
                    painter = painterResource(R.drawable.ic_mingcute_lock_fill),
                    contentDescription = "password",
                )
            },
            supportingText = {
                Text(
                    "영문, 숫자를 포함해서 8자 이상으로 설정해주세요.",
                    style = AppTypography.body_regular_12
                )
            },
        )

        UnderlineTextField(
            value = passwordCheck,
            onValueChange = onPasswordCheckChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = "비밀번호를 다시 입력해주세요.",
            visualTransformation = pwTransformation,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            leading = {
                Icon(
                    painter = painterResource(R.drawable.ic_mingcute_lock_fill),
                    contentDescription = "password",
                )
            },
            isError = isPasswordMismatch,
            supportingText = {
                if (isPasswordMismatch) {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = "error",
                        )
                        Spacer(Modifier.size(8.dp))
                        Text(
                            "비밀 번호가 일치하지 않습니다",
                            style = AppTypography.body_regular_12
                        )
                    }
                }
            },
        )

        Spacer(Modifier.height(8.dp))
    }
}

package com.miruni.signup.component.step

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
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.miruni.designsystem.AppTypography
import com.miruni.designsystem.MainColor
import com.miruni.designsystem.White
import com.miruni.feature.signup.R
import com.miruni.signup.SignUpViewModel
import com.miruni.signup.component.textfield.UnderlineTextField

@Composable
fun SignUpAccountStep(
    onPrevStep: () -> Unit,
    onNextStep: () -> Unit,
    viewModel: SignUpViewModel = viewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val emailState: TextFieldState = rememberTextFieldState(uiState.email)
    val otpState: TextFieldState = rememberTextFieldState(uiState.otp)
    val passwordState: TextFieldState = rememberTextFieldState(uiState.password)
    val passwordCheckState: TextFieldState = rememberTextFieldState(uiState.passwordCheck)

    val passwordVisible = false // 필요하면 토글 상태로 확장 가능

    val pwTransformation =
        if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()

    val btnSize = Pair(70.dp, 30.dp)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp),
        verticalArrangement = Arrangement.spacedBy(22.dp)
    ) {
        UnderlineTextField(
            state = emailState,
            modifier = Modifier.fillMaxWidth(),
            placeholder = "이메일을 입력해주세요.",
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Email
            ),
            onTextChange = viewModel::updateEmail,
            leading = {
                Icon(
                    painter = painterResource(R.drawable.ic_person),
                    contentDescription = "email",
                )
            },
            trailing = {
                Button(
                    modifier = Modifier
                        .size(btnSize.first, btnSize.second),
                    contentPadding = PaddingValues(vertical = 8.dp, horizontal = 0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MainColor.miruni_green,
                        contentColor = White
                    ),
                    shape = RoundedCornerShape(2.dp),
                    enabled = Patterns.EMAIL_ADDRESS.matcher(uiState.email).matches(),
                    onClick = {},
                ) {
                    Text("인증코드 받기", style = AppTypography.button_regular_9)
                }
            },
        )

        UnderlineTextField(
            state = otpState,
            modifier = Modifier.fillMaxWidth(),
            placeholder = "인증코드를 입력해주세요.",
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
            ),
            onTextChange = { text ->
                viewModel.updateOtp(text)
            },
            trailing = {
                Button(
                    modifier = Modifier
                        .size(btnSize.first, btnSize.second),
                    contentPadding = PaddingValues(vertical = 8.dp, horizontal = 0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MainColor.miruni_green,
                        contentColor = White
                    ),
                    shape = RoundedCornerShape(2.dp),
                    enabled = false,
                    onClick = {},
                ) {
                    Text("인증하기", style = AppTypography.button_regular_9)
                }
            }
        )

        UnderlineTextField(
            state = passwordState,
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
            onTextChange = viewModel::updatePassword,

        )

        UnderlineTextField(
            state = passwordCheckState,
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
            isError = true,
            supportingText = {
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
            },
            onTextChange = viewModel::updatePasswordCheck
        )

        Spacer(Modifier.height(8.dp))
    }
}

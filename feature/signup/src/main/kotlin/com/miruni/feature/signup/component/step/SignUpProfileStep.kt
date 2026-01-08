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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.miruni.core.designsystem.AppTypography
import com.miruni.core.designsystem.MainColor
import com.miruni.core.designsystem.White
import com.miruni.feature.signup.R
import com.miruni.feature.signup.component.textfield.BirthDateVisualTransformation
import com.miruni.feature.signup.component.textfield.PhoneNumberVisualTransformation
import com.miruni.feature.signup.component.textfield.UnderlineTextField
import com.miruni.feature.signup.utils.MiruniSize
import com.miruni.feature.signup.utils.MiruniSpacing

@Composable
fun SignUpProfileStep(
    name: String,
    birth: String,
    phone: String,
    email: String,
    otp: String,
    password: String,
    passwordCheck: String,
    onNameChange: (String) -> Unit,
    onBirthChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onOtpChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordCheckChange: (String) -> Unit,
    onRequestOtp: () -> Unit,
    onVerifyOtp: () -> Unit,
) {
    val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    val isPasswordMismatch =
        password.isNotBlank() && passwordCheck.isNotBlank() && password != passwordCheck

    val passwordVisible = false
    val pwTransformation: VisualTransformation =
        if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()

    val scrollState = rememberScrollState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = MiruniSpacing.topPadding)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(MiruniSpacing.xxl)
    ) {
        UnderlineTextField(
            value = name,
            onValueChange = onNameChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = "이름을 입력해주세요.",
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        )

        UnderlineTextField(
            value = birth,
            onValueChange = onBirthChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = "생년월일 8자리를 입력해주세요.",
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Number
            ),
            visualTransformation = BirthDateVisualTransformation(),
        )

        UnderlineTextField(
            value = phone,
            onValueChange = onPhoneChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = "전화번호 11자리를 입력해주세요.",
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Phone
            ),
            visualTransformation = PhoneNumberVisualTransformation(),
        )

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
                    modifier = Modifier.size(MiruniSize.buttonWidth, MiruniSize.buttonHeight),
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
                    modifier = Modifier.size(MiruniSize.buttonWidth, MiruniSize.buttonHeight),
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

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SignUpProfileStepPreview() {

    SignUpProfileStep(
        name = "",
        birth = "",
        phone = "",
        email = "",
        otp = "",
        password = "",
        passwordCheck = "",
        onNameChange = {},
        onBirthChange = {},
        onPhoneChange = {},
        onEmailChange = {},
        onOtpChange = {},
        onPasswordChange = {},
        onPasswordCheckChange = {},
        onRequestOtp = {},
        onVerifyOtp = {},
    )
}
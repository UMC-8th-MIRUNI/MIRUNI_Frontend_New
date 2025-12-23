package com.miruni.feature.signup.component.step

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.miruni.feature.signup.SignUpContract
import com.miruni.feature.signup.SignupViewModel
import com.miruni.feature.signup.component.textfield.BirthDateVisualTransformation
import com.miruni.feature.signup.component.textfield.PhoneNumberVisualTransformation
import com.miruni.feature.signup.component.textfield.UnderlineTextField

@Composable
fun SignUpProfileStepRoute(
    uiState: SignUpContract.State,
    onEvent: (SignUpContract.Event) -> Unit,
) {
    SignUpProfileStep(
        uiState = uiState,
        onNameChange = { onEvent(SignUpContract.Event.OnNameChanged(it)) },
        onBirthChange = { onEvent(SignUpContract.Event.OnBirthChanged(it)) },
        onPhoneChange = { onEvent(SignUpContract.Event.OnPhoneChanged(it)) },
    )
}

@Composable
fun SignUpProfileStep(
    uiState: SignUpContract.State,
    onNameChange: (String) -> Unit,
    onBirthChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        UnderlineTextField(
            value = uiState.name.value,
            onValueChange = onNameChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = "이름을 입력해주세요.",
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        )

        UnderlineTextField(
            value = uiState.birth.value,
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
            value = uiState.phone.value,
            onValueChange = onPhoneChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = "전화번호 11자리를 입력해주세요.",
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Phone
            ),
            visualTransformation = PhoneNumberVisualTransformation(),
        )

        Spacer(Modifier.height(24.dp))
    }
}

package com.miruni.feature.signup.component.step

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.miruni.signup.SignUpViewModel
import com.miruni.signup.component.textfield.UnderlineTextField

@Composable
fun SignUpProfileStep(
    onPrevStep: () -> Unit,
    onNextStep: () -> Unit,
    viewModel: SignUpViewModel = viewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    val nameState: TextFieldState = rememberTextFieldState(uiState.name)
    val birthState: TextFieldState = rememberTextFieldState(uiState.birth)
    val phoneState: TextFieldState = rememberTextFieldState(uiState.phone)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        UnderlineTextField(
            state = nameState,
            modifier = Modifier.fillMaxWidth(),
            placeholder = "이름을 입력해주세요.",
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            onTextChange = { text ->
                viewModel.updateName(text)
            }
        )

        UnderlineTextField(
            state = birthState,
            modifier = Modifier.fillMaxWidth(),
            placeholder = "생년월일 8자리를 입력해주세요.",
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Number
            ),
            onTextChange = { text ->
                val digits = text.filter { it.isDigit() }
                viewModel.updateBirth(digits)
            },
            visualTransformation = BirthDateVisualTransformation()
        )

        UnderlineTextField(
            state = phoneState,
            modifier = Modifier.fillMaxWidth(),
            placeholder = "전화번호 11자리를 입력해주세요.",
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Phone
            ),
            onTextChange = { text ->
                viewModel.updatePhone(text)
            },
            visualTransformation = PhoneNumberVisualTransformation()
        )

        Spacer(Modifier.height(24.dp))

        // 디버깅용: UiState에 실제로 잘 들어가는지 확인
        Text(
            text = "UiState.name: ${uiState.name}",
            color = Color.White,
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = "UiState.birth: ${uiState.birth}",
            color = Color.White,
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = "UiState.phone: ${uiState.phone}",
            color = Color.White,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

class BirthDateVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val raw = text.text.filter { it.isDigit() }.take(8)

        val out = buildString {
            for (i in raw.indices) {
                append(raw[i])
                if (i == 3 || i == 5) {
                    append('.')
                }
            }
        }

        // 아주 단순한 매핑 (YYYY.MM.DD 기준)
        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                // raw index -> transformed index
                return when {
                    offset <= 4 -> offset
                    offset <= 6 -> offset + 1   // 4자리 넘어가면 '.' 하나 추가
                    else -> offset + 2          // '.' 두 개
                }.coerceAtMost(out.length)
            }

            override fun transformedToOriginal(offset: Int): Int {
                // transformed index -> raw index
                return when {
                    offset <= 4 -> offset
                    offset <= 7 -> offset - 1
                    else -> offset - 2
                }.coerceAtMost(raw.length)
            }
        }

        return TransformedText(AnnotatedString(out), offsetMapping)
    }
}

class PhoneNumberVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val raw = text.text.filter { it.isDigit() }.take(11) // 010xxxxxxxx 최대 11

        val out = buildString {
            for (i in raw.indices) {
                append(raw[i])
                // 010-xxxx-xxxx (11자리) / 010-xxx-xxxx (10자리)
                val shouldDash = when {
                    raw.length <= 3 -> false
                    raw.length <= 7 -> (i == 2) // 3-나머지 (010-1234 까지)
                    raw.length == 10 -> (i == 2 || i == 5) // 3-3-4
                    else -> (i == 2 || i == 6) // 3-4-4 (11자리)
                }
                if (shouldDash && i != raw.lastIndex) append('-')
            }
        }

        val dashCountBefore = { offset: Int ->
            // offset: raw 기준 커서 위치(0..raw.length)
            when {
                raw.length <= 3 -> 0
                raw.length <= 7 -> if (offset > 3) 1 else 0
                raw.length == 10 -> {
                    when {
                        offset <= 3 -> 0
                        offset <= 6 -> 1
                        else -> 2
                    }
                }
                else -> { // 11자리
                    when {
                        offset <= 3 -> 0
                        offset <= 7 -> 1
                        else -> 2
                    }
                }
            }
        }

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                val o = offset.coerceIn(0, raw.length)
                val add = dashCountBefore(o)
                return (o + add).coerceAtMost(out.length)
            }

            override fun transformedToOriginal(offset: Int): Int {
                val t = offset.coerceIn(0, out.length)
                // transformed -> original: 대시 개수를 빼주되, 구간별로 안전하게
                // 간단하게 "t에서 '-' 개수를 센 만큼 빼기"를 하면 안정적
                val dashes = out.take(t).count { it == '-' }
                return (t - dashes).coerceIn(0, raw.length)
            }
        }

        return TransformedText(AnnotatedString(out), offsetMapping)
    }
}
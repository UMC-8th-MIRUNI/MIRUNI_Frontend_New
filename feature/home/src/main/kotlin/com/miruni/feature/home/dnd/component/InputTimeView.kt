package com.miruni.feature.home.dnd.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.miruni.core.designsystem.MainColor
import com.miruni.core.designsystem.MiruniTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputTimeView(
    timePickerState: TimePickerState,
    isTimeConfirmed: Boolean,
) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            MaterialTimeInputNoLabel(
                state = timePickerState,
                isTimeConfirmed = isTimeConfirmed
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaterialTimeInputNoLabel(
    state: TimePickerState,
    isTimeConfirmed: Boolean
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        TimeNumberField(
            value = state.hour,
            range = 0..23,
            isTimeConfirmed = isTimeConfirmed,
            onValueChange = { state.hour = it },
        )

        Text(
            ":",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(horizontal = 12.dp)
        )

        TimeNumberField(
            value = state.minute,
            range = 0..59,
            isTimeConfirmed = isTimeConfirmed,
            onValueChange = { state.minute = it },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TimeNumberField(
    value: Int,
    range: IntRange,
    isTimeConfirmed: Boolean,
    onValueChange: (Int) -> Unit
) {
    var text by remember(value) {
        mutableStateOf(value.toString().padStart(2, '0'))
    }

    OutlinedTextField(
        value = text,
        onValueChange = { new ->
            if (isTimeConfirmed) return@OutlinedTextField  // 확정 후 입력 막기
            // 1) 빈 값 → 대기
            if (new.isBlank()) {
                text = ""
                return@OutlinedTextField
            }
            // 2) 숫자가 아닌 경우 → 00
            if (new.any { !it.isDigit() }) {
                text = "00"
                onValueChange(range.first)
                return@OutlinedTextField
            }

            // 3) 두 자리 이상 입력 시 자르기
            val trimmed = new.take(2)
            val numericValue = trimmed.toIntOrNull() ?: 0

            // 4) 범위 검사 후 처리
            if (numericValue in range) {
//                text = trimmed
                text = new
                onValueChange(numericValue)
            } else {
                text = "00"
                onValueChange(numericValue)
            }

            // 두 자리 미만이면 그대로 ("0", "1" 등)
            if (trimmed.length == 1) {
                text = trimmed
            }
        },
        shape = RoundedCornerShape(10.dp),
        enabled = !isTimeConfirmed,
        singleLine = true,
        textStyle = MiruniTypography.displayMedium.copy(
            textAlign = TextAlign.Center,
            color = if (isTimeConfirmed)
                MainColor.miruni_green
            else
                MaterialTheme.colorScheme.onSurface
        ),
        modifier = Modifier
            .height(86.dp)
            .width(111.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = MainColor.miruni_green,
            unfocusedIndicatorColor = Color.Transparent,

            focusedContainerColor = if (isTimeConfirmed)
                Color.Transparent
            else
                Color(0x2A24C354),

            unfocusedContainerColor = if (isTimeConfirmed)
                Color.Transparent
            else
                MaterialTheme.colorScheme.surface,

            disabledContainerColor = MaterialTheme.colorScheme.surface,
            cursorColor = MaterialTheme.colorScheme.primary,
        ),
        label = null,
        suffix = {},
    )
}
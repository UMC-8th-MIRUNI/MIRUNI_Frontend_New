package com.miruni.feature.home.dnd.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.rememberTimePickerState
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputTimeView(
    timePickerState: TimePickerState
) {
    val timePickerState = rememberTimePickerState(
        is24Hour = true
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        MaterialTimeInputNoLabel(state = timePickerState)

        Spacer(
            Modifier.height(60.dp)
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaterialTimeInputNoLabel(
    state: TimePickerState,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        TimeNumberField(
            value = state.hour,
            range = 0..23,
            onValueChange = { state.hour = it }
        )

        Text(
            ":",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(horizontal = 12.dp)
        )

        TimeNumberField(
            value = state.minute,
            range = 0..59,
            onValueChange = { state.minute = it }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TimeNumberField(
    value: Int,
    range: IntRange,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = { new ->
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
                text = trimmed
                onValueChange(range.first)
            } else {
                text = "00"
                onValueChange(range.first)
            }

            // 두 자리 미만이면 그대로 ("0", "1" 등)
            if (trimmed.length == 1) {
                text = trimmed
            }
        },
        shape = RoundedCornerShape(10.dp),
        singleLine = true,
        textStyle = MaterialTheme.typography.headlineMedium.copy(
            textAlign = TextAlign.Center
        ),
        modifier = Modifier
            .width(80.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = MainColor.miruni_green,
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = Color(0x2A24C354),
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            disabledContainerColor = MaterialTheme.colorScheme.surface,
            cursorColor = MaterialTheme.colorScheme.primary
        ),
        label = null,
        suffix = {},
    )
}

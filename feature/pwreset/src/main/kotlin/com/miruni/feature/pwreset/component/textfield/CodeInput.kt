package com.miruni.feature.pwreset.component.textfield

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.miruni.core.designsystem.AppTypography
import com.miruni.core.designsystem.Gray
import com.miruni.core.designsystem.MainColor


@Composable
fun CodeInput(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    length: Int = 4,
    boxWidth: Dp = 72.dp,
    boxHeight: Dp = 77.dp,
    boxSpacing: Dp = 24.dp,
    boxCornerRadius: Dp = 15.dp,
    enabled: Boolean = true,
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    BasicTextField(
        value = value,
        onValueChange = { raw ->
            // 숫자만, 최대 length
            val filtered = raw.filter { it.isDigit() }.take(length)
            onValueChange(filtered)

            // 다 채우면 키보드 내리기(원하면 제거)
            if (filtered.length == length) focusManager.clearFocus()
        },
        enabled = enabled,
        modifier = modifier
            .focusRequester(focusRequester)
            .clickable(enabled = enabled) { focusRequester.requestFocus() },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus() }
        ),
        singleLine = true,
        // 커서/텍스트를 숨기고 UI는 decorationBox로 그립니다
        textStyle = LocalTextStyle.current.copy(color = Color.Transparent),
        cursorBrush = SolidColor(Color.Transparent),
        decorationBox = { innerTextField ->
            Box {
                // 실제 입력을 받는 필드(보이지 않음)
                innerTextField()
                Row(horizontalArrangement = Arrangement.spacedBy(boxSpacing)) {
                    repeat(length) { index ->
                        val digit = value.getOrNull(index)?.toString().orEmpty()
                        val isActiveCell = value.length == index

                        Box(
                            modifier = Modifier
                                .size(width = boxWidth, height = boxHeight)
                                .border(
                                    width = 1.dp,
                                    color = if (isActiveCell) MainColor.miruni_green else Gray.gray_500,
                                    shape = RoundedCornerShape(boxCornerRadius)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = digit,
                                style = AppTypography.sub_bold_14,
                            )
                        }
                    }
                }
            }
        }
    )
}

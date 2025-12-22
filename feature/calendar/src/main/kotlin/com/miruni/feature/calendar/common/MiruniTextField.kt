package com.miruni.feature.calendar.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.miruni.core.designsystem.AppTypography
import com.miruni.core.designsystem.Black
import com.miruni.core.designsystem.Gray

object MiruniTextField {
    @Composable
    fun InputText(
        value: String,
        onValueChange: (String) -> Unit,
        modifier: Modifier = Modifier,
        singleLine: Boolean = true,
        maxLines: Int = 1,
        enabled: Boolean = true,
        textColor: Color = Color.Black,
        placeholder: String? = null,
        focusRequester: FocusRequester? = null,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
        trailingIcon: (@Composable () -> Unit)? = null,
    ) {
        val roundedShape = RoundedCornerShape(10.dp)

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier
                .fillMaxWidth()
                .then(
                    if (singleLine) Modifier.height(41.dp)
                    else Modifier.height(124.dp)
                )
                .background(Color.White, roundedShape)
                .border(
                    width = 1.dp,
                    color = Gray.gray_500,
                    shape = roundedShape
                ).let {
                    if (focusRequester != null) {
                        it.focusRequester(focusRequester)
                    } else {
                        it
                    }
                },
            enabled = enabled,
            maxLines = maxLines,
            singleLine = singleLine,
            textStyle = AppTypography.body_regular_14.copy(color = textColor),
            interactionSource = interactionSource,
            decorationBox = { inner ->
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                            .padding(horizontal = 16.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        inner()
                    }

                    if (trailingIcon != null) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "calendar",
                            tint = Gray.gray_500,
                            modifier = Modifier
                                .padding(end = 12.dp)
                                .size(20.dp)
                        )
                    }
                }
            }
        )
    }
}

@Composable
@Preview
fun InputTextFieldPreview() {
    var text1 by remember { mutableStateOf("설명") }
    var text2 by remember { mutableStateOf("설명2") }


    Column(
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 100.dp),
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(16.dp)
    ) {
        MiruniTextField.InputText(
            value = text1,
            onValueChange = { text1 = it },
        )

        MiruniTextField.InputText(
            value = text2,
            onValueChange = { text1 = it },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null,
                    tint = Gray.gray_500,
                    modifier = Modifier.size(20.dp)
                )
            }
        )
    }

}
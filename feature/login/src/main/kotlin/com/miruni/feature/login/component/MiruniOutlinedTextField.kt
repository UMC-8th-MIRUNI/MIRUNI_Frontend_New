package com.miruni.feature.login.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.miruni.core.designsystem.AppTypography
import com.miruni.core.designsystem.MainColor
import com.miruni.core.designsystem.White

@Composable
fun MiruniOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    textFieldModifier: Modifier = Modifier.fillMaxWidth(),
    textStyle: TextStyle = AppTypography.body_regular_14,
    singleLine: Boolean = true,
    enabled: Boolean = true,
    isError: Boolean = false,
    leadingIcon: (@Composable (() -> Unit))? = null,
    trailingIcon: (@Composable (() -> Unit))? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    supportingText: (@Composable (() -> Unit))? = null,
    shape: Shape = RoundedCornerShape(10.dp),
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = MainColor.input_focus,
        unfocusedContainerColor = White,
        focusedBorderColor = MainColor.miruni_green,
        unfocusedBorderColor = MainColor.miruni_green,
    ),
) {
    Column(
        modifier = modifier
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = singleLine,
            enabled = enabled,
            modifier = textFieldModifier,
            textStyle = textStyle,
            colors = colors,
            shape = shape,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            visualTransformation = visualTransformation,
            isError = isError,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
        )

        // 위·아래 간격 조절용
        Spacer(modifier = Modifier.height(2.dp))
        supportingText?.invoke()
    }
}

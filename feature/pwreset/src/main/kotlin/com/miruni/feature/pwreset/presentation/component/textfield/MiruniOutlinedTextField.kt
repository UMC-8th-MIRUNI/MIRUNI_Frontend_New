package com.miruni.feature.pwreset.presentation.component.textfield

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.miruni.core.designsystem.AppTypography
import com.miruni.core.designsystem.MainColor

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
    placeholder: (@Composable (() -> Unit))? = null,
    label: (@Composable (() -> Unit))? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    supportingText: (@Composable (() -> Unit))? = null,
    shape: Shape = RoundedCornerShape(10.dp),
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = MainColor.input_focus,
        unfocusedContainerColor = Color.White,
        focusedBorderColor = MainColor.miruni_green,
        unfocusedBorderColor = MainColor.miruni_green,
    ),
) {
    Column(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier.padding(
                start = 5.dp,
                bottom = 2.dp
            )
        ){
            label?.invoke()
        }
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
            placeholder = placeholder,
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


@Preview(showBackground = true)
@Composable
fun MiruniOutlinedTextFieldPrieview(

) {
    var text1 by remember { mutableStateOf("설명") }
    var text2 by remember { mutableStateOf("설명2") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        MiruniOutlinedTextField(
            value = text1,
            onValueChange = { text1 = it },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    "이메일",
                    style = AppTypography.body_regular_14,
                )
            }

        )
        Spacer(modifier = Modifier.height(16.dp))
        MiruniOutlinedTextField(
            value = text2,
            onValueChange = { text2 = it },
        )
    }
}
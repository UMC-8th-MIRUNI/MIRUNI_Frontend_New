package com.miruni.feature.calendar.common

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.miruni.core.designsystem.AppTypography
import com.miruni.core.designsystem.Gray
import com.miruni.core.designsystem.MainColor
import com.miruni.core.designsystem.White

object MiruniButton {
    @Composable
    fun Single(
        text: String,
        modifier: Modifier = Modifier,
        onClick: () -> Unit,
        enabled: Boolean = true,
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
        ) {
            Filled(
                text = text,
                onClick = onClick,
                enabled = enabled
            )
        }
    }
}

@Composable
fun Filled(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    enabled: Boolean = false,
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        onClick = onClick,
        interactionSource = interactionSource,
        colors = ButtonDefaults.buttonColors(
            containerColor = MainColor.miruni_green,
            disabledContainerColor = Gray.gray_500
        ),
        shape = RoundedCornerShape(10.dp),
        enabled = enabled
    ) {
        Text(
            text = text,
            style = AppTypography.button_semibold_16,
            color = White
        )
    }
}

@Composable
@Preview
fun ButtonPreview() {
    Column(
        modifier = Modifier
            .background(Color.White),
        verticalArrangement = Arrangement.Bottom,
    ) {
        MiruniButton.Single(
            text = "완료",
            onClick = {}
        )
    }
}
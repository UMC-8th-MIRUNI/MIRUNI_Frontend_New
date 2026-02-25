package com.miruni.feature.calendar.presentation.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.miruni.core.designsystem.AppTypography

@Composable
fun EmptySchedule(
    text: String
) {
    Text(
        text = text,
        style = AppTypography.body_regular_12
    )
}
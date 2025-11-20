package com.miruni.core.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

@Composable
fun MiruniTheme(
    content: @Composable () -> Unit
) {
    content()
    // Color scheme
    MaterialTheme(
        colorScheme = lightColorScheme(),
        typography = MiruniTypography,
        content = content
    )
}
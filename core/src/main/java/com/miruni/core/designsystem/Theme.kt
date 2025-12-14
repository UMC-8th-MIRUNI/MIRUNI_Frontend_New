package com.miruni.core.designsystem

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@VisibleForTesting
val LightColorScheme = lightColorScheme(
    primary = MainColor.miruni_green,
    onPrimary = White,
    primaryContainer = MainColor.alpha_50,
    onPrimaryContainer = Black,
    background = Color.White,
    onBackground = Black,
    surface = Gray.background_gray,
    onSurface = Black,
    surfaceVariant = Gray.gray_300,
    onSurfaceVariant = Black
)

data class CustomColorScheme(
    val buttonBackground: Color,
    val buttonBackgroundDisabled: Color,
    val buttonText: Color,
    val buttonTextDisabled: Color,
    val selectedContainer: Color,
    val selectedText: Color,
    val unSelectedContainer: Color,
    val unSelectedText: Color,
    val errorText: Color,

//    val heading: Color,
//    val textFieldBackground: Color,
//    val textFieldText: Color,
//    val confirmText: Color,
//    val spacerLine: Color,
//    val defaultIconColor: Color,
//    val topBarTextButtonTextColor: Color,
//    val dialogContentColor: Color
)

val LightCustomColors = CustomColorScheme(
    buttonBackground = MainColor.miruni_green,
    buttonBackgroundDisabled = Gray.gray_500,
    buttonText = White,
    buttonTextDisabled = White,
    selectedContainer = MainColor.input_focus,
    selectedText = Black,
    unSelectedContainer = Color.White,
    unSelectedText = Black_28,
    errorText = Red
)

val LocalCustomColorScheme = staticCompositionLocalOf<CustomColorScheme> {
    LightCustomColors
}

val MaterialTheme.customColorScheme: CustomColorScheme
    @Composable
    get() = LocalCustomColorScheme.current

@Composable
fun MiruniTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // ColorScheme
    val colorScheme =
//        if (darkTheme) {}
        LightColorScheme
    val customColorScheme =
//        if (darkTheme) {}
        LightCustomColors

    CompositionLocalProvider(
        LocalCustomColorScheme provides customColorScheme
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = MiruniTypography,
            content = content
        )
    }
}
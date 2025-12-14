package com.miruni.core.designsystem

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.miruni.core.R

object AppTypography {
    // 폰트
    internal val Pretendard =
        FontFamily(
            Font(R.font.pretendard_thin, FontWeight.Thin),
            Font(R.font.pretendard_extralight, FontWeight.ExtraLight),
            Font(R.font.pretendard_light, FontWeight.Light),
            Font(R.font.pretendard_regular, FontWeight.Normal),
            Font(R.font.pretendard_medium, FontWeight.Medium),
            Font(R.font.pretendard_semibold, FontWeight.SemiBold),
            Font(R.font.pretendard_bold, FontWeight.Bold),
            Font(R.font.pretendard_extrabold, FontWeight.ExtraBold),
            Font(R.font.pretendard_black, FontWeight.Black)
        )

    val header_bold_16 =
        TextStyle(
            fontFamily = Pretendard,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            letterSpacing = (-0.05f).em,
            lineHeight = (16 * 1.00).sp // 100%
        )
    val header_bold_20 =
        TextStyle(
            fontFamily = Pretendard,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            letterSpacing = (-0.05f).em,
            lineHeight = (20 * 1.50).sp // 150%
        )

    val body_regular_12 =
        TextStyle(
            fontFamily = Pretendard,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            letterSpacing = (-0.05f).em,
            lineHeight = (12 * 1.50).sp // 150%
        )
    val body_regular_14 =
        TextStyle(
            fontFamily = Pretendard,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            letterSpacing = (-0.05f).em,
            lineHeight = (14 * 1.50).sp // 150%
        )

    val sub_semibold_12 =
        TextStyle(
            fontFamily = Pretendard,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
            letterSpacing = (0f).em,
            lineHeight = (12 * 1.00).sp // 100%
        )
    val sub_bold_12 =
        TextStyle(
            fontFamily = Pretendard,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            letterSpacing = (0f).em,
            lineHeight = (12 * 1.00).sp // 100%
        )
    val sub_medium_14 =
        TextStyle(
            fontFamily = Pretendard,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            letterSpacing = (0f).em,
            lineHeight = (14 * 1.00).sp // 100%
        )
    val sub_bold_14 =
        TextStyle(
            fontFamily = Pretendard,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            letterSpacing = (0f).em,
            lineHeight = (14 * 1.00).sp // 100%
        )
    val sub_regular_16 =
        TextStyle(
            fontFamily = Pretendard,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            letterSpacing = (0f).em,
            lineHeight = (16 * 1.00).sp // 100%
        )

    val description_regular_9 =
        TextStyle(
            fontFamily = Pretendard,
            fontWeight = FontWeight.Normal,
            fontSize = 9.sp,
            letterSpacing = (-0.01f).em,
            lineHeight = (9 * 1.50).sp // 150%
        )

    val button_regular_9 =
        TextStyle(
            fontFamily = Pretendard,
            fontWeight = FontWeight.Normal,
            fontSize = 9.sp,
            letterSpacing = (0f).em,
            lineHeight = (9 * 1.00).sp // 100%
        )
    val button_semibold_16 =
        TextStyle(
            fontFamily = Pretendard,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            letterSpacing = (0f).em,
            lineHeight = (16 * 1.00).sp // 100%
        )
}

val MiruniTypography = Typography(
    // Display
    displayLarge = TextStyle(
        fontFamily = AppTypography.Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp
    ),
    displayMedium = TextStyle(
        fontFamily = AppTypography.Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 45.sp,
        lineHeight = 52.sp,
        letterSpacing = (-0.25).sp
    ),
    displaySmall = TextStyle(
        fontFamily = AppTypography.Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = (-0.25).sp
    ),

    // Headline
    headlineLarge = TextStyle(
        fontFamily = AppTypography.Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = (-0.25).sp
    ),
    headlineMedium = TextStyle(
        fontFamily = AppTypography.Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = (-0.25).sp
    ),
    headlineSmall = TextStyle(
        fontFamily = AppTypography.Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = (-0.25).sp
    ),

    // Title
    titleLarge = TextStyle(
        fontFamily = AppTypography.Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = (-0.25).sp
    ),
    titleMedium = TextStyle(
        fontFamily = AppTypography.Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = (-0.25).sp
    ),
    titleSmall = TextStyle(
        fontFamily = AppTypography.Pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = (-0.25).sp
    ),

    // Body
    bodyLarge = TextStyle(
        fontFamily = AppTypography.Pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = (-0.25).sp
    ),
    bodyMedium = TextStyle(
        fontFamily = AppTypography.Pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = (-0.25).sp
    ),
    bodySmall = TextStyle(
        fontFamily = AppTypography.Pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = (-0.25).sp
    ),

    // Label
    labelLarge = TextStyle(
        fontFamily = AppTypography.Pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = (-0.25).sp
    ),
    labelMedium = TextStyle(
        fontFamily = AppTypography.Pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 165.sp,
        letterSpacing = (-0.25).sp
    ),
    labelSmall = TextStyle(
        fontFamily = AppTypography.Pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = (-0.25).sp
    ),
)

fun MaterialTheme.customTypography() : AppTypography = AppTypography
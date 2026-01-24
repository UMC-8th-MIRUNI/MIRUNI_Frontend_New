package com.miruni.feature.onboard.presentation.model

import androidx.compose.ui.graphics.Color

// 온보딩 페이지 데이터 모델
sealed interface OnboardingPageData{
    val backgroundColor: Color

    data class Basic(
        val header: String,
        val body: String,
        val imgRes: Int,
        override val backgroundColor: Color
    ) : OnboardingPageData

    data class Final(
        val header: String,
        val body: String,
        val topImgRes: Int,
        val bottomImgRes: Int,
        override val backgroundColor: Color
    ) : OnboardingPageData
}
package com.miruni.signup.model

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.miruni.designsystem.AppTypography

sealed class Term(
    val key: String,
    val title: String,
    val required: Boolean,
) {
    abstract fun getTermContent() : AnnotatedString
    data object Service : Term(
        key = "service",
        title = "이용약관 동의",
        required = true
    ) {
        override fun getTermContent(): AnnotatedString {
            return buildAnnotatedString {
                withStyle(style = AppTypography.sub_bold_14.toSpanStyle()){
                    append("이용약관 동의\n\n")
                }
                append("[서비스 이용 약관 동의]\n")
                withStyle(style = AppTypography.sub_bold_12.toSpanStyle()){
                    append("제 1조 (목적)")
                }
            }
        }
    }

    data object Privacy : Term(
        key = "privacy",
        title = "개인정보 수집 및 이용 동의",
        required = true
    ) {
        override fun getTermContent(): AnnotatedString {
            return buildAnnotatedString {
                withStyle(style = AppTypography.sub_bold_14.toSpanStyle()){
                    append("개인정보 수집 및 이용동의\n\n")
                }
                append("[개인정보 수집 및 이용동의]\n")
                withStyle(style = AppTypography.sub_bold_12.toSpanStyle()){
                    append("제 1조 (목적)")
                }
            }
        }
    }

    data object Marketing : Term(
        key = "marketing",
        title = "마케팅 정보 수신 동의",
        required = false
    ) {
        override fun getTermContent(): AnnotatedString {
            return buildAnnotatedString {
                withStyle(style = AppTypography.sub_bold_14.toSpanStyle()){
                    append("이용약관 동의\n\n")
                }
                append("[서비스 이용 약관 동의]")
                withStyle(style = AppTypography.sub_bold_12.toSpanStyle()){
                    append("제 1조 (목적)")
                }
            }
        }
    }
}
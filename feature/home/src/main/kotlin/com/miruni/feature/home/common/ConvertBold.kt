package com.miruni.feature.home.common

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle

/**
 * 문자열에서 ' '로 구분한 부분 Bold 처리
 */
fun convertBold(text: String): AnnotatedString {
    return buildAnnotatedString {
        var currIdx = 0
        val regex = "'([^']+)'".toRegex()

        for (match in regex.findAll(text)) {
            val start = match.range.first
            val end = match.range.last + 1
            val boldText = match.groupValues[1]

            // 일반 텍스트 추가
            append(text.substring(currIdx, start))

            // Bold 처리 텍스트 추가
            withStyle(
                style = SpanStyle(fontWeight = FontWeight.Bold)
            ) { append(boldText) }

            currIdx = end
        }

        // bold 처리하고 남은 텍스트 추가
        if (currIdx < text.length) {
            append(text.substring(currIdx))
        }
    }
}
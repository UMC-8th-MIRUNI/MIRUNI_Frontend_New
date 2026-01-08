package com.miruni.feature.signup.component.textfield

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

/**
 * 공통: formatted 문자열을 만들면서 raw<->transformed 커서 매핑을 안전하게 생성
 */
private data class FormatResult(
    val out: String,
    val rawToTransformed: IntArray,  // size = rawLen + 1
    val transformedToRaw: IntArray,  // size = outLen + 1
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FormatResult

        if (out != other.out) return false
        if (!rawToTransformed.contentEquals(other.rawToTransformed)) return false
        if (!transformedToRaw.contentEquals(other.transformedToRaw)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = out.hashCode()
        result = 31 * result + rawToTransformed.contentHashCode()
        result = 31 * result + transformedToRaw.contentHashCode()
        return result
    }
}

private fun buildFormatResult(
    raw: String,
    maxLen: Int,
    shouldInsertSeparatorAfterRawIndex: (rawIndex: Int, rawLen: Int) -> Char? // 예: 3이면 '.' 등
): FormatResult {
    val digits = raw.filter(Char::isDigit).take(maxLen)
    val rawLen = digits.length

    val outSb = StringBuilder()
    val rawToT = IntArray(rawLen + 1)
    rawToT[0] = 0

    var tIndex = 0
    for (i in 0 until rawLen) {
        outSb.append(digits[i])
        tIndex += 1
        rawToT[i + 1] = tIndex

        val sep = shouldInsertSeparatorAfterRawIndex(i, rawLen)
        if (sep != null && i != rawLen - 1) {
            outSb.append(sep)
            tIndex += 1
            rawToT[i + 1] = tIndex
        }
    }

    val out = outSb.toString()
    val outLen = out.length

    // transformed offset -> raw offset: 앞에서부터 숫자 개수로 계산(안전)
    val tToRaw = IntArray(outLen + 1)
    var countDigits = 0
    tToRaw[0] = 0
    for (i in 0 until outLen) {
        if (out[i].isDigit()) countDigits++
        tToRaw[i + 1] = countDigits
    }

    return FormatResult(out, rawToT, tToRaw)
}

/**
 * 생년월일: YYYY.MM.DD (raw 최대 8자리)
 * raw: "19991231" -> out: "1999.12.31"
 */
class BirthDateVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val result = buildFormatResult(
            raw = text.text,
            maxLen = 8
        ) { rawIndex, rawLen ->
            // year(4) 뒤, month(2) 뒤에 점 찍기 (rawIndex는 0-based)
            when (rawIndex) {
                3 -> '.'   // after 4th digit
                5 -> '.'   // after 6th digit
                else -> null
            }
        }

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                val o = offset.coerceIn(0, result.rawToTransformed.lastIndex)
                return result.rawToTransformed[o].coerceIn(0, result.out.length)
            }

            override fun transformedToOriginal(offset: Int): Int {
                val t = offset.coerceIn(0, result.transformedToRaw.lastIndex)
                return result.transformedToRaw[t].coerceIn(0, result.rawToTransformed.lastIndex)
            }
        }

        return TransformedText(AnnotatedString(result.out), offsetMapping)
    }
}

/**
 * 전화번호:
 * - 02로 시작하면: 02-XXX-XXXX(9자리) / 02-XXXX-XXXX(10자리)
 * - 그 외(010/031 등): XXX-XXX-XXXX(10자리) / XXX-XXXX-XXXX(11자리)
 *
 * raw는 digits only를 가정.
 */
class PhoneNumberVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val rawDigits = text.text.filter(Char::isDigit)

        val isSeoul = rawDigits.startsWith("02")
        val maxLen = if (isSeoul) 10 else 11
        val raw = rawDigits.take(maxLen)
        val rawLen = raw.length

        // prefix 길이(02는 2, 그 외는 3)
        val prefixLen = if (isSeoul) 2 else 3

        // 중간 자리수 결정(현재 길이에 따라)
        val middleLen = when {
            isSeoul -> if (rawLen >= 10) 4 else 3      // 02-XXXX-XXXX vs 02-XXX-XXXX
            else -> if (rawLen >= 11) 4 else 3         // XXX-XXXX-XXXX vs XXX-XXX-XXXX
        }

        // 대시 삽입 위치: prefix 끝, prefix+middle 끝
        val result = buildFormatResult(
            raw = raw,
            maxLen = maxLen
        ) { rawIndex, len ->
            val firstDashAfter = prefixLen - 1
            val secondDashAfter = prefixLen + middleLen - 1

            when (rawIndex) {
                firstDashAfter -> if (len > prefixLen) '-' else null
                secondDashAfter -> if (len > prefixLen + middleLen) '-' else null
                else -> null
            }
        }

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                val o = offset.coerceIn(0, result.rawToTransformed.lastIndex)
                return result.rawToTransformed[o].coerceIn(0, result.out.length)
            }

            override fun transformedToOriginal(offset: Int): Int {
                val t = offset.coerceIn(0, result.transformedToRaw.lastIndex)
                return result.transformedToRaw[t].coerceIn(0, result.rawToTransformed.lastIndex)
            }
        }

        return TransformedText(AnnotatedString(result.out), offsetMapping)
    }
}

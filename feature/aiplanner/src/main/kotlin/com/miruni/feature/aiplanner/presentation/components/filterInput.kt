package com.miruni.feature.aiplanner.presentation.components

fun filterDateInput(input: String): String {
    // 숫자만 허용
    val digits = input.filter { it.isDigit() }

    if (digits.isEmpty()) return ""

    // 최대 MMdd → 4자리
    val limited = digits.take(4)

    return when (limited.length) {
        1, 2 -> limited               // M 또는 MM
        3 -> "${limited[0]}/${limited.substring(1)}"
        4 -> "${limited.substring(0, 2)}/${limited.substring(2)}"
        else -> limited
    }
}

fun filterTimeInput(input: String): String {
    val digits = input.filter { it.isDigit() }.take(4)

    return when (digits.length) {
        0 -> ""
        1, 2 -> digits
        3 -> "${digits.substring(0, 2)}:${digits.substring(2)}"
        4 -> "${digits.substring(0, 2)}:${digits.substring(2)}"
        else -> digits
    }
}
package com.miruni.feature.login.model


data class TextInputField(
    val value: String = "",
    val isError: Boolean = false,
    val errorMessage: String? = null
) {
    fun clearError(): TextInputField = copy(isError = false, errorMessage = null)
    fun withError(errorMessage: String? = null): TextInputField = copy(isError = true, errorMessage = errorMessage)
}

val TextInputField.text: String get() = value
val TextInputField.trimmed: String get() = value.trim()
val TextInputField.length: Int get() = value.length
fun TextInputField.isBlank(): Boolean = value.isBlank()
fun TextInputField.isNotBlank(): Boolean = value.isNotBlank()

// --- 숫자 관련 ---
val TextInputField.digits: String get() = value.filter(Char::isDigit)

fun TextInputField.hasOnlyDigits(): Boolean =
    value.isNotEmpty() && value.all(Char::isDigit)

fun TextInputField.digitsLength(): Int = digits.length
fun TextInputField.isDigitsLengthIn(range: IntRange): Boolean = digits.length in range

// --- 검증 helper (에러 상태까지 포함해서) ---
fun TextInputField.isValid(predicate: (String) -> Boolean): Boolean =
    !isError && predicate(value)

fun TextInputField.isValidTrimmed(predicate: (String) -> Boolean): Boolean =
    !isError && predicate(value.trim())

fun TextInputField.isValidDigits(predicate: (String) -> Boolean): Boolean =
    !isError && predicate(digits)

// --- 비교 helper ---
fun TextInputField.equalsValue(other: TextInputField): Boolean =
    value == other.value

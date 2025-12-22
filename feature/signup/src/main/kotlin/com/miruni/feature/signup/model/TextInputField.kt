package com.miruni.feature.signup.model

data class TextInputField(
    val value : String = "",
    val isError : Boolean = false,
    val errorMessage : String? = null
){
    fun clearError() : TextInputField {
        return copy(isError = false, errorMessage = null)
    }
    fun withError(errorMessage: String? = null) : TextInputField {
        return copy(isError = true, errorMessage = errorMessage)
    }
}
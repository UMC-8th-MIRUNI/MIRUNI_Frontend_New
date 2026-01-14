package com.miruni.core.result

sealed class DataResult<out D, out E : RootError> {
    data class Success<out D, out E : RootError>(val data: D) : DataResult<D, E>()
    data class Error<out E : RootError>(val error: E) : DataResult<Nothing, E>()
    override fun toString(): String {
        return when (this) {
            is Success -> "Success[data=$data]"
            is Error -> "Error[exception=$error]"
        }
    }
}
package com.miruni.core.network

/**
 * 공통 API Response
 */
data class ApiResponse<T>(
    val errorCode: String,
    val message: String,
    val result: T
)
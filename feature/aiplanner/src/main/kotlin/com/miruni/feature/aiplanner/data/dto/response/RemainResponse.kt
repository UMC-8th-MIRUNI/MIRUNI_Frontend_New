package com.miruni.feature.aiplanner.data.dto.response

data class RemainResponse(
    val remain: Int // 잔여 횟수
) {
    fun toDomain(): Int {
        return remain
    }
}
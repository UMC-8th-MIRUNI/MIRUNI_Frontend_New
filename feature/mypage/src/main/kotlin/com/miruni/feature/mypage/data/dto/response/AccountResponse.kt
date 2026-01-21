package com.miruni.feature.mypage.data.dto.response

/**
 * 계정 정보 업데이트 응답 DTO
 * API: PATCH /api/users/account
 */
data class AccountResponse(
    val id: Long,
    val nickname: String?,
    val profileImage: String?,
    val name: String?,
    val email: String,
    val phoneNumber: String?,
    val birth: String?
)

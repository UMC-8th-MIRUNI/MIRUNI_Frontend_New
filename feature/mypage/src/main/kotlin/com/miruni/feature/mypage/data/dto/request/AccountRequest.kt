package com.miruni.feature.mypage.data.dto.request

/**
 * 계정 정보 업데이트 요청 DTO
 * API: PATCH /api/users/account
 */
data class AccountRequest(
    val name: String?,
    val birth: String?,
    val phoneNumber: String?
)

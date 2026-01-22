package com.miruni.feature.mypage.data.dto.response

/**
 * 프로필 업데이트 응답 DTO
 * API: PUT /api/users/profile
 */
data class ProfileResponse(
    val id: Long,
    val nickname: String,
    val profileImage: String,
    val name: String?,
    val email: String,
    val phoneNumber: String?,
    val birth: String?
)

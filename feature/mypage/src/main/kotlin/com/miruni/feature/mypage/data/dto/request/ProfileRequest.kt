package com.miruni.feature.mypage.data.dto.request

/**
 * 프로필 업데이트 요청 DTO
 * API: PUT /api/users/profile
 */
data class ProfileRequest(
    val profileImage: String,
    val nickname: String
)

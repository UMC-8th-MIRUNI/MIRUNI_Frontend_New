package com.miruni.feature.mypage.data.dto.response

/**
 * 프로필 업데이트 응답 DTO
 * API: PUT /api/users/profile
 */

// 임시. 프로덕션 시 초기화 x
data class ProfileResponse(
    val id: Long = 12345678,
    val nickname: String = "김가영",
    val profileImage: String,
    val name: String = "김가영",
    val email: String = "gayeong@gmail.com",
    val phoneNumber: String = "010-1111-2222",
    val birth: String = "2000.01.01"
)

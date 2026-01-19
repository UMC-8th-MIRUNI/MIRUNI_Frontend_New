package com.miruni.feature.mypage.domain.model

/**
 * 사용자 계정 도메인 모델
 */
data class UserAccount(
    val id: Long,
    val nickname: String?,
    val profileImage: String?,
    val name: String?,
    val email: String,
    val phoneNumber: String?,
    val birth: String?
)

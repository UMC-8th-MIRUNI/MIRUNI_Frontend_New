package com.miruni.feature.mypage.data.mapper

import com.miruni.feature.mypage.data.dto.response.ProfileResponse
import com.miruni.feature.mypage.domain.model.UserProfile

/**
 * ProfileResponse DTO를 UserProfile 도메인 모델로 변환
 */
fun ProfileResponse.toDomain(): UserProfile = UserProfile(
    id = id,
    nickname = nickname,
    profileImage = profileImage,
    name = name,
    email = email,
    phoneNumber = phoneNumber,
    birth = birth
)

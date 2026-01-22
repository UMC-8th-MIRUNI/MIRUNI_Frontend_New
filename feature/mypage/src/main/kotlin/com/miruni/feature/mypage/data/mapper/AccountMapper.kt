package com.miruni.feature.mypage.data.mapper

import com.miruni.feature.mypage.data.dto.response.AccountResponse
import com.miruni.feature.mypage.domain.model.UserAccount

/**
 * AccountResponse DTO를 UserAccount 도메인 모델로 변환
 */
fun AccountResponse.toDomain(): UserAccount = UserAccount(
    id = id,
    nickname = nickname,
    profileImage = profileImage,
    name = name,
    email = email,
    phoneNumber = phoneNumber,
    birth = birth
)

package com.miruni.feature.home.presentation.model

import com.miruni.feature.home.domain.model.UserInfo

data class UserInfoUiModel(
    val nickname: String, // 사용자 닉네임
    val peanutCount: Int // 땅콩 개수
)

fun UserInfo.toUiModel(): UserInfoUiModel {
    return UserInfoUiModel(
        nickname = nickname,
        peanutCount = peanutCount
    )
}
package com.miruni.feature.home.data.dto.response

import com.miruni.feature.home.domain.model.UserInfo

data class GetHomeUserResponse(
    val nickname: String, // 사용자 닉네임
    val peanutCount: Int // 땅콩 개수
) {
    fun toDomain(): UserInfo {
        return UserInfo(
            nickname = nickname,
            peanutCount = peanutCount
        )
    }
}
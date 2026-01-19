package com.miruni.feature.mypage.domain.repository

import com.miruni.core.result.DataError
import com.miruni.core.result.DataResult
import com.miruni.feature.mypage.domain.model.UserProfile

/**
 * 프로필 레포지토리 인터페이스
 */
interface ProfileRepository {

    /**
     * 프로필 업데이트
     * @param profileImage 프로필 이미지 (예: "GREEN", "BETTY")
     * @param nickname 닉네임
     * @return DataResult<UserProfile, DataError>
     */
    suspend fun updateProfile(
        profileImage: String,
        nickname: String
    ): DataResult<UserProfile, DataError>
}

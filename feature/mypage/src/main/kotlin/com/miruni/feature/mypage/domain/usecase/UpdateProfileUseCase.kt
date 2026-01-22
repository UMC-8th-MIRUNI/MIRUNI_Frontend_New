package com.miruni.feature.mypage.domain.usecase

import android.util.Log
import com.miruni.core.result.DataError
import com.miruni.core.result.DataResult
import com.miruni.feature.mypage.domain.model.UserProfile
import com.miruni.feature.mypage.domain.repository.ProfileRepository

/**
 * 프로필 업데이트 유스케이스
 */
class UpdateProfileUseCase(
    private val profileRepository: ProfileRepository
) {
    /**
     * 프로필 업데이트 실행
     * @param profileImage 프로필 이미지 (예: "GREEN", "BETTY")
     * @param nickname 닉네임
     * @return DataResult<UserProfile, DataError>
     */
    suspend operator fun invoke(
        profileImage: String,
        nickname: String
    ): DataResult<UserProfile, DataError> {
        Log.d(TAG, "invoke() called - profileImage: $profileImage, nickname: $nickname")
        return profileRepository.updateProfile(
            profileImage = profileImage,
            nickname = nickname
        )
    }

    companion object {
        private const val TAG = "UpdateProfileUseCase"
    }
}

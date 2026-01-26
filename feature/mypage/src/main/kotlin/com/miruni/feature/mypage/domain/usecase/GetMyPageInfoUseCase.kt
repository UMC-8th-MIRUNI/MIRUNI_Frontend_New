package com.miruni.feature.mypage.domain.usecase

import android.util.Log
import com.miruni.core.result.DataError
import com.miruni.core.result.DataResult
import com.miruni.feature.mypage.domain.model.UserProfile
import com.miruni.feature.mypage.domain.repository.ProfileRepository

/**
 * 마이페이지 사용자 정보 조회 유스케이스
 */
class GetMyPageInfoUseCase(
    private val profileRepository: ProfileRepository
) {
    /**
     * 마이페이지 사용자 정보 조회 실행
     * @return DataResult<UserProfile, DataError>
     */
    suspend operator fun invoke(): DataResult<UserProfile, DataError> {
        Log.d(TAG, "invoke() called")
        return profileRepository.getMyPageInfo()
    }

    companion object {
        private const val TAG = "GetMyPageInfoUseCase"
    }
}

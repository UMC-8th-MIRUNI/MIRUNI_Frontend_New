package com.miruni.feature.mypage.data.repository

import android.util.Log
import com.miruni.core.network.NetworkResult
import com.miruni.core.result.DataError
import com.miruni.core.result.DataResult
import com.miruni.feature.mypage.data.dto.request.ProfileRequest
import com.miruni.feature.mypage.data.mapper.toDomain
import com.miruni.feature.mypage.data.mapper.toDomainError
import com.miruni.feature.mypage.domain.datasource.ProfileRemoteDataSource
import com.miruni.feature.mypage.domain.model.UserProfile
import com.miruni.feature.mypage.domain.repository.ProfileRepository

/**
 * 프로필 레포지토리 구현체
 */
class ProfileRepositoryImpl(
    private val profileRemoteDataSource: ProfileRemoteDataSource
) : ProfileRepository {

    override suspend fun updateProfile(
        profileImage: String,
        nickname: String
    ): DataResult<UserProfile, DataError> {
        Log.d(TAG, "updateProfile() called - profileImage: $profileImage, nickname: $nickname")

        val request = ProfileRequest(
            profileImage = profileImage,
            nickname = nickname
        )

        return when (val networkResult = profileRemoteDataSource.updateProfile(request)) {
            is NetworkResult.Success -> {
                val response = networkResult.data
                Log.d(TAG, "updateProfile() NetworkResult.Success - response: $response")

                // 서버에서 에러 코드를 반환한 경우
                if (!response.errorCode.isNullOrBlank()) {
                    Log.e(TAG, "updateProfile() Server error - errorCode: ${response.errorCode}, message: ${response.message}")
                    DataResult.Error(
                        DataError.CustomError(
                            code = response.errorCode ?: "UNKNOWN",
                            msg = response.message ?: "요청 처리 중 문제가 발생했어요."
                        )
                    )
                } else {
                    val result = response.result
                    if (result == null) {
                        Log.e(TAG, "updateProfile() result is null")
                        DataResult.Error(DataError.DataNotFound)
                    } else {
                        Log.d(TAG, "updateProfile() Success - result: $result")
                        DataResult.Success(result.toDomain())
                    }
                }
            }

            is NetworkResult.Failure -> {
                Log.e(TAG, "updateProfile() NetworkResult.Failure - error: ${networkResult.error}")
                DataResult.Error(networkResult.error.toDomainError())
            }
        }
    }

    companion object {
        private const val TAG = "ProfileRepository"
    }
}

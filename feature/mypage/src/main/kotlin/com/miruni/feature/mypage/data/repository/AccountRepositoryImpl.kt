package com.miruni.feature.mypage.data.repository

import android.util.Log
import com.miruni.core.network.NetworkResult
import com.miruni.core.result.DataError
import com.miruni.core.result.DataResult
import com.miruni.feature.mypage.data.dto.request.AccountRequest
import com.miruni.feature.mypage.data.mapper.toDomain
import com.miruni.feature.mypage.data.mapper.toDomainError
import com.miruni.feature.mypage.domain.datasource.AccountRemoteDataSource
import com.miruni.feature.mypage.domain.model.UserAccount
import com.miruni.feature.mypage.domain.repository.AccountRepository

/**
 * 계정 레포지토리 구현체
 */
class AccountRepositoryImpl(
    private val accountRemoteDataSource: AccountRemoteDataSource
) : AccountRepository {

    override suspend fun updateAccount(
        name: String?,
        birth: String?,
        phoneNumber: String?
    ): DataResult<UserAccount, DataError> {
        Log.d(TAG, "updateAccount() called - name: $name, birth: $birth, phoneNumber: $phoneNumber")

        val request = AccountRequest(
            name = name,
            birth = birth,
            phoneNumber = phoneNumber
        )

        return when (val networkResult = accountRemoteDataSource.updateAccount(request)) {
            is NetworkResult.Success -> {
                val response = networkResult.data
                Log.d(TAG, "updateAccount() NetworkResult.Success - response: $response")

                // 서버에서 에러 코드를 반환한 경우
                if (!response.errorCode.isNullOrBlank()) {
                    Log.e(TAG, "updateAccount() Server error - errorCode: ${response.errorCode}, message: ${response.message}")
                    DataResult.Error(
                        DataError.CustomError(
                            code = response.errorCode ?: "UNKNOWN",
                            msg = response.message ?: "요청 처리 중 문제가 발생했어요."
                        )
                    )
                } else {
                    val result = response.result
                    if (result == null) {
                        Log.e(TAG, "updateAccount() result is null")
                        DataResult.Error(DataError.DataNotFound)
                    } else {
                        Log.d(TAG, "updateAccount() Success - result: $result")
                        DataResult.Success(result.toDomain())
                    }
                }
            }

            is NetworkResult.Failure -> {
                Log.e(TAG, "updateAccount() NetworkResult.Failure - error: ${networkResult.error}")
                DataResult.Error(networkResult.error.toDomainError())
            }
        }
    }

    companion object {
        private const val TAG = "AccountRepository"
    }
}

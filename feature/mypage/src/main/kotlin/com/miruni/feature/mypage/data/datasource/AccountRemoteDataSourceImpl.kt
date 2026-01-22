package com.miruni.feature.mypage.data.datasource

import android.util.Log
import com.miruni.core.network.ApiResponse
import com.miruni.core.network.NetworkResult
import com.miruni.core.network.executeApiRequest
import com.miruni.feature.mypage.data.api.AccountApi
import com.miruni.feature.mypage.data.dto.request.AccountRequest
import com.miruni.feature.mypage.data.dto.response.AccountResponse
import com.miruni.feature.mypage.domain.datasource.AccountRemoteDataSource

/**
 * 계정 원격 데이터 소스 구현체
 */
class AccountRemoteDataSourceImpl(
    private val accountApi: AccountApi
) : AccountRemoteDataSource {

    override suspend fun updateAccount(
        request: AccountRequest
    ): NetworkResult<ApiResponse<AccountResponse>> {
        Log.d(TAG, "updateAccount() called - request: $request")
        return executeApiRequest {
            accountApi.updateAccount(request).also {
                Log.d(TAG, "updateAccount() response: $it")
            }
        }
    }

    companion object {
        private const val TAG = "AccountRemoteDataSource"
    }
}

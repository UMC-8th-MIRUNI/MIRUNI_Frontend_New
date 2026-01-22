package com.miruni.feature.mypage.domain.datasource

import com.miruni.core.network.ApiResponse
import com.miruni.core.network.NetworkResult
import com.miruni.feature.mypage.data.dto.request.AccountRequest
import com.miruni.feature.mypage.data.dto.response.AccountResponse

/**
 * 계정 원격 데이터 소스 인터페이스
 */
interface AccountRemoteDataSource {

    /**
     * 계정 정보 업데이트 API 호출
     * @param request 계정 업데이트 요청 데이터
     * @return NetworkResult<ApiResponse<AccountResponse>>
     */
    suspend fun updateAccount(
        request: AccountRequest
    ): NetworkResult<ApiResponse<AccountResponse>>
}

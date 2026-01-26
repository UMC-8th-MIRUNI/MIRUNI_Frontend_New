package com.miruni.feature.mypage.data.api

import com.miruni.core.network.ApiResponse
import com.miruni.feature.mypage.data.dto.request.AccountRequest
import com.miruni.feature.mypage.data.dto.response.AccountResponse
import retrofit2.http.Body
import retrofit2.http.PATCH

/**
 * 계정 관련 API 인터페이스
 */
interface AccountApi {

    /**
     * 사용자 계정 정보 변경 (이름, 생년월일, 전화번호)
     * @param accountRequest 계정 변경 요청 데이터
     * @return ApiResponse<AccountResponse> 변경된 계정 정보
     */
    @PATCH("/api/users/account")
    suspend fun updateAccount(
        @Body accountRequest: AccountRequest
    ): ApiResponse<AccountResponse>
}

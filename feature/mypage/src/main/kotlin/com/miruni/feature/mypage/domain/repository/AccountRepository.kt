package com.miruni.feature.mypage.domain.repository

import com.miruni.core.result.DataError
import com.miruni.core.result.DataResult
import com.miruni.feature.mypage.domain.model.UserAccount

/**
 * 계정 레포지토리 인터페이스
 */
interface AccountRepository {

    /**
     * 계정 정보 업데이트
     * @param name 이름
     * @param birth 생년월일
     * @param phoneNumber 전화번호
     * @return DataResult<UserAccount, DataError>
     */
    suspend fun updateAccount(
        name: String?,
        birth: String?,
        phoneNumber: String?
    ): DataResult<UserAccount, DataError>
}

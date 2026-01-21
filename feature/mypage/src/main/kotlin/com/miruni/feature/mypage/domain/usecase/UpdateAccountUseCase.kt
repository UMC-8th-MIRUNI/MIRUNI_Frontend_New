package com.miruni.feature.mypage.domain.usecase

import android.util.Log
import com.miruni.core.result.DataError
import com.miruni.core.result.DataResult
import com.miruni.feature.mypage.domain.model.UserAccount
import com.miruni.feature.mypage.domain.repository.AccountRepository

/**
 * 계정 정보 업데이트 유스케이스
 */
class UpdateAccountUseCase(
    private val accountRepository: AccountRepository
) {
    /**
     * 계정 정보 업데이트 실행
     * @param name 이름
     * @param birth 생년월일
     * @param phoneNumber 전화번호
     * @return DataResult<UserAccount, DataError>
     */
    suspend operator fun invoke(
        name: String?,
        birth: String?,
        phoneNumber: String?
    ): DataResult<UserAccount, DataError> {
        Log.d(TAG, "invoke() called - name: $name, birth: $birth, phoneNumber: $phoneNumber")
        return accountRepository.updateAccount(
            name = name,
            birth = birth,
            phoneNumber = phoneNumber
        )
    }

    companion object {
        private const val TAG = "UpdateAccountUseCase"
    }
}

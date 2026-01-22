package com.miruni.feature.login.domain.usecase

import com.miruni.core.result.DataError
import com.miruni.core.result.DataResult
import com.miruni.feature.login.domain.model.AuthToken
import com.miruni.feature.login.domain.repository.AuthRepository

class GetKakaoLoginUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(kakaoAccessToken: String, autoLogin : Boolean) : DataResult<AuthToken, DataError> {
        return authRepository.kakaoLogin(kakaoAccessToken,autoLogin)
    }
}
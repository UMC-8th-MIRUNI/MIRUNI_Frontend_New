package com.miruni.core.domain.auth

import kotlinx.coroutines.flow.Flow

/**
 * Access Token 저장소
 * - 조회
 * - 저장
 * - 삭제
 */
interface TokenDataStore {
    /** Access Token 조회 */
    suspend fun getAccessToken(): String?

    suspend fun getAccessTokenFlow(): Flow<String?>

    /** Access Token 저장 */
    suspend fun saveAccessToken(token: String)

    /** Access Token 삭제 */
    suspend fun clear()
}
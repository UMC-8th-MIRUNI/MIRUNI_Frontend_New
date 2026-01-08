package com.miruni.feature.login.utils.helper

import android.app.Activity
import android.content.Context
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun kakaoLogin(context: Context): Result<String> =
    suspendCoroutine { cont ->
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            when {
                error != null -> cont.resume(Result.failure(error))
                token != null -> cont.resume(Result.success(token.accessToken))
            }
        }
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context, callback = callback)
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }
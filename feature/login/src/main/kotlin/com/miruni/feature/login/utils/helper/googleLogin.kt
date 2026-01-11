package com.miruni.feature.login.utils.helper

import android.app.Activity
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException

suspend fun googleLogin(
    activity: Activity,
    webClientId: String,
    autoLogin: Boolean = false,
): Result<String,> {

    val credentialManager = CredentialManager.create(activity)

    suspend fun request(filterAuthorized: Boolean): GetCredentialResponse {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setServerClientId(webClientId)
            .setFilterByAuthorizedAccounts(filterAuthorized)
            .setAutoSelectEnabled(autoLogin)
            .build()

        val req = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        return credentialManager.getCredential(request = req, context = activity)
    }

    val result = try {
        request(filterAuthorized = true)
    } catch (e: NoCredentialException) {
        try {
            request(filterAuthorized = false)
        } catch (e2: GetCredentialException) {
            return Result.failure(e2)
        }
    } catch (e: GetCredentialCancellationException) {
        return Result.failure(e)
    } catch (e: GetCredentialException) {
        return Result.failure(e)
    }

    val credential = result.credential
    if (credential is androidx.credentials.CustomCredential &&
        credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
    ) {
        return try {
            val google = GoogleIdTokenCredential.createFrom(credential.data)
            Result.success(google.idToken)
        } catch (e: GoogleIdTokenParsingException) {
            Result.failure(e)
        }
    }

    return Result.failure(Exception("구글 로그인에 실패했습니다. (type=${credential.type})"))
}

package com.miruni.core.network

import com.miruni.core.result.DataError
import java.io.EOFException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException
import kotlinx.serialization.SerializationException

sealed class NetworkError {

    data class Http(
        val code: Int,
        val message: String? = null
    ) : NetworkError()

    // 연결/전송 계열
    data object Timeout : NetworkError()
    data object NoConnection : NetworkError()
    data object HostUnreachable : NetworkError()
    data object DnsResolutionFailed : NetworkError()
    data object SslHandshakeFailed : NetworkError()
    data object ConnectionLost : NetworkError()

    // 파싱 계열
    data object ParseError : NetworkError()

    // 그 외
    data class Unknown(val throwable: Throwable? = null) : NetworkError()

    companion object {
        fun fromThrowable(t: Throwable): NetworkError = when (t) {
            is SocketTimeoutException -> Timeout
            is UnknownHostException -> DnsResolutionFailed
            is ConnectException -> HostUnreachable
            is SSLHandshakeException -> SslHandshakeFailed
            is SerializationException, is EOFException -> ParseError
            is IOException -> NoConnection
            else -> Unknown(t)
        }
    }
}


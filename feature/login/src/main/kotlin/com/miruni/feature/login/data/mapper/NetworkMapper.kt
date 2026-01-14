package com.miruni.feature.login.data.mapper

import com.miruni.core.network.NetworkError
import com.miruni.core.result.DataError

fun NetworkError.toDomainError(): DataError = when (this) {
    is NetworkError.Http -> DataError.CustomError(code = code.toString(), msg = message ?: "요청 처리 중 문제가 발생했어요.")
    NetworkError.Timeout -> DataError.CustomError(code = "TIMEOUT", msg = "응답이 늦어요. 잠시 후 다시 시도해 주세요.")
    NetworkError.NoConnection,
    NetworkError.ConnectionLost,
    NetworkError.DnsResolutionFailed,
    NetworkError.HostUnreachable -> DataError.CustomError(code = "NETWORK", msg = "인터넷 연결을 확인해 주세요.")
    NetworkError.SslHandshakeFailed -> DataError.CustomError(code = "SSL", msg = "보안 연결에 문제가 있어요.")
    NetworkError.ParseError -> DataError.CustomError(code = "PARSE", msg = "요청 처리 중 문제가 발생했어요.")
    is NetworkError.Unknown -> DataError.Unknown
}

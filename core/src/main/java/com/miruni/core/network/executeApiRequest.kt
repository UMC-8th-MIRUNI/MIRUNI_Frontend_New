package com.miruni.core.network

import kotlinx.coroutines.CancellationException

suspend inline fun <T> executeApiRequest(
    crossinline call: suspend () -> T
): NetworkResult<T> = try {
    NetworkResult.Success(call())
} catch (c: CancellationException) {
    throw c
} catch (t: Throwable) {
    // 여기서는 HttpException인지 몰라도 됨(의존성 없으니까)
    NetworkResult.Failure(NetworkError.fromThrowable(t))
}

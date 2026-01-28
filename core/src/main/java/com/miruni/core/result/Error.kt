package com.miruni.core.result

interface AppError
typealias RootError = AppError

sealed class DataError(
    val errorCode: String,
    val errorMessage: String,
) : AppError {

    data class Validation(
        val message: String = "입력 내용을 다시 확인해 주세요.",
    ) : DataError(
        errorCode = "VALIDATION",
        errorMessage = message,
    )

    // 서버/도메인에서 의미 있는 에러만 남김
    data object Unknown :
        DataError("UNKNOWN", "문제가 발생했어요. 잠시 후 다시 시도해 주세요.")

    data object Unauthorized :
        DataError("401", "로그인이 필요해요. 다시 로그인해 주세요.")

    data object Forbidden :
        DataError("403", "접근 권한이 없어요.")

    data object BadRequest :
        DataError("400", "요청이 올바르지 않아요. 입력 내용을 확인해 주세요.")

    data object TimeConflict :
        DataError(errorCode = "AI_PLAN400_004", errorMessage = "같은 시간에 다른 일정이 예정되어 있습니다.")

    data object PeanutInsufficient :
        DataError(errorCode = "USER400_8", errorMessage = "땅콩 갯수가 부족합니다.")

    data object DataNotFound :
        DataError("404", "요청한 정보를 찾지 못했어요.")

    data object ServerError :
        DataError("500", "서버에 문제가 있어요. 잠시 후 다시 시도해 주세요.")
    data class CustomError(
        val code: String = "CUSTOM_ERROR",
        val msg: String = "요청 처리 중 문제가 발생했어요.",
    ) : DataError(code, msg)
}

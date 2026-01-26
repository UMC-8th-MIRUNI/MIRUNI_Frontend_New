package com.miruni.core.common

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

object DateTimeHelper {
    private val SERVER_DATE_TIME_FORMAT: DateTimeFormatter =
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:00.000'Z'")

    @RequiresApi(Build.VERSION_CODES.O)
    fun toServerDateTime(
        date: LocalDate,
        time: LocalTime,
        timeZone: ZoneId = ZoneId.systemDefault()
    ): String {
        return LocalDateTime.of(date, time)
            .atZone(timeZone) // 기준 time zone
            .withZoneSameInstant(ZoneOffset.UTC) // UTC 변환
            .format(SERVER_DATE_TIME_FORMAT)
    }
}
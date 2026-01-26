package com.miruni.feature.aiplanner.presentation.components

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Duration
import java.time.LocalTime
import java.time.format.DateTimeFormatter

/**
 * 날짜/시간 포맷
 */
@RequiresApi(Build.VERSION_CODES.O)
object DateUtils {
    val serverDateFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val uiDateFmt = DateTimeFormatter.ofPattern("M/dd")

    val serverTimeFmt = DateTimeFormatter.ofPattern("HH:mm:ss")
    val uiTimeFmt = DateTimeFormatter.ofPattern("HH:mm")

    fun parseDuration(start: String, end: String): Long {
        return try {
            val s = LocalTime.parse(start, serverTimeFmt)
            val e = LocalTime.parse(end, serverTimeFmt)
            Duration.between(s, e).toMinutes()
        } catch (e: Exception) { 0L }
    }
}
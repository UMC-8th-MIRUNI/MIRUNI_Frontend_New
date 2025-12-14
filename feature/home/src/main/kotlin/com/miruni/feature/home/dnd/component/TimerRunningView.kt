package com.miruni.feature.home.dnd.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.miruni.core.designsystem.AppTypography

@Composable
fun TimerRunningView(remainingSeconds: Int) {

    val h = remainingSeconds / 3600
    val m = (remainingSeconds % 3600) / 60
    val s = remainingSeconds % 60

    Text(
        text = "%02d:%02d:%02d".format(h, m, s),
        style = AppTypography.header_bold_20
    )
}

package com.miruni.feature.pwreset.component.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp
import com.miruni.core.designsystem.AppTypography
import com.miruni.core.designsystem.Gray
import com.miruni.core.designsystem.White

@Composable
fun PwResetTheme(
    headerText: String?,
    subHeaderText: String?,
    content: @Composable ColumnScope.() -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp)
        ) {
            if (headerText != null && subHeaderText != null) {
                Text(headerText, style = AppTypography.header_bold_20)
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    subHeaderText,
                    style = AppTypography.body_regular_12,
                    color = Gray.gray_500
                )
            }
            content()
        }
    }
}

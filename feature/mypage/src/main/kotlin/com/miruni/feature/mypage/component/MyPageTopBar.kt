package com.miruni.feature.mypage.component

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.miruni.core.designsystem.MiruniTypography

private const val TAG = "MyPageTopBar"

@Composable
fun MyPageTopBar(
    modifier: Modifier = Modifier,
    text: String,
    onBackClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp)
            .testTag("myPageTopBar"),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (onBackClick != null) {
            IconButton(
                onClick = {
                    Log.d(TAG, "Back button clicked")
                    onBackClick()
                },
                modifier = Modifier.testTag("backButton")
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = "back"
                )
            }
        } else {
            // 빈 공간 유지
            Spacer(modifier = Modifier.size(48.dp))
        }

        Spacer(modifier = modifier.weight(1f))

        Text(
            text = text,
            style = MiruniTypography.titleMedium
        )

        Spacer(modifier = modifier.weight(1f))

        // 오른쪽 공간 균형 맞추기
        Spacer(modifier = Modifier.size(48.dp))
    }
}

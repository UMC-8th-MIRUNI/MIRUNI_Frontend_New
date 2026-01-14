package com.miruni.feature.home.dnd.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.miruni.core.designsystem.AppTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DndTopBar(
    onClose: () -> Unit,
    title: String = "방해금지 모드",
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.White,
            titleContentColor = Color.Black,
            actionIconContentColor = Color.Black
        ),
        title = {
            Text(
                text = title,
                style = AppTypography.header_bold_16,
                textAlign = TextAlign.Center,
            )
        },
        actions = {
            IconButton(onClick = onClose) {
                // 홈페이지로 이동
                Icon(
                    contentDescription = "닫기",
                    imageVector = Icons.Filled.Close,
                )
            }
        }
    )
}
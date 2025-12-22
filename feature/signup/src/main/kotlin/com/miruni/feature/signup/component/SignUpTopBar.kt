package com.miruni.feature.signup.component

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import com.miruni.designsystem.AppTypography
import com.miruni.feature.signup.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpTopBar(
    onPrevStep: () -> Unit,
    title: String = "회원가입",
    actions: @Composable () -> Unit = {},
) {
    CenterAlignedTopAppBar(
        navigationIcon = {
            IconButton(onClick = onPrevStep) {
                Icon(
                    painter = painterResource(R.drawable.ic_chevron_right),
                    contentDescription = "뒤로 가기"
                )
            }
        },
        title = {
            Text(
                text = title,
                style = AppTypography.header_bold_16,
                textAlign = TextAlign.Center,
            )
        },
        actions = {
            Box(modifier = Modifier.padding(end = 16.dp)) {
                actions()
            }
        }
    )
}

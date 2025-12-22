package com.miruni.feature.home.dnd

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.miruni.core.designsystem.AppTypography
import com.miruni.core.designsystem.Gray
import com.miruni.core.designsystem.MiruniTheme
import com.miruni.feature.home.R
import com.miruni.feature.home.dnd.component.CancelOrConfirmButton
import com.miruni.feature.home.dnd.component.RerunTimerSettingModal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DndPauseScreen(
    navController: NavHostController,
) {
    var showRerunTimerSettingModal by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            Column {
                CancelOrConfirmButton(
                    onCancelClick = {
                        navController.popBackStack()
                    },
                    onConfirmClick = { showRerunTimerSettingModal = true }
                )
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFFFFFFF)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.miruni_basic),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(100.dp)
                    .size(126.dp)
            )
            Text(
                modifier = Modifier,
                style = AppTypography.header_bold_20,
                color = Color.Black,
                text = "정말 중지하시겠어요?",
            )

            Spacer(Modifier.height(16.dp))

            Text(
                modifier = Modifier,
                style = AppTypography.sub_medium_14,
                color = Gray.gray_500,
                textAlign = TextAlign.Center,
                text = "지금 멈추면\n땅콩을 n개 받을 수 있어요!",
            )
        }
    }
    if (showRerunTimerSettingModal) {
        RerunTimerSettingModal(
            onDismiss = {
                showRerunTimerSettingModal = false
            },
            onConfirm = { hour, minute ->
                showRerunTimerSettingModal = false

                // TODO: 재실행 시간 저장
                Log.d("Modal", "rerun at $hour:$minute")
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DndPauseScreenPreview() {
    MiruniTheme {
        DndPauseScreen(
            navController = rememberNavController()
        )
    }
}
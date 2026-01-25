package com.miruni.feature.mypage.info.feedback

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.miruni.core.designsystem.AppTypography
import com.miruni.core.designsystem.Gray
import com.miruni.core.designsystem.MainColor
import com.miruni.core.designsystem.MiruniTheme
import com.miruni.feature.mypage.component.MyPageTopBar

private const val TAG = "FeedbackDetailsScreen"

@Composable
fun ShowFeedbackDetailsScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Scaffold(
        containerColor = Color(0xFFF6F5F6),
        topBar = {
            MyPageTopBar(
                text = "문의 및 피드백",
                onBackClick = {
                    Log.d(TAG, "Back button clicked")
                    navController.popBackStack()
                }
            )
        },
    ) { innerPadding ->
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(20.dp)
                .height(89.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                Color.White
            )
        ) {
            Row(
                modifier = modifier
                    .padding(20.dp)
            ) {
                Text("시간표 기능 수정 문의")
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "2025.05.23",
                        style = AppTypography.button_regular_9,
                        color = Gray.gray_500
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(text = "답변완료",
                        style = AppTypography.body_regular_12,
                        color = MainColor.miruni_green)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ShowFeedbackDetailsScreenPreview() {
    MiruniTheme {
        ShowFeedbackDetailsScreen(
            navController = rememberNavController()
        )
    }
}
package com.miruni.feature.aiplanner.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.miruni.core.designsystem.AppTypography
import com.miruni.core.designsystem.Black
import com.miruni.core.designsystem.MainColor
import com.miruni.feature.aiplanner.R

@Composable
fun AiPlannerLoadingScreen(
    navController: NavHostController
) {
    AiPlannerLoadingContent(
        onBack = { navController.popBackStack() }
    )
}

// 로딩 화면 UI 구성
@Composable
fun AiPlannerLoadingContent(
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
            .background(color = Color.White)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            /** 뒤로 가기 */
            Image(
                painter = painterResource(R.drawable.left_arrow),
                contentDescription = "뒤로가기",
                modifier = Modifier
                    .size(17.dp)
                    .clickable { onBack() }
            )
        }

        Spacer(modifier = Modifier.height(129.dp))

        /** 미루니 이미지 */
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.miruni_pencil),
                contentDescription = null,
                modifier = Modifier.size(
                    width = 222.dp,
                    height = 178.dp
                )
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 137.dp, end = 86.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.miruni_shadow),
                contentDescription = null,
                modifier = Modifier.size(
                    width = 107.dp,
                    height = 23.dp
                )
            )
        }

        Spacer(modifier = Modifier.height(66.dp))

        /** 안내 텍스트 */
        GuideTextRow()

        Spacer(modifier = Modifier.height(124.dp))

        /** 확인 버튼 */
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MainColor.miruni_green,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(vertical = 5.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "확인",
                style = AppTypography.button_semibold_16,
                color = Color(0xFFF9F9F9),
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}

@Composable
fun GuideTextRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "일정을 짜는 중이에요..",
            style = AppTypography.header_bold_20,
            color = Black
        )
    }

    Spacer(modifier = Modifier.height(22.dp))

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "AI 플래너 기능을 활용하여 가영님이 지치지 않도록,\n딱 맞는 일정을 짜드릴게요!",
            style = AppTypography.PretendardTextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                letterSpacing = (-0.05f).em,
                lineHeightRatio = 1.67f
            ),
            color = Color(0xFFBBBBBB),
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun PreviewAiPlannerLoadingScreen() {
    AiPlannerLoadingScreen(navController = rememberNavController())
}
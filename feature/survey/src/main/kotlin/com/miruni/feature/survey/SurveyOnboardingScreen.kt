package com.miruni.feature.survey

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.miruni.core.designsystem.AppTypography
import com.miruni.core.designsystem.Gray
import com.miruni.core.designsystem.MiruniTheme
import com.miruni.feature.survey.component.SingleWhiteButton

@Composable
private fun SurveyScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF24C354)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.spring),
            contentDescription = null,
            modifier = Modifier
                .size(width = 600.dp, height = 400.dp)
                .offset(x = 100.dp, y = (-300).dp)
        )
        Image(
            painter = painterResource(id = R.drawable.spring),
            contentDescription = null,
            modifier = Modifier
                .size(width = 600.dp, height = 400.dp)
                .offset(x = (-100).dp, y = 300.dp)
                .rotate(150f)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(120.dp))

            Image(
                painter = painterResource(id = R.drawable.miruni_basic),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(250.dp)
            )

            Spacer(modifier = Modifier.height(120.dp))

            Text(
                text = "안녕하세요, 김가영님!",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color(0xFFFFF608)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "시작하기 전에,\n가영님에 대해 알려주세요.",
                textAlign = TextAlign.Center,
                style = AppTypography.body_regular_14,
                fontSize = 20.sp,
                color = Gray.gray_700
            )

            Spacer(modifier = Modifier.height(160.dp))

            SingleWhiteButton(
                onClick = { navController.navigate("home") },
                text = "확인"
            )
        }
    }
}

@Preview()
@Composable
private fun SurveyScreenPreview() {
    MiruniTheme {
        SurveyScreen(
            navController = rememberNavController()
        )
    }
}
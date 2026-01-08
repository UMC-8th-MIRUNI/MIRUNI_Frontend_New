package com.miruni.feature.home.schedule

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.miruni.core.designsystem.AppTypography
import com.miruni.core.designsystem.Gray
import com.miruni.core.designsystem.MiruniTheme
import com.miruni.core.navigation.MiruniRoute
import com.miruni.feature.home.R

@Composable
fun SelectDndModeScreen(
    navController: NavHostController
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // 캐릭터 이미지
            Image(
                painter = painterResource(id = R.drawable.miruni_basic),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(190.dp)
            )

            Spacer(Modifier.height(100.dp))

            Text(
                modifier = Modifier,
                text = "방해금지모드를 사용하시겠어요?",
                style = AppTypography.header_bold_20,
            )

            Spacer(Modifier.height(20.dp))

            Text(
                modifier = Modifier,
                text = "실행 화면을 벗어나면, 계속 알림을 보내서\n다시 집중할 수 있게 도와드려요!",
                textAlign = TextAlign.Center,
                color = Gray.gray_500,
                fontSize = 13.2.sp,
                lineHeight = 20.sp
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.spacedBy(32.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Gray.gray_500,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .width(157.dp)
                        .height(49.dp),
                    shape = RoundedCornerShape(10.dp),
                    onClick = {
                        // TODO: 중지 버튼 클릭 시 일정 실행 중지 screen 으로 이동
                    }
                ) {
                    Text("아니오")
                }

                Button(
                    modifier = Modifier
                        .weight(1f)
                        .width(157.dp)
                        .height(49.dp),
                    shape = RoundedCornerShape(10.dp),
                    onClick = {
                        Log.d("DndTimerSet", "Complete clicked")
                        // TODO: 완료 버튼 클릭 시 일정 실행 조기 완료 screen 으로 이동
                    }
                ) {
                    Text("예")
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun SelectDndModeScreenPreview() {
    MiruniTheme {
        SelectDndModeScreen(
            navController = rememberNavController()
        )
    }
}
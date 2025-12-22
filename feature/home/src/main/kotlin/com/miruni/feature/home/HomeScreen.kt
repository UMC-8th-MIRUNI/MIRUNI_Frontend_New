package com.miruni.feature.home

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.miruni.core.designsystem.AppTypography
import com.miruni.core.designsystem.Gray
import com.miruni.core.designsystem.MainColor
import com.miruni.core.designsystem.MiruniTheme
import com.miruni.feature.home.dnd.component.LinearProgressBar

/** State */
var achievementRate: Float = 0.12f // 임시

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Surface (
        modifier = modifier.fillMaxSize(),
        color = MainColor.miruni_green
    ) {
        HomeContent(modifier = modifier)
    }
}

@Composable
fun HomeContent(
    modifier: Modifier
) {
    Column (
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopSection(modifier = Modifier.wrapContentHeight())
        BottomSection(modifier = Modifier.weight(1f))
    }
}

@Composable
fun TopSection(
    modifier: Modifier
) {
    Box (modifier = modifier
        .fillMaxWidth()
        .padding(24.dp)) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
        ) {
            HeaderRow()
            DescriptionSection()
            ProgressBarSection()
            ButtonSection()
        }
    }
}

@Composable
fun BottomSection(
    modifier: Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
//        item {
//            TodayScheduleSection()
//        }
    }
}

/**
 * 헤더
 */
@Composable
fun HeaderRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "logo"
        )

        Text(
            text = "Miruni",
            fontSize = 18.sp,
//            fontFamily = FontFamily.Alexandria
            modifier = Modifier
                .padding(start = 8.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        Image(
            painter = painterResource(id = R.drawable.peanut),
            contentDescription = "peanut"
        )
        Text(
            text = "0",
            style = AppTypography.header_bold_16,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.bell),
            contentDescription = "bell"
        )
    }
}

/**
 * 메인 설명 영역
 */
@Composable
fun DescriptionSection(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(270.dp)
            .padding(bottom = 26.dp)
    ) {

        Text(
            text = convertBold("'가영'님,\n오늘은 더 나은 하루가 될거예요.\n'미루니가 함께해요!'"),
            style = AppTypography.pretendard_medium,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 40.dp)
        )

        // Miruni 이미지
        Column (
            modifier = Modifier
                .align(Alignment.BottomEnd),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.miruni_basic),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.miruni_shadow),
                contentDescription = null,
                modifier = Modifier
                    .width(57.dp)
                    .height(12.dp)
                    .offset(x = 20.dp)
            )
        }
    }
}

/**
 * 진행률 박스
 */
@Composable
fun ProgressBarSection(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.White, shape = RoundedCornerShape(10.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = "진행률",
                color = Color.Black,
                style = AppTypography.sub_bold_14
            )
            Text(
                text = "오늘 목표의 " + (achievementRate * 100).toInt() + "%를 달성했어요!",
                color = Gray.gray_500,
                style = AppTypography.description_regular_9
            )

            Spacer(modifier = Modifier.height(16.dp))

            LinearProgressBar(
                progress = achievementRate,
                modifier = Modifier
                    .fillMaxWidth(),
                height = 12.dp,
                progressColor = MainColor.miruni_green,
                backgroundColor = Color(0xFFE5E7EB)
            )
        }
    }
}

@Composable
fun ButtonSection(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 17.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .height(126.dp)
                .background(color = Color(0xFFB9E8C6), shape = RoundedCornerShape(10.dp))
                .padding(start = 25.dp, end = 15.dp, top = 15.dp, bottom = 15.dp)
        ) {
            Column(
                modifier = Modifier.align(Alignment.TopStart),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.miruni_pencil),
                    contentDescription = "AI Planner",
                    modifier = Modifier
                        .width(65.dp)
                        .height(52.dp)
                )
                Image(
                    painter = painterResource(R.drawable.miruni_shadow),
                    contentDescription = null,
                    modifier = Modifier
                        .width(30.dp)
                        .height(5.dp)
                        .offset(x = (-10).dp)
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "AI 플래너 바로가기",
                style = AppTypography.pretendard_bold_10,
                color = Gray.gray_700,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
            )
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .height(126.dp)
                .background(color = Color(0xFFB9E8C6), shape = RoundedCornerShape(10.dp))
                .padding(start = 25.dp, end = 15.dp, top = 15.dp, bottom = 15.dp)
        ) {
            Column(
                modifier = Modifier.align(Alignment.TopStart),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.miruni_lock),
                    contentDescription = "AI Planner",
                    modifier = Modifier
                        .width(65.dp)
                        .height(52.dp)
                )
                Image(
                    painter = painterResource(R.drawable.miruni_shadow),
                    contentDescription = null,
                    modifier = Modifier
                        .width(30.dp)
                        .height(5.dp)
                        .offset(x = (-10).dp)
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "방해금지 모드 바로가기",
                style = AppTypography.pretendard_bold_10,
                color = Gray.gray_700,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    MiruniTheme {
        HomeScreen(navController = rememberNavController())
    }
}
package com.miruni.feature.home.dnd

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.miruni.core.designsystem.AppTypography
import com.miruni.core.designsystem.Gray
import com.miruni.core.designsystem.MainColor
import com.miruni.core.designsystem.MiruniTheme
import com.miruni.core.designsystem.MiruniTypography
import com.miruni.core.navigation.MiruniRoute
import com.miruni.feature.home.R
import com.miruni.feature.home.dnd.component.DndTopBar
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DndTimerRunningScreen(
    hour: Int,
    minute: Int,
    navController: NavHostController,
) {
    var currentHour by remember { mutableStateOf(hour) }
    var currentMinute by remember { mutableStateOf(minute) }

    LaunchedEffect(Unit) {
        while (currentHour > 0 || currentMinute > 0) {
            delay(60_000L)

            if (currentMinute > 0) {
                currentMinute--
            } else {
                currentHour--
                currentMinute = 59
            }
        }
    }

    LaunchedEffect(currentHour, currentMinute) {
        if (currentHour == 0 && currentMinute == 0) {
            navController.navigate(
                MiruniRoute.HomeDndComplete.createRoute(
                    hour = hour,
                    minute = minute
                )
            ) {
                popUpTo(MiruniRoute.HomeDndTimerRunning.route) {
                    inclusive = true
                }
            }
        }
    }

    Scaffold(
        topBar = {
            DndTopBar(onClose = {
                navController.navigate(MiruniRoute.Home.route) {
                    popUpTo(MiruniRoute.Home.route) {
                        inclusive = false
                    }
                    launchSingleTop = true
                }
            })
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFFFFFF))
                .padding(horizontal = 20.dp)
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(20.dp))
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color(0x1A24C354)
                ),
                modifier = Modifier
                    .height(65.dp)
            ) {
                Log.d("DndTimerSet", "Top Card rendered")

                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "자극 멘트",
                        textAlign = TextAlign.Center,
                        style = AppTypography.body_regular_14
                    )
                }
            }

            Spacer(Modifier.height(60.dp))

            Log.d("DndTimerSet", "Main Image rendered")
            Image(
                painter = painterResource(id = R.drawable.miruni_basic),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(126.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .padding(top = 50.dp),
                    style = MiruniTypography.displayMedium,
                    color = MainColor.miruni_green,
                    text = "%02d".format(currentHour),
                    fontSize = 48.sp
                )
                Spacer(Modifier.width(10.dp))
                Text(
                    modifier = Modifier
                        .padding(top = 50.dp),
                    style = MiruniTypography.displayMedium,
                    color = Color.Black,
                    text = ":",
                    fontSize = 48.sp
                )
                Spacer(Modifier.width(10.dp))
                Text(
                    modifier = Modifier
                        .padding(top = 50.dp),
                    style = MiruniTypography.displayMedium,
                    color = MainColor.miruni_green,
                    text = "%02d".format(currentMinute),
                    fontSize = 48.sp
                )
            }

            Spacer(Modifier.height(80.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
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
                        Log.d("DndTimerSet", "Stop clicked")
                        // TODO: 중지 버튼 클릭 시 일정 실행 중지 screen 으로 이동
                        navController.navigate(MiruniRoute.HomeDndPause.route)
                    }
                ) {
                    Text(
                        text = "중지",
                        style = AppTypography.button_semibold_16
                    )
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
                        navController.navigate(
                            MiruniRoute.HomeDndEarlyEnd.createRoute(
                                hour = currentHour,
                                minute = currentMinute
                            )
                        )
                    }
                ) {
                    Text(
                        text = "완료",
                        style = AppTypography.button_semibold_16
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DndTimerRunningScreenPreview() {
    MiruniTheme {
        DndTimerRunningScreen(
            hour = 0,
            minute = 0,
            navController = rememberNavController()
        )
    }
}
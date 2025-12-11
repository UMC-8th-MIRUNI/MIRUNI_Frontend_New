package com.miruni.feature.home.dnd

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.miruni.core.designsystem.AppTypography
import com.miruni.core.designsystem.MiruniTheme
import com.miruni.feature.home.R
import com.miruni.feature.home.dnd.component.DndTopBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import com.miruni.core.designsystem.MainColor
import com.miruni.feature.home.dnd.component.InputTimeView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DndTimerSetScreen(
    navController: NavHostController,
    onConfirm: (hour: Int, minute: Int) -> Unit
) {
    val timePickerState = rememberTimePickerState(is24Hour = true)

    // 시간 확정 여부
    var isTimeConfirmed by remember { mutableStateOf(false) }
    // 포맷된 시간
    val selectedTimeText = "%02d:%02d".format(timePickerState.hour, timePickerState.minute)

    // 화면 재구성 로그
    Log.d("DndTimerSet", "Composable Recomposition. isTimeConfirmed=$isTimeConfirmed, time=$selectedTimeText")

    // 시간 변경 로그
    LaunchedEffect(timePickerState.hour, timePickerState.minute) {
        Log.d("DndTimerSet", "Time changed -> ${timePickerState.hour}:${timePickerState.minute}")
    }

    Surface(
        color = Color.Transparent,
        modifier = Modifier.navigationBarsPadding()
    ) { }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            DndTopBar(onClose = {
                Log.d("DndTimerSet", "TopBar Close clicked")
                navController.popBackStack()
            })

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .height(65.dp)
                    .shadow(
                        elevation = 10.dp,
                        ambientColor = Color(0x1A24C354),
                        shape = RoundedCornerShape(10.dp)
                    )
            ) {
                Log.d("DndTimerSet", "Top Card rendered")

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("다른 어플 사용을 제한")
                            }
                            append("하고, 할 일에 더 집중해보세요.")
                        },
                        textAlign = TextAlign.Center,
                        style = AppTypography.body_regular_14
                    )
                }
            }
            Log.d("DndTimerSet", "Main Image rendered")
            Image(
                painter = painterResource(id = R.drawable.miruni_basic),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(start = 100.dp, end = 100.dp, top = 100.dp, bottom = 100.dp)
                    .size(126.dp)
            )

            if (!isTimeConfirmed) {
                Log.d("DndTimerSet", "Showing InputTimeView")
                InputTimeView(timePickerState = timePickerState)
            } else {
                Log.d("DndTimerSet", "Showing Confirmed Time Text: $selectedTimeText")
                Text(
                    text = "$selectedTimeText",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier
                        .padding(horizontal = 12.dp),
                    color = MainColor.miruni_green
                )
            }

            if (!isTimeConfirmed) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(10.dp),
                    onClick = {
                        isTimeConfirmed = true
                        Log.d("DndTimerSet", "Confirm clicked -> hour=${timePickerState.hour}, minute=${timePickerState.minute}")
                        onConfirm(timePickerState.hour, timePickerState.minute)
                    }
                ) {
                    Text(text = "확인")
                }
            } else {
                Log.d("DndTimerSet", "Showing Stop/Complete buttons")
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 20.dp),
                        shape = RoundedCornerShape(10.dp),
                        onClick = {
                            Log.d("DndTimerSet", "Stop clicked")
                            // TODO: 중지 버튼 클릭 처리
                        }
                    ) {
                        Text("중지")
                    }

                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 20.dp),
                        shape = RoundedCornerShape(10.dp),
                        onClick = {
                            Log.d("DndTimerSet", "Complete clicked")
                            // TODO: 완료 버튼 클릭 처리
                        }
                    ) {
                        Text("완료")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DndTimerSetCompleteScreen(
    navController: NavHostController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
    ) {

    }
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun DndTimerSetScreenPreview() {
    MiruniTheme {
        DndTimerSetScreen(
            navController = rememberNavController(),
            onConfirm = { _, _ -> }
        )
    }
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun DndTimerSetCompleteScreenPreview() {

}
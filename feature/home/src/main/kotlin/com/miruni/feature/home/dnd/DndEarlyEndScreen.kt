package com.miruni.feature.home.dnd

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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.miruni.core.designsystem.AppTypography
import com.miruni.core.designsystem.Gray
import com.miruni.core.designsystem.MiruniTheme
import com.miruni.core.navigation.MiruniRoute
import com.miruni.feature.home.R
import com.miruni.feature.home.dnd.component.CancelOrConfirmButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DndEarlyEndScreen(
    hour: Int,
    minute: Int,
    navController: NavController
) {
    var currentHour by remember { mutableStateOf(hour) }
    var currentMinute by remember { mutableStateOf(minute) }

    Scaffold(
        bottomBar = {
            Column {
                CancelOrConfirmButton(
                    onCancelClick = {
                        navController.popBackStack()
                    },
                    onConfirmClick = {
                        navController.navigate(
                            MiruniRoute.HomeDndComplete.createRoute(
                                hour = currentHour,
                                minute = currentMinute
                            )
                        )
                    }
                )
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    ) { innerPadding ->
        Column (
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
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(start = 100.dp, end = 100.dp, top = 100.dp, bottom = 100.dp)
                    .size(126.dp)
            )
            Text(
                modifier = Modifier,
                style = AppTypography.header_bold_20,
                color = Color.Black,
                text = "벌써 다 끝내셨나요?",
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
}

@Preview(showBackground = true)
@Composable
fun DndEarlyEndScreenPreview() {
    MiruniTheme {
        DndEarlyEndScreen(
            hour = 0,
            minute = 0,
            navController = rememberNavController()
        )
    }
}
package com.miruni.feature.home.dnd.component.screen

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.miruni.core.designsystem.AppTypography
import com.miruni.core.designsystem.Gray
import com.miruni.core.designsystem.MainColor
import com.miruni.feature.home.R
import com.miruni.feature.home.dnd.component.button.RowButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PauseOrEarlyEndScreen(
    hour: Int,
    minute: Int,
    title: String,
    subDescription : String,
    navController: NavController,
    onClickButton1: () -> Unit,
    onClickButton2: () -> Unit,
) {

    Scaffold(
        bottomBar = {
            Column {
                RowButton(
                    onClickButton1 = { onClickButton1() },
                    onClickButton2 = { onClickButton2() },
                    text1 = "취소",
                    text2 = "확인",
                    button1Color = MainColor.miruni_green,
                    button2Color = Gray.gray_500
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
                text = title,
            )

            Spacer(Modifier.height(16.dp))

            Text(
                modifier = Modifier,
                style = AppTypography.sub_medium_14,
                color = Gray.gray_500,
                textAlign = TextAlign.Center,
                text = subDescription,
            )
        }
    }
}

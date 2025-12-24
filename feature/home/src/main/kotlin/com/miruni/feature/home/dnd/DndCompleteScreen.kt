package com.miruni.feature.home.dnd

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.miruni.core.designsystem.Gray
import com.miruni.core.designsystem.MiruniTheme

@Composable
fun DndCompleteScreen(
    hour: Int,
    minute: Int,
    navController: NavHostController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF24C354)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = com.miruni.feature.home.R.drawable.spring),
            contentDescription = null,
            modifier = Modifier
                .size(width = 600.dp, height = 400.dp)
                .offset(x = 100.dp, y = (-300).dp)
        )
        Image(
            painter = painterResource(id = com.miruni.feature.home.R.drawable.spring),
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
                painter = painterResource(id = com.miruni.feature.home.R.drawable.miruni_success),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(250.dp)
            )

            Spacer(modifier = Modifier.height(120.dp))

            Text(
                text = "%02d:%02d".format(hour, minute),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
                fontSize = 50.sp,
                color = Color(0xFFFFF608)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "만에\n성장 완료!",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(60.dp))

            Button(
                modifier = Modifier
                    .height(49.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Gray.gray_300
                ),
                onClick = {
                    navController.navigate("home")
                }
            ) {
                Text(
                    text = "확인",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DndCompleteScreenPreview() {
    MiruniTheme {
        DndCompleteScreen(
            hour = 0,
            minute = 0,
            navController = rememberNavController()
        )
    }
}
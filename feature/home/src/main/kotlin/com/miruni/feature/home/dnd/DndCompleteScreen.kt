package com.miruni.feature.home.dnd

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.miruni.core.designsystem.MiruniTheme

@Composable
fun DndCompleteScreen(
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
        Image(
            painter = painterResource(id = com.miruni.feature.home.R.drawable.miruni_success),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(start = 100.dp, end = 100.dp, bottom = 200.dp)
                .size(250.dp)
        )
        Text(
            modifier = Modifier
                .padding(top = 200.dp),
            text = "00:00",
            textAlign = TextAlign.Center,
            fontSize = 50.sp,
            color = Color(0xFFFFF608)
        )
        Text(
            modifier = Modifier
                .padding(top = 300.dp),
            text = buildAnnotatedString {
                append("만에")
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                ) {
                    append("\n성장 완료!")
                }
            },
            textAlign = TextAlign.Center
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, top = 700.dp, end = 20.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0XFFF3F3F3)
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

@Preview(showBackground = true)
@Composable
fun DndCompleteScreenPreview() {
    MiruniTheme {
        DndCompleteScreen(
            navController = rememberNavController()
        )
    }
}
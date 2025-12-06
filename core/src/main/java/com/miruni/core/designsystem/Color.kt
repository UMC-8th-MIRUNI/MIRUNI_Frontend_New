package com.miruni.core.designsystem

import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

val White = Color(0xFFF7F7F7)
val Black = Color(0xFF040404)
val Black_28 = Color(0xFF282828)
val Red = Color(0xFFDD5E3B)

object MainColor {
    val miruni_green = Color(0xFF6ABE61)
    val alpha_10 = Color(0xFFECF6ED)
    val alpha_50 = Color(0xFFDAF0DD)
    /** 텍스트 입력 시 */
    val input_focus = Color(0xFFF3FCF2)
}

object StrokeColor {
    val dark = Color(0xFF787878)
}

object Yellow {
    val yellow = Color(0xFFF3E54F)
    val kakao = Color(0xFFF4F359)
}

object Gray {
    val gray_300 = Color(0xFFF1F1F1)
    val gray_400 = Color(0xFFD5D5D5)
    val gray_500 = Color(0xFFB2B2B2)
    val gray_700 = Color(0xFF616161)
    val background_gray = Color(0xFFF4F4F4)
}

// Preview
@Preview(showBackground = true, heightDp = 1500, widthDp = 800)
@Composable
fun ColorSystemPreview() {
    Column(
        modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
    ) {
       Column {
            Text("Main Colors")
            ColorRow("miruni_green", MainColor.miruni_green)
            ColorRow("alpha_10", MainColor.alpha_10)
            ColorRow("alpha_50", MainColor.alpha_50)
            ColorRow("input_focus", MainColor.input_focus)

            Spacer(modifier = Modifier.height(16.dp))
            Text("Sub Primary Colors")

            Spacer(modifier = Modifier.height(16.dp))
            Text("Stroke Colors")
            ColorRow("dark", StrokeColor.dark)

            Spacer(modifier = Modifier.height(16.dp))
            Text("Gray")
            ColorRow("gray_300", Gray.gray_300)
            ColorRow("gray_400", Gray.gray_400)
            ColorRow("gray_500", Gray.gray_500)
            ColorRow("gray_700", Gray.gray_700)
            ColorRow("background_gray", Gray.background_gray)

            Spacer(modifier = Modifier.height(16.dp))
            Text("Yellow")
            ColorRow("yellow", Yellow.yellow)
            ColorRow("kakao", Yellow.kakao)
       }
    }
}

@Composable
fun ColorRow(
    name: String,
    color: Color,
) {
    Row(
        modifier = Modifier
                .width(400.dp)
                .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                    .size(40.dp)
                    .background(color = color)
                    .border(1.dp, Color.Gray),
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(name)
    }
}
package com.miruni.feature.mypage.account

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.miruni.core.designsystem.AppTypography
import com.miruni.core.designsystem.MainColor
import com.miruni.core.designsystem.MiruniTheme
import com.miruni.core.designsystem.MiruniTypography

@Composable
fun SettingAccountScreen(
    modifier: Modifier = Modifier
) {
    var text by remember { mutableStateOf("") }

    Scaffold(
        containerColor = Color(0xFFF6F5F6),
        topBar = {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = "back"
                )

                Spacer(modifier = modifier.weight(1f))

                Text(
                    text = "계정 설정",
                    style = MiruniTypography.titleMedium
                )

                Spacer(modifier = modifier.weight(1f))

                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "option"
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(20.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "이름",
                style = AppTypography.sub_bold_14
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = "김가영",
                modifier = modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                onValueChange = { text = it },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MainColor.miruni_green,
                    unfocusedBorderColor = Color(0xFFF1ECEC)
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "생년월일",
                style = AppTypography.sub_bold_14
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = "2003.12.20",
                modifier = modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                onValueChange = { text = it },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MainColor.miruni_green,
                    unfocusedBorderColor = Color(0xFFF1ECEC)
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "전화번호",
                style = AppTypography.sub_bold_14
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = "010-8991-3803",
                modifier = modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                onValueChange = { text = it },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MainColor.miruni_green,
                    unfocusedBorderColor = Color(0xFFF1ECEC)
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "이메일",
                style = AppTypography.sub_bold_14
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = "aa@gmail.com",
                modifier = modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                onValueChange = { text = it },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MainColor.miruni_green,
                    unfocusedBorderColor = Color(0xFFF1ECEC)
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "설문조사",
                style = AppTypography.sub_bold_14
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = "설문조사 내역 수정하기",
                modifier = modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                onValueChange = { text = it },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = "edit survey",
                        modifier = modifier
                            .padding(4.dp)
                            .clickable { }
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MainColor.miruni_green,
                    unfocusedBorderColor = Color(0xFFF1ECEC)
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = "로그아웃",
                modifier = modifier.fillMaxWidth(),
                onValueChange = { text = it },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MainColor.miruni_green,
                    unfocusedBorderColor = Color(0xFFF1ECEC)
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = "탈퇴하기",
                modifier = modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                onValueChange = { text = it },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MainColor.miruni_green,
                    unfocusedBorderColor = Color(0xFFF1ECEC)
                )
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun SettingAccountScreenPreview() {
    MiruniTheme {
        SettingAccountScreen()
    }
}
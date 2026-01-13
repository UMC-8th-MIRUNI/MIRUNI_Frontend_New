package com.miruni.feature.mypage.notification

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
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
import com.miruni.core.designsystem.Gray
import com.miruni.core.designsystem.MiruniTheme
import com.miruni.core.designsystem.MiruniTypography

@Composable
fun SettingNotificationScreen(
    modifier: Modifier = Modifier
) {
    var checked by remember { mutableStateOf(false) }

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
                    text = "알림 설정",
                    style = MiruniTypography.titleMedium
                )

                Spacer(modifier = modifier.weight(1f))
            }
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(20.dp)
        ) {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    Color.White
                )
            ) {
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Text(
                        text = "일정 리마인드 알림",
                        style = AppTypography.sub_bold_14
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "일정 시작 5분 전과 10분 전에 리마인드 알림이 발송돼요.",
                        style = AppTypography.button_regular_9,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    HorizontalDivider(
                        thickness = 1.dp,
                        color = Gray.gray_300,
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "5분 전",
                            style = AppTypography.body_regular_12
                        )

                        Spacer(modifier = modifier.weight(1f))

                        Switch(
                            checked = checked,
                            onCheckedChange = {
                                checked = it
                            }
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "10분 전",
                            style = AppTypography.body_regular_12
                        )

                        Spacer(modifier = modifier.weight(1f))

                        Switch(
                            checked = checked,
                            onCheckedChange = {
                                checked = it
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    Color.White
                )
            ) {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column(
                        modifier = modifier
                    ) {
                        Text(text = "실행 유도 팝업 알림",
                            style = AppTypography.sub_bold_14
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            modifier = modifier
                                .width(160.dp),
                            maxLines = 2,
                            text = "일정을 시작할 시간에 팝업 알림이 발송되어,\n즉시 실행을 유도헤줘요.",
                            style = AppTypography.button_regular_9,
                            color = Color.Gray
                            )
                    }

                    Spacer(modifier = modifier.weight(1f))

                    Switch(
                        checked = checked,
                        onCheckedChange = {
                            checked = it
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    Color.White
                )
            ) {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column(
                        modifier = modifier
                    ) {
                        Text(text = "실행 잔소리 알림",
                            style = AppTypography.sub_bold_14
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            modifier = modifier
                                .width(160.dp),
                            maxLines = 2,
                            text = "일정을 시작하지 않고 미루고 있을 때,\n미루니의 잔소리 알림이 발송돼요.",
                            style = AppTypography.button_regular_9,
                            color = Color.Gray
                        )
                    }

                    Spacer(modifier = modifier.weight(1f))

                    Switch(
                        checked = true,
                        onCheckedChange = {
                            checked = it
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(60.dp))

            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    Color.White
                )
            ) {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column(
                        modifier = modifier
                    ) {
                        Text(text = "마케팅 정보 수신 동의",
                            style = AppTypography.sub_bold_14
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            modifier = modifier
                                .width(220.dp),
                            text = "이메일, 푸시 알림 등을 통해 이벤트, 혜택, 신규 서비스 소식을\n받아볼 수 있습니다.\n-수신 채널: 이메일, 앱 푸시, 문자(SMS)\n-수신 목적: 이벤트, 혜택, 프로모션 안내 등\n동의하지 않아도 서비스 이용에는 제한이 없습니다.",
                            style = AppTypography.button_regular_9,
                            color = Color.Gray
                        )
                    }

                    Spacer(modifier = modifier.weight(1f))

                    Switch(
                        checked = true,
                        onCheckedChange = {
                            checked = it
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingNotificationScreenPreview() {
    MiruniTheme {
        SettingNotificationScreen()
    }
}
package com.miruni.feature.login.component.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.miruni.core.designsystem.AppTypography
import com.miruni.core.designsystem.Gray
import com.miruni.core.designsystem.White
import com.miruni.feature.login.R
import com.miruni.feature.login.component.MiruniButton

@Composable
fun NotificationScreen(
    isDialogOpen: Boolean,
    onOpenDialog: () -> Unit,
    onCloseDialog: () -> Unit,
    onNextClicked: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        NotificationHeader(
            onPrevClicked = {}
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.img_notification),
                contentDescription = "알람",
                modifier = Modifier.size(width = 174.dp, height = 163.dp)
            )
            Spacer(modifier = Modifier.height(28.dp))
            Text(
                "더 나은 MIRUNI 사용을 위해",
                style = AppTypography.header_bold_20
            )
            Spacer(modifier = Modifier.height(14.dp))
            Text(
                "알림 허용이 필요해요!",
                style = AppTypography.sub_medium_14
            )
        }
        Spacer(modifier = Modifier.height(48.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .padding(horizontal = 20.dp)
        ) {
            Text("알림을 켜 이런 점이 좋아요", style = AppTypography.sub_bold_14)
            Spacer(modifier = Modifier.height(34.dp))
            Text(
                "일정을 깜빡하지 않도록 미루니가 알려줘요.\n" +
                        "해야 할 시간이 되면, 미루니가 나타나 시작을 도와줘요.\n" +
                        "미루고 있을 땐, 힘낼 수 있도록 동기부여를 해줘요.",
                style = AppTypography.body_regular_14,
                color = Gray.gray_700
            )
            Spacer(modifier = Modifier.height(18.dp))
            Text(
                "언제든지 설정에서 알림을 끌 수 있으니 걱정 마세요!",
                style = AppTypography.sub_bold_14,
                color = Gray.gray_700
            )
            Spacer(modifier = Modifier.height(76.dp))
            MiruniButton(
                text = "확인",
                onClick = onOpenDialog
            )
        }
    }
    if (isDialogOpen) {
        NotificationPermissionDialog(
            onDismiss = onCloseDialog,
            onYes = onNextClicked
        )
    }
}

@Composable
private fun NotificationHeader(
    modifier: Modifier = Modifier,
    onPrevClicked: () -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onPrevClicked
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_chevron_right),
                contentDescription = "뒤로 가기"
            )
        }
    }
}

@Composable
private fun NotificationPermissionDialog(
    onDismiss: () -> Unit,
    onYes: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(fraction = 0.9f)
                .height(250.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(White)
                .padding(horizontal = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(27.dp))
            Icon(
                painter = painterResource(R.drawable.ic_bell),
                contentDescription = "벨",
                tint = Gray.gray_700
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                "[MIRUNI]가 알림을 보내도록\n허용하시겠습니까?",
                style = AppTypography.header_bold_16,
                textAlign = TextAlign.Center,
                lineHeight = 29.sp
            )
            Spacer(modifier = Modifier.height(52.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                MiruniButton(
                    modifier = Modifier.weight(1f),
                    text = "아니요",
                    onClick = onDismiss
                )
                MiruniButton(
                    modifier = Modifier.weight(1f),
                    text = "예",
                    onClick = onYes
                )
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NotificationScreenPreview() {
    NotificationScreen(
        onNextClicked = {},
        isDialogOpen = true,
        onOpenDialog = {},
        onCloseDialog = {})
}
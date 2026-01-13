package com.miruni.feature.mypage

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.miruni.core.designsystem.AppTypography
import com.miruni.core.designsystem.Gray
import com.miruni.core.designsystem.MainColor
import com.miruni.core.designsystem.MiruniTheme
import com.miruni.core.designsystem.MiruniTypography
import com.miruni.core.navigation.MiruniRoute

@Composable
fun MyPageRoute(
    viewModel: MyPageViewModel = hiltViewModel(),
    navController: NavController,

    ) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                MyPageContract.Effect.NavigateToSettingAccount ->
                    navController.navigate(
                        MiruniRoute.MyPageSettingAccount
                    )

                MyPageContract.Effect.NavigateToSettingNotification ->
                    navController.navigate(
                        MiruniRoute.MyPageSettingNotification
                    )

                MyPageContract.Effect.NavigateToInfo ->
                    navController.navigate(
                        MiruniRoute.MyPageInfo
                    )
            }
        }
    }
    MyPageScreen(
        onEvent = viewModel::setEvent,
        onSettingAccountClick = { viewModel.setEvent(MyPageContract.Event.OnSettingAccountClick) },
        onSettingNotificationClick = { viewModel.setEvent(MyPageContract.Event.OnSettingNotificationClick) },
        onInfoClick = { viewModel.setEvent(MyPageContract.Event.OnInfoClick) }
    )
}


@Composable
fun MyPageScreen(
    onSettingAccountClick: () -> Unit,
    onSettingNotificationClick: () -> Unit,
    onInfoClick: () -> Unit,
    modifier: Modifier = Modifier,
    onEvent: (MyPageContract.Event) -> Unit
) {
    Scaffold(
        containerColor = MainColor.miruni_green,
        topBar = {
            Row(
                modifier = modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    onClick = { onEvent(MyPageContract.Event.OnTopBarEditClick) }
                ) {
                    Icon(
                        modifier = modifier
                            .padding(4.dp),
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = "edit"
                    )
                }
                IconButton(
                    onClick = { onEvent(MyPageContract.Event.OnTopBarNotificationClick) }
                ) {
                    Icon(
                        modifier = modifier
                            .padding(4.dp),
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = "notifications"
                    )
                }
            }
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(120.dp))

                Image(
                    painter = painterResource(R.drawable.janet),
                    contentDescription = "profile",
                    modifier = modifier
                        .size(87.dp),
                )

                Spacer(modifier = Modifier.height(28.dp))

                Text(
                    text = "닉네임",
                    style = MiruniTypography.titleMedium
                )

                Text(
                    text = "aaa@gmail.com",
                    style = AppTypography.body_regular_14,
                    color = Gray.gray_700
                )
            }
            // setting content
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(440.dp),
                color = Color.White,
                shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                ) {
                    Text(
                        text = "설정",
                        style = MiruniTypography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(28.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                border = BorderStroke(1.dp, Gray.gray_300),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .padding(24.dp)
                            .clickable(onClick = onSettingAccountClick),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Person,
                            contentDescription = "setting account",
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = "계정 설정",
                            textAlign = TextAlign.Justify,
                            style = AppTypography.body_regular_14
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                border = BorderStroke(1.dp, Gray.gray_300),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .padding(24.dp)
                            .clickable(onClick = onSettingAccountClick),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Notifications,
                            contentDescription = "setting notification",
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = "알림 설정",
                            style = AppTypography.body_regular_14
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                border = BorderStroke(1.dp, Gray.gray_300),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .padding(24.dp)
                            .clickable(onClick = onSettingAccountClick),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = "information",
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = "문의 및 정보",
                            style = AppTypography.body_regular_14
                        )
                    }
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun MyPageScreenPreview() {
    MiruniTheme {
        MyPageScreen(
            onEvent = {},
            onSettingAccountClick = {},
            onSettingNotificationClick = {},
            onInfoClick = {}
        )
    }
}
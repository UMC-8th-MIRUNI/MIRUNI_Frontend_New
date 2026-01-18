package com.miruni.feature.mypage

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
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
    navController: NavController
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                MyPageContract.Effect.Navigation.NavigateToSettingAccount ->
                    navController.navigate(
                        MiruniRoute.MyPageSettingAccount.route
                    )

                MyPageContract.Effect.Navigation.NavigateToSettingNotification ->
                    navController.navigate(
                        MiruniRoute.MyPageSettingNotification.route
                    )

                MyPageContract.Effect.Navigation.NavigateToInfo ->
                    navController.navigate(
                        MiruniRoute.MyPageInfo.route
                    )
            }
        }
    }

    MyPageScreen(
        state = state,
        onEvent = viewModel::setEvent
    )
}

@Composable
fun MyPageScreen(
    state: MyPageContract.State,
    onEvent: (MyPageContract.Event) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        containerColor = MainColor.miruni_green,
        topBar = {
            TopSection(
                modifier = modifier.wrapContentHeight(),
                state = state,
                onEvent = onEvent
            )
        },
        bottomBar = {
            BottomSection(
                state = state,
                onEvent = onEvent,
            )
        }
    ) { innerPadding ->
        MyPageContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding()),
        )
    }
}

@Composable
fun MyPageContent(
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
    ) {
//        item {
//            TopSection(
//                modifier = modifier.wrapContentHeight(),
//                state = state,
//                viewModel = viewModel
//            )
//        }
    }
}

@Composable
private fun TopSection(
    modifier: Modifier,
    state: MyPageContract.State,
    onEvent: (MyPageContract.Event) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                onClick = {
                    Log.d("MyPageScreen", "Edit Clicked")
                    onEvent(MyPageContract.Event.OnTopBarEditClick)
                },
                enabled = !state.isEditMode,
            ) {
                Icon(
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = "edit",
                    modifier = Modifier.padding(4.dp)
                )
            }

            IconButton(
                onClick = {
                    Log.d("MyPageScreen", "Edit Clicked")
                    onEvent(MyPageContract.Event.OnTopBarNotificationClick)
                },
                enabled = !state.isEditMode,
            ) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = "notifications",
                    modifier = Modifier.padding(4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val images = state.profileImages
            val current = state.selectedProfileImageIndex
            val size = images.size

            val prevIndex = (current - 1 + size) % size
            val nextIndex = (current + 1) % size

            if (state.isEditMode) {
                Image(
                    painter = painterResource(images[prevIndex].resId),
                    contentDescription = "profile",
                    modifier = modifier
                        .size(87.dp),
                )

                IconButton(
                    onClick = {
                        onEvent(MyPageContract.Event.OnProfileImagePrevClick)
                    }
                ) {
                    Icon(Icons.Default.KeyboardArrowLeft, "profile prev click")
                }
            }

            Image(
                painter = painterResource(images[current].resId),
                contentDescription = "profile",
                modifier = modifier
                    .size(87.dp),
            )

            if (state.isEditMode) {
                IconButton(
                    onClick = {
                        onEvent(MyPageContract.Event.OnProfileImageNextClick)
                    }
                ) {
                    Icon(Icons.Default.KeyboardArrowRight, "profile next click")
                }

                Image(
                    painter = painterResource(images[nextIndex].resId),
                    contentDescription = "profile",
                    modifier = modifier
                        .size(87.dp),
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // 닉네임
        if (state.isEditMode) {
            TextField(
                value = state.nickName,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                ),
                onValueChange = {
                    onEvent(MyPageContract.Event.OnNicknameChange(nickname = it))
                },
                singleLine = true,
                modifier = Modifier.width(108.dp),
                textStyle = TextStyle(textAlign = TextAlign.Center)
            )
        } else {
            Text(
                text = state.nickName,
                style = MiruniTypography.titleMedium,
                color = Color.Black
            )
        }

        if (!state.isEditMode) {
            Text(
                text = "aaa@gmail.com",
                style = AppTypography.body_regular_14,
                color = Gray.gray_700
            )
        }

        if (state.isEditMode) {
            TextButton(
                enabled = state.isCompleteEnabled,
                onClick = { onEvent(MyPageContract.Event.OnCompleteClick) }
            ) {
                Text(
                    text = "완료",
                    color = Color.White,
                    style = TextStyle(
                        textDecoration = TextDecoration.Underline
                    )
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(28.dp))
}

@Composable
private fun BottomSection(
    state: MyPageContract.State,
    onEvent: (MyPageContract.Event) -> Unit,
//    onSettingAccountClick: () -> Unit
) {
    // setting content
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(415.dp),
        color = Color.White,
        shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
        ) {
            Text(
                text = "설정",
                style = AppTypography.header_bold_16
            )

            Spacer(modifier = Modifier.height(28.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        border = BorderStroke(2.dp, Gray.gray_300),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(24.dp)
                    .clickable(
                        onClick = {
                            onEvent(MyPageContract.Event.OnSettingAccountClick)
                        }
                    ),
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
                        border = BorderStroke(2.dp, Gray.gray_300),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(24.dp)
                    .clickable(
                        onClick = {
                            onEvent(MyPageContract.Event.OnSettingNotificationClick)
                        }
                    ),
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
                        border = BorderStroke(2.dp, Gray.gray_300),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(24.dp)
                    .clickable(
                        onClick = {
                            onEvent(MyPageContract.Event.OnInfoClick)
                        }
                    ),
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

@Preview(showBackground = true)
@Composable
private fun MyPageScreenPreview() {
    MiruniTheme {
        MyPageScreen(
            state = MyPageContract.State(isEditMode = false),
            onEvent = {},
        )
    }
}
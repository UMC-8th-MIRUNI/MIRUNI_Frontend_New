package com.miruni.feature.mypage.account

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.miruni.core.designsystem.AppTypography
import com.miruni.core.designsystem.MainColor
import com.miruni.core.designsystem.MiruniTheme
import com.miruni.core.designsystem.MiruniTypography
import com.miruni.core.navigation.MiruniRoute
import com.miruni.feature.mypage.component.MyPageBottomBar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

private const val TAG = "SettingAccountScreen"

@Composable
fun SettingAccountScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: SettingAccountViewModel = hiltViewModel(),
) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()

    SettingAccountScreenContent(
        state = state,
        effect = viewModel.effect,
        onEvent = { viewModel.setEvent(it) },
        onBackClick = { navController.popBackStack() },
        onNavigateToSurvey = { navController.navigate(MiruniRoute.Survey.route) },
        modifier = modifier
    )
}

@Composable
fun SettingAccountScreenContent(
    state: SettingAccountContract.State,
    effect: Flow<SettingAccountContract.Effect>,
    onEvent: (SettingAccountContract.Event) -> Unit,
    onBackClick: () -> Unit,
    onNavigateToSurvey: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var menuExpanded by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(effect) {
        Log.d(TAG, "SettingAccountScreen LaunchedEffect - collecting effects")
        effect.collect { effect ->
            Log.d(TAG, "Effect received: $effect")
            when (effect) {
                is SettingAccountContract.Effect.Message.Toast -> {
                    Log.d(TAG, "Showing toast: ${effect.message}")
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }

                is SettingAccountContract.Effect.Message.Error -> {
                    Log.e(TAG, "Showing error: ${effect.message}")
                    Toast.makeText(context, effect.message, Toast.LENGTH_LONG).show()
                }

                SettingAccountContract.Effect.AccountUpdateSuccess -> {
                    Log.d(TAG, "Account update success")
                }
            }
        }
    }

    Scaffold(
        modifier = modifier,
        containerColor = Color(0xFFF6F5F6),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .testTag("topBar"),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(
                    onClick = {
                        Log.d(TAG, "Back button clicked")
                        onBackClick()
                    },
                    modifier = Modifier.testTag("backButton")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "back"
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "계정 설정",
                    style = MiruniTypography.titleMedium
                )

                Spacer(modifier = Modifier.weight(1f))

                // 수정 모드가 아닐 때만 메뉴 버튼 표시
                if (!state.isEditMode) {
                    IconButton(
                        onClick = {
                            Log.d(TAG, "Menu button clicked")
                            menuExpanded = true
                        },
                        modifier = Modifier.testTag("menuButton")
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "option"
                        )
                    }
                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text("수정하기")
                            },
                            onClick = {
                                Log.d(TAG, "Menu Edit clicked")
                                menuExpanded = false
                                onEvent(
                                    SettingAccountContract.Event.OnEditClick
                                )
                            },
                            modifier = Modifier.testTag("editMenuItem")
                        )
                    }
                } else {
                    // 수정 모드일 때 빈 공간 유지
                    Spacer(modifier = Modifier.size(48.dp))
                }
            }
        },
        bottomBar = {
            // 수정 모드일 때만 완료 버튼 표시
            if (state.isEditMode) {
                MyPageBottomBar(
                    canConfirm = state.isCompleteEnabled && !state.isLoading,
                    btnText = if (state.isLoading) "저장 중..." else "완료",
                    onConfirmClick = {
                        Log.d(TAG, "Complete button clicked")
                        onEvent(
                            SettingAccountContract.Event.OnCompleteClick
                        )
                    },
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.Start
            ) {
                // 이름
                Text(
                    text = "이름",
                    style = AppTypography.sub_bold_14
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = state.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("nameTextField"),
                    shape = RoundedCornerShape(12.dp),
                    onValueChange = { newValue ->
                        Log.d(TAG, "Name changed: $newValue")
                        onEvent(
                            SettingAccountContract.Event.OnNameChange(newValue)
                        )
                    },
                    enabled = state.isEditMode && !state.isLoading,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MainColor.miruni_green,
                        unfocusedBorderColor = Color(0xFFF1ECEC),
                        disabledBorderColor = Color(0xFFF1ECEC)
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 생년월일
                Text(
                    text = "생년월일",
                    style = AppTypography.sub_bold_14
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = state.birth,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("birthTextField"),
                    shape = RoundedCornerShape(12.dp),
                    onValueChange = { newValue ->
                        if (newValue.length <= 8) {
                            Log.d(TAG, "Birth changed: $newValue")
                            onEvent(
                                SettingAccountContract.Event.OnBirthChange(newValue)
                            )
                        }
                    },
                    label = { Text("생년월일(YYYYMMDD)") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    singleLine = true,
                    enabled = state.isEditMode && !state.isLoading,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MainColor.miruni_green,
                        unfocusedBorderColor = Color(0xFFF1ECEC),
                        disabledBorderColor = Color(0xFFF1ECEC)
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 전화번호
                Text(
                    text = "전화번호",
                    style = AppTypography.sub_bold_14
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = state.phoneNumber,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("phoneTextField"),
                    shape = RoundedCornerShape(12.dp),
                    onValueChange = { newValue ->
                        Log.d(TAG, "Phone changed: $newValue")
                        onEvent(
                            SettingAccountContract.Event.OnPhoneChange(newValue)
                        )
                    },
                    enabled = state.isEditMode && !state.isLoading,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MainColor.miruni_green,
                        unfocusedBorderColor = Color(0xFFF1ECEC),
                        disabledBorderColor = Color(0xFFF1ECEC)
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 이메일 (읽기 전용)
                Text(
                    text = "이메일",
                    style = AppTypography.sub_bold_14
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = state.email,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("emailTextField"),
                    shape = RoundedCornerShape(12.dp),
                    onValueChange = { },
                    readOnly = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MainColor.miruni_green,
                        unfocusedBorderColor = Color(0xFFF1ECEC),
                        disabledBorderColor = Color(0xFFF1ECEC)
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 설문조사
                Text(
                    text = "설문조사",
                    style = AppTypography.sub_bold_14
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = "설문조사 내역 수정하기",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            onClick = {
                                onNavigateToSurvey()
                            }
                        )
                        .testTag("surveyTextField"),
                    shape = RoundedCornerShape(12.dp),
                    onValueChange = { },
                    readOnly = true,
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = "edit survey",
                            modifier = Modifier
                                .padding(4.dp)
                                .clickable {
                                    Log.d(TAG, "Survey edit clicked")
                                    onNavigateToSurvey()
                                }
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MainColor.miruni_green,
                        unfocusedBorderColor = Color(0xFFF1ECEC)
                    )
                )

                Spacer(modifier = Modifier.height(4.dp))

                // 로그아웃
                OutlinedTextField(
                    value = "로그아웃",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = {
                            // TODO : Logout
                        })
                        .testTag("logoutTextField"),
                    onValueChange = { },
                    readOnly = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MainColor.miruni_green,
                        unfocusedBorderColor = Color(0xFFF1ECEC)
                    )
                )

                Spacer(modifier = Modifier.height(4.dp))

                // 탈퇴하기
                OutlinedTextField(
                    value = "탈퇴하기",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = {
                            // TODO : delete account
                        })
                        .testTag("withdrawTextField"),
                    shape = RoundedCornerShape(12.dp),
                    onValueChange = { },
                    readOnly = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MainColor.miruni_green,
                        unfocusedBorderColor = Color(0xFFF1ECEC)
                    )
                )

                // 에러 메시지
                state.errorMessage?.let { errorMessage ->
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        style = AppTypography.body_regular_14,
                        modifier = Modifier.testTag("errorMessage")
                    )
                }
            }

            // 로딩 오버레이
            if (state.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(48.dp)
                            .testTag("loadingIndicator"),
                        color = MainColor.miruni_green
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun SettingAccountScreenPreview() {
    MiruniTheme {
        SettingAccountScreenContent(
            state = SettingAccountContract.State(
                name = "홍길동",
                birth = "19900101",
                phoneNumber = "010-1234-5678",
                email = "test@test.com"
            ),
            effect = emptyFlow(),
            onEvent = {},
            onBackClick = {},
            onNavigateToSurvey = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingAccountScreenEditModePreview() {
    MiruniTheme {
        SettingAccountScreenContent(
            state = SettingAccountContract.State(
                name = "홍길동",
                birth = "19900101",
                phoneNumber = "010-1234-5678",
                email = "test@test.com",
                isEditMode = true
            ),
            effect = emptyFlow(),
            onEvent = {},
            onBackClick = {},
            onNavigateToSurvey = {}
        )
    }
}

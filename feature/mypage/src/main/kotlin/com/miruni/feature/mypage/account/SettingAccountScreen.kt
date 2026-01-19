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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.miruni.core.designsystem.AppTypography
import com.miruni.core.designsystem.MainColor
import com.miruni.core.designsystem.MiruniTheme
import com.miruni.core.designsystem.MiruniTypography
import com.miruni.feature.mypage.component.MyPageBottomBar

private const val TAG = "SettingAccountScreen"

@Composable
fun SettingAccountScreen(
    navController: NavHostController,
    viewModel: SettingAccountViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
) {
    var menuExpanded by remember { mutableStateOf(false) }
    val state by viewModel.viewState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        Log.d(TAG, "SettingAccountScreen LaunchedEffect - collecting effects")
        viewModel.effect.collect { effect ->
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
        containerColor = Color(0xFFF6F5F6),
        topBar = {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .testTag("topBar"),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(
                    onClick = {
                        Log.d(TAG, "Back button clicked")
                        navController.popBackStack()
                    },
                    modifier = Modifier.testTag("backButton")
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = "back"
                    )
                }

                Spacer(modifier = modifier.weight(1f))

                Text(
                    text = "계정 설정",
                    style = MiruniTypography.titleMedium
                )

                Spacer(modifier = modifier.weight(1f))

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
                                viewModel.setEvent(
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
                        viewModel.setEvent(
                            SettingAccountContract.Event.OnCompleteClick
                        )
                    }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.Start
            ) {
                // 이름
                Text(
                    text = "이름",
                    style = AppTypography.sub_bold_14
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = state.name,
                    modifier = modifier
                        .fillMaxWidth()
                        .testTag("nameTextField"),
                    shape = RoundedCornerShape(12.dp),
                    onValueChange = { newValue ->
                        Log.d(TAG, "Name changed: $newValue")
                        viewModel.setEvent(
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

                Spacer(modifier = Modifier.height(20.dp))

                // 생년월일
                Text(
                    text = "생년월일",
                    style = AppTypography.sub_bold_14
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = state.birth,
                    modifier = modifier
                        .fillMaxWidth()
                        .testTag("birthTextField"),
                    shape = RoundedCornerShape(12.dp),
                    onValueChange = { newValue ->
                        Log.d(TAG, "Birth changed: $newValue")
                        viewModel.setEvent(
                            SettingAccountContract.Event.OnBirthChange(newValue)
                        )
                    },
                    enabled = state.isEditMode && !state.isLoading,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MainColor.miruni_green,
                        unfocusedBorderColor = Color(0xFFF1ECEC),
                        disabledBorderColor = Color(0xFFF1ECEC)
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))

                // 전화번호
                Text(
                    text = "전화번호",
                    style = AppTypography.sub_bold_14
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = state.phoneNumber,
                    modifier = modifier
                        .fillMaxWidth()
                        .testTag("phoneTextField"),
                    shape = RoundedCornerShape(12.dp),
                    onValueChange = { newValue ->
                        Log.d(TAG, "Phone changed: $newValue")
                        viewModel.setEvent(
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

                Spacer(modifier = Modifier.height(20.dp))

                // 이메일 (읽기 전용)
                Text(
                    text = "이메일",
                    style = AppTypography.sub_bold_14
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = state.email,
                    modifier = modifier
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

                Spacer(modifier = Modifier.height(20.dp))

                // 설문조사
                Text(
                    text = "설문조사",
                    style = AppTypography.sub_bold_14
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = "설문조사 내역 수정하기",
                    modifier = modifier
                        .fillMaxWidth()
                        .testTag("surveyTextField"),
                    shape = RoundedCornerShape(12.dp),
                    onValueChange = { },
                    readOnly = true,
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = "edit survey",
                            modifier = modifier
                                .padding(4.dp)
                                .clickable {
                                    Log.d(TAG, "Survey edit clicked")
                                }
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MainColor.miruni_green,
                        unfocusedBorderColor = Color(0xFFF1ECEC)
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                // 로그아웃
                OutlinedTextField(
                    value = "로그아웃",
                    modifier = modifier
                        .fillMaxWidth()
                        .testTag("logoutTextField"),
                    onValueChange = { },
                    readOnly = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MainColor.miruni_green,
                        unfocusedBorderColor = Color(0xFFF1ECEC)
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                // 탈퇴하기
                OutlinedTextField(
                    value = "탈퇴하기",
                    modifier = modifier
                        .fillMaxWidth()
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
        SettingAccountScreen(
            navController = rememberNavController()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingAccountScreenEditModePreview() {
    MiruniTheme {
        // Preview with edit mode - would need custom state handling
        SettingAccountScreen(
            navController = rememberNavController()
        )
    }
}

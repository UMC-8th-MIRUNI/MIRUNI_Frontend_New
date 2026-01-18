package com.miruni.feature.mypage.account

import android.util.Log
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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.miruni.core.designsystem.AppTypography
import com.miruni.core.designsystem.MainColor
import com.miruni.core.designsystem.MiruniTheme
import com.miruni.core.designsystem.MiruniTypography
import com.miruni.feature.mypage.component.MyPageBottomBar

@Composable
fun SettingAccountScreen(
    navController: NavHostController,
    viewModel: SettingAccountViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
) {
    var menuExpanded by remember { mutableStateOf(false) }
    val state by viewModel.viewState.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = Color(0xFFF6F5F6),
        topBar = {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(
                    onClick = {
                        navController.popBackStack()
                    }
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

                IconButton(
                    onClick = {
                        menuExpanded = true
                    }
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
                            Log.d("SettingAccountScreen", "Menu Edit clicked")
                            menuExpanded = false
                            viewModel.setEvent(
                                SettingAccountContract.Event.OnEditClick
                            )
                        }
                    )
                }
            }
        },
        bottomBar = {
            if (state.isEditMode && !state.isSaving) {
                MyPageBottomBar(
                    canConfirm = true,
                    btnText = "완료",
                    onConfirmClick = {
                        viewModel.setEvent(
                            SettingAccountContract.Event.OnCompleteClick
                        )
                    }
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
                value = state.name,
                modifier = modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                onValueChange = {
                },
                enabled = state.isEditMode,
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
                value = state.birth,
                modifier = modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                onValueChange = {
                },
                enabled = state.isEditMode,
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
                value = state.phoneNumber,
                modifier = modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                onValueChange = {
                },
                enabled = state.isEditMode,
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
                value = state.email,
                modifier = modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                onValueChange = { },
                readOnly = true,
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
                onValueChange = { },
                readOnly = true,
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
                onValueChange = { },
                readOnly = true,
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
                onValueChange = { },
                readOnly = true,
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
        SettingAccountScreen(
            navController = rememberNavController()
        )
    }
}
package com.miruni.feature.mypage.info

import android.util.Log
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.miruni.core.designsystem.AppTypography
import com.miruni.core.designsystem.Gray
import com.miruni.core.designsystem.MiruniTheme
import com.miruni.feature.mypage.R
import com.miruni.feature.mypage.component.MyPageBottomBar
import com.miruni.feature.mypage.component.MyPageTopBar

private const val TAG = "WriteFeedbackScreen"

@Composable
fun WriteFeedbackScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onCameraClick: () -> Unit = {},
    onConfirmClick: () -> Unit = {}
) {

    var text by remember { mutableStateOf("") }
    var checkedState by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = Color(0xFFF6F5F6),
        topBar = {
            MyPageTopBar(
                text = "문의 및 피드백",
                onBackClick = {
                    Log.d(TAG, "Back button clicked")
                    navController.popBackStack()
                }
            )
        },
        bottomBar = {
            MyPageBottomBar(
                canConfirm = true,
                onConfirmClick = onConfirmClick
            )
        }

    ) { innerPadding ->
        Column {
            Card(
                modifier = modifier
                    .padding(innerPadding)
                    .padding(20.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    Color.White
                )
            ) {
                Column(
                    modifier = modifier
                        .padding(20.dp)
                ) {
                    Text(
                        text = "제목",
                        style = AppTypography.sub_medium_14,
                        color = Gray.gray_700
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = text,
                        onValueChange = { newText ->
                            text = newText
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = Gray.gray_300,
                            unfocusedBorderColor = Gray.gray_400
                        ),
                        modifier = Modifier.testTag("titleTextField")
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "내용",
                        style = AppTypography.sub_medium_14,
                        color = Gray.gray_700
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = text,
                        onValueChange = { newText ->
                            text = newText
                        },
                        modifier = modifier
                            .height(110.dp)
                            .testTag("contentTextField"),
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = Gray.gray_300,
                            unfocusedBorderColor = Gray.gray_400
                        )
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Card(
                        onClick = onCameraClick,
                        modifier = modifier
                            .size(56.dp)
                            .testTag("cameraButton"),
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Gray.gray_300
                        ),
                        border = BorderStroke(1.dp, Gray.gray_400)
                    ) {
                        Column(
                            modifier = modifier
                                .fillMaxSize()
                                .padding(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.outline_camera_24),
                                contentDescription = "camera",
                                modifier = modifier.size(20.dp),
                            )
                            Text(
                                text = "0/10",
                                style = AppTypography.description_regular_9
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            // Make the entire row clickable and handle the state change
                            .clickable(
                                role = Role.Checkbox,
                                onClick = { checkedState = !checkedState }
                            )
                            .testTag("privacyCheckbox"),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = checkedState,
                            onCheckedChange = null // Set to null as the parent Row handles clicks
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("개인정보 수집 이용 동의 (필수)")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FeedbackScreenPreview() {
    MiruniTheme {
        WriteFeedbackScreen(
            navController = rememberNavController()
        )
    }
}

package com.miruni.feature.mypage.info.feedback

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.miruni.core.designsystem.AppTypography
import com.miruni.core.designsystem.Gray
import com.miruni.core.designsystem.MiruniTheme
import com.miruni.feature.mypage.component.MyPageBottomBar
import com.miruni.feature.mypage.component.MyPageTopBar

private const val TAG = "ShowFeedbackHistory"

private const val MAX_PHOTO_COUNT = 10

@Composable
fun ShowFeedbackHistory(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onConfirmClick: () -> Unit = {}
) {
    val context = LocalContext.current

    var text by remember { mutableStateOf("") }
    var checkedState by remember { mutableStateOf(false) }
    var selectedPhotos by remember { mutableStateOf<List<Uri>>(emptyList()) }

    // Photo picker launcher
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(MAX_PHOTO_COUNT)
    ) { uris ->
        if (uris.isNotEmpty()) {
            selectedPhotos = uris.take(MAX_PHOTO_COUNT)
            Log.d(TAG, "Selected ${selectedPhotos.size} photos")
        }
    }

    // Permission launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Log.d(TAG, "Permission granted, launching photo picker")
            photoPickerLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        } else {
            Log.d(TAG, "Permission denied")
            Toast.makeText(
                context,
                "앨범에 접근하려면 권한 허용이 필요합니다.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    // Function to handle camera/album button click
    val onAlbumClick: () -> Unit = {
        Log.d(TAG, "Album button clicked")

        // Check if photo picker is available (Android 11+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android 13+ uses photo picker without permission
            photoPickerLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Android 11-12 uses photo picker without permission
            photoPickerLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        } else {
            // Android 10 and below requires READ_EXTERNAL_STORAGE permission
            val permission = Manifest.permission.READ_EXTERNAL_STORAGE
            when {
                ContextCompat.checkSelfPermission(
                    context,
                    permission
                ) == PackageManager.PERMISSION_GRANTED -> {
                    Log.d(TAG, "Permission already granted")
                    photoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }

                else -> {
                    Log.d(TAG, "Requesting permission")
                    permissionLauncher.launch(permission)
                }
            }
        }
    }

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
                btnText = "확인",
                onConfirmClick = onConfirmClick // TODO : 서버 request, novigate to InformationScreen
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(20.dp),
        ) {
            Card(
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

                    Row() {
                        Card(
                            onClick = onAlbumClick,
                            modifier = modifier
                                .size(56.dp)
                                .testTag("cameraButton"),
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Gray.gray_300
                            ),
                            border = BorderStroke(1.dp, Gray.gray_400)
                        ) {
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
                        Text(
                            text ="개인정보 수집 이용 동의 (필수)",
                            style = AppTypography.body_regular_12
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .height(184.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    Color.White
                )
            ) {
                Text(
                    modifier = modifier
                        .padding(20.dp),
                    text ="답변",
                    style = AppTypography.body_regular_14,
                    )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ShowFeedbackHistoryPreview() {
    MiruniTheme {
        ShowFeedbackHistory(
            navController = rememberNavController()
        )
    }
}
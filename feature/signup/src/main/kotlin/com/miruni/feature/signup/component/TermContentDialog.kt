package com.miruni.feature.signup.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.miruni.core.designsystem.AppTypography
import com.miruni.feature.signup.model.Term

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TermContentDialog(
    term: Term,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        )
    ) {
        Scaffold(
            modifier = modifier
                .fillMaxSize(),
            topBar = {
                SignUpTopBar(
                    onPrevStep = onDismiss,
                    title = "회원가입",
                )
            },
            bottomBar = {
                SignUpBottomBar(
                    btnText = "확인",
                    canNext = true,
                    onNextStep = {
                        onDismiss()
                    }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = term.getTermContent(),
                    style = AppTypography.body_regular_14
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
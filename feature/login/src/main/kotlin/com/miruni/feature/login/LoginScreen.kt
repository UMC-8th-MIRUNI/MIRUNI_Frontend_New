package com.miruni.feature.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.miruni.core.designsystem.MiruniTheme

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onSignUpClick: () -> Unit,
    onResetPasswordClick: () -> Unit
) {
    Column(Modifier.padding(16.dp)) {
        Text("Login Screen")

        Button(onClick = onLoginSuccess) {
            Text("로그인")
        }

        Button(onClick = onSignUpClick) {
            Text("회원가입")
        }

        Button(onClick = onResetPasswordClick) {
            Text("비밀번호 재설정")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LoginScreenPreview() {
    MiruniTheme {
        LoginScreen({}, {}, {})
    }
}
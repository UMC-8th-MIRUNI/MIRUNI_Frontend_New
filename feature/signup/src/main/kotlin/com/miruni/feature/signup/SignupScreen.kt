package com.miruni.feature.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.miruni.designsystem.MiruniTheme

@Composable
fun SignupScreen(
    onSignUpSuccess: () -> Unit
) {

    Column(Modifier.padding(16.dp)) {
        Text("SignUp Screen")

        Button(onClick = onSignUpSuccess) {
            Text("회원가입 성공")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SignUpScreenPreview() {
    MiruniTheme {
        SignupScreen({})
    }
}
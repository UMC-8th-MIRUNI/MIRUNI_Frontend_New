package com.miruni.feature.pwreset

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
fun PwResetScreen(
    onLoginRestart: () -> Unit
) {
    Column(Modifier.padding(16.dp)) {
        Text("PwReset Screen")

        Button(onClick = onLoginRestart) {
            Text("로그인 재시도")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PwResetScreenPreview() {
    MiruniTheme {
        PwResetScreen({})
    }
}
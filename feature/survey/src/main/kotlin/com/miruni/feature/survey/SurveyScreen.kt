package com.miruni.feature.survey

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.miruni.designsystem.MiruniTheme

@Composable
private fun SurveyScreen(modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(text = "Survey Screen")
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SurveyScreenPreview() {
    MiruniTheme {
        SurveyScreen()
    }
}
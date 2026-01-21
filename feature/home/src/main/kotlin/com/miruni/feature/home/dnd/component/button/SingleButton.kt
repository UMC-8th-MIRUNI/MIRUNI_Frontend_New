package com.miruni.feature.home.dnd.component.button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.miruni.core.designsystem.Gray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingleGreenButton(
    onClick: () -> Unit,
    text: String
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(49.dp),
        shape = RoundedCornerShape(10.dp),
        onClick = {
            onClick()
        },
    ) {
        Text(
            text = text
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingleWhiteButton(
    onClick: () -> Unit,
    text: String
) {
    Button(
        modifier = Modifier
            .height(49.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Gray.gray_300
        ),
        onClick = onClick
    ) {
        Text(
            text = text,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
        )
    }
}
package com.miruni.feature.signup.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.miruni.designsystem.AppTypography
import com.miruni.designsystem.MainColor
import com.miruni.designsystem.White

@Composable
fun SignUpBottomBar(
    canNext: Boolean,
    btnText: String = "다음",
    onNextStep: () -> Unit,
){
    Surface(
        color = Color.Transparent,
        modifier = Modifier.
            navigationBarsPadding()
    ){
        Column {
            Button(
                onClick = onNextStep,
                enabled = canNext,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(49.dp)
                    .padding(horizontal = 20.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MainColor.miruni_green,
                    contentColor = White
                )
            ) {
                Text(btnText, style = AppTypography.button_semibold_16)
            }
            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}
package com.miruni.feature.home.dnd.component.button

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.miruni.core.designsystem.Gray
import com.miruni.core.designsystem.MainColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RowButton(
    text1: String,
    text2: String,
    onClickButton1: () -> Unit,
    onClickButton2: () -> Unit,
    button1Color: Color,
    button2Color: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            modifier = Modifier
                .weight(1f)
                .height(50.dp)
                .padding(start = 20.dp, end = 10.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = button1Color
            ),
            onClick = {
                Log.d("DndTimerSet", "Cancel clicked")
                onClickButton1()
            }
        ) {
            Text(
                text = text1
            )
        }

        Button(
            modifier = Modifier
                .weight(1f)
                .height(50.dp)
                .padding(start = 10.dp, end = 20.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = button2Color
            ),
            onClick = {
                Log.d("DndTimerSet", "Confirm clicked")
                onClickButton2()
            }
        ) {
            Text(
                text = text2
            )
        }
    }
}
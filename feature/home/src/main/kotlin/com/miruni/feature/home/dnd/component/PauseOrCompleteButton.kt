package com.miruni.feature.home.dnd.component

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
import androidx.compose.ui.unit.dp
import com.miruni.core.designsystem.Gray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PauseOrCompleteButton(
    modifier: Modifier = Modifier
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
                containerColor = Gray.gray_500
            ),
            onClick = {
                Log.d("DndTimerSet", "Stop clicked")
                // TODO: 중지 버튼 클릭 처리
            }
        ) {
            Text("중지")
        }

        Button(
            modifier = Modifier
                .weight(1f)
                .height(50.dp)
                .padding(horizontal = 20.dp),
            shape = RoundedCornerShape(10.dp),
            onClick = {
                Log.d("DndTimerSet", "Complete clicked")
                // TODO: 완료 버튼 클릭 처리
            }
        ) {
            Text("완료")
        }
    }
}
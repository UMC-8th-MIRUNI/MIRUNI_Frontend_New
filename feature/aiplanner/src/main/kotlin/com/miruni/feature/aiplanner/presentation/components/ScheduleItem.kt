package com.miruni.feature.aiplanner.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.miruni.core.designsystem.AppTypography
import com.miruni.core.designsystem.Gray
import com.miruni.core.designsystem.MainColor

@Composable
fun ScheduleItem(
    title: String,
    doneCount: Int,
    totalCount: Int,
    progressRate: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(Gray.gray_300)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 19.dp, end = 24.dp, top = 20.dp, bottom = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column() {
                Text(
                    text = title,
                    style = AppTypography.sub_bold_14,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(9.dp))

                Text(
                    text = "진행률: ${doneCount}/${totalCount}단계",
                    style = AppTypography.description_regular_9,
                    color = Color.Black
                )
            }

            DonutProgressBar(
                modifier = Modifier.size(40.dp),
                progress = progressRate / 100f,
                strokeWidth = 5.dp,
                progressColor = MainColor.miruni_green
            )
        }
    }
}

@Preview
@Composable
fun PreviewScheduleItem() {
    ScheduleItem(
        title = "UMC",
        doneCount = 2,
        totalCount = 10,
        progressRate = 20,
        onClick = {}
    )
}
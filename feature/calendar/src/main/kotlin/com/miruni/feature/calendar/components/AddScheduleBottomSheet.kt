package com.miruni.feature.calendar.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.miruni.core.designsystem.AppTypography
import com.miruni.core.designsystem.Black
import com.miruni.core.designsystem.Gray
import com.miruni.feature.calendar.common.MiruniButton
import com.miruni.feature.calendar.common.MiruniTextField
import com.miruni.feature.calendar.model.AddScheduleState
import com.miruni.feature.calendar.model.DateTimeRangeState
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScheduleBottomSheet(
    selectedDate: LocalDate,
    modifier: Modifier = Modifier,
    onConfirm: (AddScheduleState) -> Unit,
    onDismiss: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var title by remember { mutableStateOf("") }
    var priority by remember { mutableStateOf(Priority.MEDIUM) }
    var description by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }
    var dateTimeRange by remember {
        mutableStateOf(DateTimeRangeState(selectedDate, selectedDate))
    }


    if (showDatePicker) {
        DateTimeRangeDialog(
            initialRange = dateTimeRange,
            onConfirm = {
                showDatePicker = false
                dateTimeRange = it
            },
            onDismiss = { showDatePicker = false }
        )
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 20.dp)
                    .imePadding()
            ) {
                Text(
                    text = "일정 등록하기",
                    style = AppTypography.sub_bold_14,
                    color = Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp)
                )

                InputField(
                    label = "제목",
                    value = title,
                    onValueChange = { title = it },
                )

                Spacer(modifier = Modifier.height(10.dp))

                DateField(
                    label = "날짜 및 시간",
                    dateTimeRange = dateTimeRange,
                    onClick = { showDatePicker = true }
                )

                Spacer(modifier = Modifier.height(10.dp))

                InputField(
                    label = "설명",
                    value = description,
                    onValueChange = { description = it },
                    singleLine = false,
                    maxLines = 5
                )

                Spacer(modifier = Modifier.height(50.dp))

                MiruniButton.Single(
                    text = "완료",
                    onClick = {
                        onConfirm(
                            AddScheduleState(
                                title = title,
                                dateTimeRange = dateTimeRange,
                                priority = priority,
                                description = description
                            )
                        )
                    },
                    enabled = title.isNotBlank()
                )
            }
        }
    }
}

@Composable
fun InputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    singleLine: Boolean = true,
    maxLines: Int = 1,
) {
    Column {
        Text(
            text = label,
            style = AppTypography.body_regular_14,
            color = Black,
            modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
        )

        MiruniTextField.InputText(
            value = value,
            onValueChange = onValueChange,
            maxLines = maxLines,
            singleLine = singleLine
        )
    }
}

@SuppressLint("NewApi")
@Composable
fun DateField(
    label: String,
    dateTimeRange: DateTimeRangeState,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Column {
        Text(
            text = label,
            style = AppTypography.body_regular_14,
            color = Black,
            modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
        )
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(41.dp)
                .border(1.dp, Gray.gray_500, RoundedCornerShape(10.dp))
                .background(Color.White, RoundedCornerShape(10.dp))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onClick
                )
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = dateTimeRange.formatDateTime(),
                    style = AppTypography.body_regular_14,
                    color = Black,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null,
                    tint = Gray.gray_700
                )
            }
        }
    }
}
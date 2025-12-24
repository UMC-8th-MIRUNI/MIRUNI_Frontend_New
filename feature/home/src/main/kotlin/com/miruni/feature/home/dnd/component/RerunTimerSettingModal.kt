package com.miruni.feature.home.dnd.component

import android.R.attr.repeatCount
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.miruni.core.designsystem.MainColor
import com.miruni.core.designsystem.MiruniTheme
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

private const val REPEAT_COUNT = 100

@Composable
fun RerunTimerSettingModal(
    onDismiss: () -> Unit,
    onConfirm: (hour: Int, minute: Int) -> Unit
) {
    val dividerColor: Color = MainColor.miruni_green

    val hourItems = remember { (0..23).map { it.toString().padStart(2, '0') } }
    val minuteItems = remember { (0..59).map { it.toString().padStart(2, '0') } }

    val hourPickerState = rememberPickerState()
    val minutePickerState = rememberPickerState()

    val itemHeightPixels = remember { mutableStateOf(0) }
    val itemHeightDp = pixelsToDp(itemHeightPixels.value)

    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Surface(
            modifier = Modifier.size(330.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                val hour = remember {
                    (0..23).map { it.toString().padStart(2, '0') }
                }
                val minute = remember {
                    (0..59).map { it.toString().padStart(2, '0') }
                }
                Text(
                    text = buildAnnotatedString {
                        append("지금 멈추시겠다면, 오늘 안에")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("\n다시 실행할 시간")
                        }
                        append("을 설정해주세요!")
                    },
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center, // 텍스트 자체를 가운데 정렬
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Picker(
                            state = hourPickerState,
                            items = hour,
                            modifier = Modifier
                                .width(80.dp)
                                .padding(end = 12.dp),
                            textModifier = Modifier.padding(vertical = 8.dp),
                            textStyle = TextStyle(fontSize = 32.sp)
                        )
                        Text(
                            ":",
                            style = TextStyle(fontSize = 32.sp),
                            modifier = Modifier.padding(horizontal = 12.dp)
                        )
                        Picker(
                            state = minutePickerState,
                            items = minute,
                            modifier = Modifier
                                .width(80.dp)
                                .padding(start = 12.dp),
                            textStyle = TextStyle(fontSize = 32.sp),
                            textModifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                    HorizontalDivider(
                        color = dividerColor,
                        thickness = 1.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                            .offset(y = (itemHeightDp / 2) + 28.dp)
                    )

                    HorizontalDivider(
                        color = dividerColor,
                        thickness = 1.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                            .offset(y = (itemHeightDp / 2) - 28.dp)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFCBCBCB)
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 36.dp)
                            .height(36.dp)
                    ) {
                        Text(text = "취소", fontSize = 16.sp)
                    }
                    Button(
                        onClick = {
                            // TODO: 확인 버튼 클릭 시 홈페이지로 이동. 설정한 시간에 일정이 있는 경우 재실행 시간 설정 경고 모달 띄우기
                        },
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 36.dp)
                            .height(36.dp)
                    ) {
                        Text(text = "확인", fontSize = 16.sp)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Picker(
    items: List<String>,
    state: PickerState = rememberPickerState(),
    modifier: Modifier = Modifier,
    startIndex: Int = 0,
    visibleItemsCount: Int = 3,
    textModifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTextStyle.current,
) {
    val visibleItemsMiddle = visibleItemsCount / 2
    val listScrollCount = items.size * REPEAT_COUNT

    fun getItem(index: Int) = items[index % items.size]

    val listState = rememberLazyListState(
        initialFirstVisibleItemIndex =
            items.size * (repeatCount / 2) - visibleItemsMiddle + startIndex
    )
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)

    val itemHeightPixels = remember { mutableStateOf(0) }
    val itemHeightDp = pixelsToDp(itemHeightPixels.value)

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex + visibleItemsMiddle }
            .map { getItem(it) }
            .distinctUntilChanged()
            .collect { state.selectedItem = it }
    }
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            state = listState,
            flingBehavior = flingBehavior,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .height(itemHeightDp * visibleItemsCount)
        ) {
            items(listScrollCount) { index ->
                val centerIndex = listState.firstVisibleItemIndex + visibleItemsMiddle
                val isCenterItem = index == centerIndex

                Text(
                    text = getItem(index),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = textStyle.copy(
                        fontWeight = if (isCenterItem)
                            FontWeight.SemiBold
                        else FontWeight.Medium,
                        color = if (isCenterItem)
                            Color.Black
                        else
                            Color(0xFFCBCBCB)
                    ),
                    modifier = Modifier
                        .onSizeChanged { itemHeightPixels.value = it.height }
                        .then(textModifier)
                )
            }
        }
    }
}

@Composable
private fun pixelsToDp(pixels: Int) = with(LocalDensity.current) { pixels.toDp() }

@Composable
fun rememberPickerState() = remember { PickerState() }

class PickerState {
    var selectedItem by mutableStateOf("")
}

@Preview(showBackground = true)
@Composable
fun RerunTimerSettingModalPreview() {
    MiruniTheme {
        RerunTimerSettingModal(
            onDismiss = {},
            onConfirm = { _, _ -> }
        )
    }
}

package com.miruni.feature.aiplanner.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.miruni.core.designsystem.AppTypography
import com.miruni.core.designsystem.MainColor
import com.miruni.core.navigation.MiruniRoute
import com.miruni.feature.aiplanner.R
import com.miruni.feature.aiplanner.common.convertBold

/**
 * AI 플래너 온보딩 네비게이트
 */
@Composable
fun AiPlannerOnboardingScreen(
    navController: NavController,
    viewModel: AiPlannerViewModel = hiltViewModel()
) {
    AiPlannerOnboardingContent(
        onComplete = {
            viewModel.handleEvents(AiPlannerContract.Event.CompleteOnboarding)

            navController.navigate(MiruniRoute.AiPlanner.route) {
                popUpTo(MiruniRoute.AiPlannerOnboarding.route) {
                    inclusive = true
                }
            }
        }
    )
}

@Composable
fun AiPlannerOnboardingContent(
    onComplete: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MainColor.miruni_green),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.3f)
                .padding(17.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            Box(
                modifier = Modifier
                    .shadow(elevation = 1.83.dp)
                    .background(color = Color(0xFFB9E8C6), shape = RoundedCornerShape(10.dp))
            ) {
                Text(
                    text = convertBold("미루니만의 'AI 플래너' 기능을 통해\n미루지 않을 수 있는 계획을 세우고\n 할 일을 시작해보세요!"),
                    color = Color.White,
                    style = AppTypography.sub_medium_14,
                    modifier = Modifier
                        .padding(horizontal = 38.dp, vertical = 18.dp)
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.43f)
                .padding(end = 44.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            Column(
                modifier = Modifier
                    .wrapContentSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.miruni_pencil),
                    contentDescription = null,
                    modifier = Modifier
                        .size(width = 222.dp, height = 178.dp)
                        .graphicsLayer { // 이미지 리소스 뒤집혀 보여서 속성 추가함
                            scaleX = -1f
                        }
                )
                Image(
                    painter = painterResource(R.drawable.miruni_shadow),
                    contentDescription = null,
                    modifier = Modifier
                        .size(width = 107.dp, height = 23.dp)
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.27f)
                .padding(end = 15.dp, bottom = 38.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            Row(
                modifier = Modifier
                    .clickable { onComplete() },
                verticalAlignment = Alignment.CenterVertically
            ) {

                var textHeight by remember { mutableStateOf(0.dp) }
                val density = LocalDensity.current

                Text(
                    text = "시작하기",
                    color = Color.White,
                    style = AppTypography.sub_bold_14,
                    onTextLayout = { result ->
                        textHeight = with(density) {
                            result.size.height.toDp()
                        }
                    }
                )
                Spacer(modifier = Modifier.width(10.dp))
                Image(
                    painter = painterResource(R.drawable.right_arrow),
                    contentDescription = null,
                    modifier = Modifier.size(textHeight)
                )
            }
        }
    }
}
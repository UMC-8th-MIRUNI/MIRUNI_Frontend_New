package com.miruni.feature.onboard.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.miruni.core.designsystem.MiruniTheme
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.miruni.core.common.SIDE_EFFECTS_KEY
import com.miruni.core.designsystem.AppTypography
import com.miruni.core.designsystem.MiruniSize.height
import com.miruni.core.designsystem.Yellow
import com.miruni.core.navigation.MiruniRoute
import com.miruni.feature.onboard.OnboardContract
import com.miruni.feature.onboard.OnboardViewModel
import com.miruni.feature.onboard.R
import com.miruni.feature.onboard.presentation.model.OnboardingPageData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardScreen(
    navController: NavController,
    viewModel: OnboardViewModel = hiltViewModel()
) {
    val state by viewModel.viewState.collectAsState()
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { state.pageData.size })

    // 화면 이동
    LaunchedEffect(SIDE_EFFECTS_KEY) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is OnboardContract.Effect.NavigateToLogin -> {
                    navController.navigate(MiruniRoute.Login.route) {
                        popUpTo(MiruniRoute.AppOnboarding.route) { inclusive = true }
                    }
                }
            }
        }
    }

    OnboardContent(
        pageList = state.pageData,
        pagerState = pagerState,
        onClickNext = { scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) } },
        onClickStart = { viewModel.setEvent(OnboardContract.Event.CompleteOnboarding) },
        onClickSkip = { viewModel.setEvent(OnboardContract.Event.SkipOnboarding) }
    )
}

@Composable
fun OnboardContent(
    pageList: List<OnboardingPageData>,
    pagerState: PagerState,
    onClickNext: () -> Unit,
    onClickStart: () -> Unit,
    onClickSkip: () -> Unit
) {
    val isLastPage = pagerState.currentPage == pageList.size - 1

    Box(modifier = Modifier.fillMaxSize()) {

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFF24C354))
        ) { index ->
            when (val page = pageList[index]) {
                is OnboardingPageData.Basic -> BasicLayout(data = page)
                is OnboardingPageData.Final -> FinalLayout(data = page)
            }
        }

        // Skip or 우상단 배경 이미지
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding(),
            contentAlignment = Alignment.TopEnd
        ) {
            if (!isLastPage) {
                Text(
                    text = "Skip",
                    style = AppTypography.sub_medium_14,
                    color = Color.White,
                    modifier = Modifier
                        .padding(top = 76.dp, end = 16.dp)
                        .clickable { onClickSkip() }
                )
            } else {
                val finalPage = pageList[pagerState.currentPage] as? OnboardingPageData.Final
                finalPage?.let {
                    Image(
                        painter = painterResource(it.topImgRes),
                        contentDescription = null
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
        ) {
            // 좌하단 배경 이미지
            if (isLastPage) {
                val finalPage = pageList[pagerState.currentPage] as? OnboardingPageData.Final
                finalPage?.let {
                    Image(
                        painter = painterResource(it.bottomImgRes),
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.BottomStart)
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 36.dp, end = 16.dp, bottom = 89.dp)
                    .align(Alignment.BottomCenter),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 인디케이터
                PageIndicator(
                    pageCount = pageList.size,
                    currentPage = pagerState.currentPage
                )

                Row(
                    modifier = Modifier.clickable {
                        if (isLastPage) onClickStart() else onClickNext()
                    },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text =
                            if (isLastPage) "시작하기"
                            else "다음",
                        style = AppTypography.sub_bold_14,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Image(
                        painter = painterResource(R.drawable.right_arrow),
                        contentDescription = null,
                        modifier = Modifier.size(17.dp)
                    )
                }
            }
        }
    }
}

/** 1, 2번째 페이지 */
@Composable
fun BasicLayout(
    data: OnboardingPageData.Basic
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(166.dp))

        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            color = Color(0xFF7AD996),
            shadowElevation = 2.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 35.dp, vertical = 26.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = data.header,
                    style = AppTypography.header_bold_16,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(11.dp))
                Text(
                    text = data.body,
                    style = AppTypography.body_regular_12,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(120.dp))

        Image(
            painter = painterResource(R.drawable.miruni_basic),
            contentDescription = null,
            modifier = Modifier.size(width = 195.dp, height = 178.dp)
        )
        Row {
            Spacer(modifier = Modifier.width((150.06).dp))
            Image(
                painter = painterResource(R.drawable.miruni_shadow),
                contentDescription = null,
                modifier = Modifier.size(width = 107.dp, height = 23.dp)
            )
        }
    }
}

/** 마지막 페이지 */
@Composable
fun FinalLayout(
    data: OnboardingPageData.Final
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = data.header,
                style = AppTypography.header_bold_20,
                color = Yellow.yellow,
            )
            Spacer(modifier = Modifier.height(21.dp))
            Text(
                text = data.body,
                style = AppTypography.body_regular_12,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}


/**
 * 인디케이터
 * - 애니메이션 컴포넌트
 */
@Composable
fun PageIndicator(
    pageCount: Int,
    currentPage: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(3.dp) // 점 사이 간격
    ) {
        repeat(pageCount) { iteration ->
            // 현재 페이지인지 확인
            val isSelected = currentPage == iteration

            // 1. 너비 애니메이션 (선택되면 길어짐)
            val width by animateDpAsState(
                targetValue = if (isSelected) 17.dp else 4.dp, // 선택: 32dp, 비선택: 10dp
                animationSpec = spring(stiffness = Spring.StiffnessLow), // 쫀득한 느낌
                label = "indicatorWidth"
            )

            // 2. 색상 애니메이션 (선택되면 흰색, 아니면 회색)
            val color by animateColorAsState(
                targetValue = if (isSelected) Color.White else Color.Black.copy(alpha = 0.2f),
                animationSpec = tween(durationMillis = 300),
                label = "indicatorColor"
            )

            Box(
                modifier = Modifier
                    .height(4.dp)
                    .width(width)
                    .clip(CircleShape)
                    .background(color)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun OnboardScreenPreview() {
    OnboardContent(
        pageList = listOf(
            OnboardingPageData.Basic(
                backgroundColor = Color(0xFF24C354),
                header = "매일 다짐해도 바꾸기 어려운 미루는 습관,",
                body = "MIRUNI는 계획부터 실천까지 합께합니다.",
                imgRes = R.drawable.miruni_basic
            ),
            OnboardingPageData.Basic(
                backgroundColor = Color(0xFF24C354),
                header = "AI 플래너, 방해요소 차단, 일정 알림",
                body = "모두 한 번에.",
                imgRes = R.drawable.miruni_basic
            ),
            OnboardingPageData.Final(
                backgroundColor = Color(0xFF24C354),
                header = "습관은 분명 바뀔 수 있어요.",
                body = "MIRUNI와 함께,\n더 효율적인 하루를 시작해보세요!",
                topImgRes = R.drawable.onboarding_top_img,
                bottomImgRes = R.drawable.onboarding_bottom_img
            )
        ),
        pagerState = rememberPagerState(initialPage = 1, pageCount = { 3 }),
        onClickNext = {},
        onClickStart = {},
        onClickSkip = {}
    )
}
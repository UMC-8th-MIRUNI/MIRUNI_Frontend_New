package com.miruni.feature.aiplanner.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun AiPlannerScreen(
    navController: NavController,
    viewModel: AiPlannerViewModel = hiltViewModel()
) {
    val state by viewModel.viewState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.setEvent(AiPlannerContract.Event.Enter)
    }

    if (state.showOnboarding) { // 온보딩 화면

    } else { // AI 플래너 화면

    }
}
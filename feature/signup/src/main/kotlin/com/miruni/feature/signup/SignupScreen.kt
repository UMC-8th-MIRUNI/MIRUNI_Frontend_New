package com.miruni.feature.signup

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.miruni.core.designsystem.AppTypography
import com.miruni.core.designsystem.MiruniTheme
import com.miruni.feature.signup.component.SignUpBottomBar
import com.miruni.feature.signup.component.SignUpTopBar
import com.miruni.feature.signup.component.TermContentDialog
import com.miruni.feature.signup.component.step.SignUpAccountStepRoute
import com.miruni.feature.signup.component.step.SignUpProfileStepRoute
import com.miruni.feature.signup.component.step.SignUpTermStepRoute
import com.miruni.feature.signup.model.SignupStateStep


@Composable
fun SignupScreen(
    navController: NavHostController,
    onSignUpSuccess: () -> Unit,
    viewModel: SignupViewModel = viewModel()
) {
    val uiState = viewModel.viewState.value
    val steps = remember { SignUpContract.stepSequence }
    val currentIndex = remember(uiState.step) {
        steps.indexOf(uiState.step).coerceAtLeast(0)
    }
    Scaffold(
        topBar = {
            SignUpTopBar(
                onPrevStep = {
                    viewModel.setEvent(SignUpContract.Event.OnPrevStepClicked)
                },
                title = "회원 가입",
                actions = {
                    Text(
                        text = "${currentIndex + 1}/${steps.size} 단계",
                        style = AppTypography.body_regular_12,
                    )
                }
            )
        },
        bottomBar = {
            SignUpBottomBar(
                canNext = true,
                onNextStep = {
                    viewModel.setEvent(SignUpContract.Event.OnNextStepClicked)
                }
            )
        },
    ) { innerPadding ->
        AnimatedContent(
            targetState = uiState.step,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            transitionSpec = {
                val forward = steps.indexOf(targetState) > steps.indexOf(initialState)
                val dur = 400
                if (forward) {
                    (slideInHorizontally(
                        initialOffsetX = { it / 2}, animationSpec = tween(dur)
                    ) + fadeIn(tween(dur))) togetherWith
                            (slideOutHorizontally(
                                targetOffsetX = { -it / 2 }, animationSpec = tween(dur)
                            ) + fadeOut(tween( 120)))
                } else {
                    (slideInHorizontally(
                        initialOffsetX = { -it / 2 }, animationSpec = tween(dur)
                    ) + fadeIn(tween(dur))) togetherWith
                            (slideOutHorizontally(
                                targetOffsetX = { it / 2 }, animationSpec = tween(dur)
                            ) + fadeOut(tween(120)))
                }.using(SizeTransform(clip = false, sizeAnimationSpec = { _, _ -> tween(0) }))
            },
        ) { step ->
            Box(
                modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp)
            ){
                when (step) {
                    SignupStateStep.Profile -> {
                        SignUpProfileStepRoute(
                            uiState = uiState,
                            viewModel = viewModel,
                        )
                    }

                    SignupStateStep.Account -> {
                        SignUpAccountStepRoute(
                            uiState = uiState,
                            viewModel = viewModel,
                        )
                    }

                    SignupStateStep.Terms -> {
                        SignUpTermStepRoute(
                            uiState = uiState,
                            viewModel = viewModel,
                        )

                    }
                }
            }
        }
        uiState.selectedTerm?.let { term ->
            TermContentDialog(
                term = term,
                onDismiss = {
                    viewModel.setEvent(SignUpContract.Event.OnSelectedTermChanged(null))
                }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SignUpScreenPreview() {
    MiruniTheme {
        SignupScreen(navController = rememberNavController(), onSignUpSuccess = {})
    }
}
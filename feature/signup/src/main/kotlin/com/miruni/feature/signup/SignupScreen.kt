package com.miruni.feature.signup

import android.util.Log
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.miruni.core.designsystem.AppTypography
import com.miruni.core.designsystem.MiruniTheme
import com.miruni.signup.SignUpViewModel
import com.miruni.signup.component.SignUpBottomBar
import com.miruni.signup.component.SignUpTopBar
import com.miruni.signup.component.TermContentDialog
import com.miruni.signup.component.step.SignUpAccountStep
import com.miruni.signup.component.step.SignUpProfileStep
import com.miruni.signup.component.step.SignUpTermStep
import com.miruni.signup.model.SignUpStateStep


@Composable
fun SignupScreen(
    navController: NavHostController,
    onSignUpSuccess: () -> Unit,
    viewModel: SignUpViewModel = viewModel()
) {

    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val steps = remember {
        listOf(
            SignUpStateStep.Profile,
            SignUpStateStep.Account,
            SignUpStateStep.Terms,
        )
    }
    var stepIndex by rememberSaveable { mutableStateOf(0) }

    Scaffold(
        topBar = {
            SignUpTopBar(
                onPrevStep = {
                    if (stepIndex > 0)  stepIndex--
                },
                title = "회원 가입",
                actions = {
                    Text(
                        text = "${stepIndex + 1}/${steps.size}단계",
                        style = AppTypography.body_regular_12,
                    )
                }
            )
        },
        bottomBar = {
            SignUpBottomBar(
                canNext = stepIndex < steps.lastIndex,
                onNextStep = {
                    if (stepIndex < steps.lastIndex) stepIndex++
                }
            )
        },
    ) { innerPadding ->
        AnimatedContent(
            targetState = stepIndex,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            transitionSpec = {
                val forward = targetState > initialState
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
        ) { index ->
            Box(
                modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp)
            ){
                when (steps[index]) {
                    SignUpStateStep.Profile -> {
                        SignUpProfileStep(
                            viewModel = viewModel,
                            onPrevStep = {
                                if (index > 0) stepIndex--
                            },
                            onNextStep = {
                                Log.d("SignUpScreen", "")
//                            if (index < steps.lastIndex) {
//                                stepIndex++
//
//                            }
                            }
                        )
                    }

                    SignUpStateStep.Account -> {
                        SignUpAccountStep(
                            onPrevStep = {
                                if (index > 0) stepIndex--
                            },
                            onNextStep = {
                                if (index < steps.lastIndex) stepIndex++
                            }
                        )
                    }

                    SignUpStateStep.Terms -> {
                        SignUpTermStep(
                            onPrevStep = {
                                if (index > 0) stepIndex--
                            },
                            onNextStep = {
                                if (index < steps.lastIndex) stepIndex++
                            },
                            onSelectTermContent = { term ->
                                viewModel.updateSelectedTerm(term)
                            }
                        )
                    }
                }
            }
        }
        uiState.selectedTerm?.let { term ->
            TermContentDialog(
                term = term,
                onDismiss = {
                    viewModel.updateSelectedTerm(null)
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
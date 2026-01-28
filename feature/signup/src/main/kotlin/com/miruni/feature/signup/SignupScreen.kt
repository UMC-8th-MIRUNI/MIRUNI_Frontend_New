package com.miruni.feature.signup

import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.miruni.core.designsystem.AppTypography
import com.miruni.feature.signup.presentation.component.SignUpBottomBar
import com.miruni.feature.signup.presentation.component.SignUpTopBar
import com.miruni.feature.signup.presentation.component.TermContentDialog
import com.miruni.feature.signup.presentation.step.SignUpProfileStep
import com.miruni.feature.signup.presentation.step.SignUpTermStep
import com.miruni.feature.signup.presentation.navigation.SignupRoute
import com.miruni.core.designsystem.MiruniSpacing
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SignupNavigator(
    onSignUpSuccess: () -> Unit,
    onBack: () -> Unit,
    viewModel: SignupViewModel = hiltViewModel(),
) {
    val uiState = viewModel.viewState.collectAsStateWithLifecycle().value
    val onEvent: (SignUpContract.Event) -> Unit = viewModel::setEvent
    val navController = rememberNavController()

    // 현재 라우트 관찰
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        ?: SignupRoute.PROFILE
    val snackHostState = remember { SnackbarHostState() }

    // VM과 라우트 동기화 (canNext, 단계표시용)
    LaunchedEffect(currentRoute) {
        onEvent(SignUpContract.Event.OnRouteChanged(currentRoute))
    }
    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is SignUpContract.Effect.Navigation.ToRoute -> {
                    if (navController.currentDestination?.route != effect.route) {
                        navController.navigate(effect.route) { launchSingleTop = true }
                    }
                }
                is SignUpContract.Effect.Navigation.Back -> {
                    if (!navController.popBackStack()) onBack()
                }
                is SignUpContract.Effect.Navigation.Done -> onSignUpSuccess()

                is SignUpContract.Effect.Message.Toast -> {}
                is SignUpContract.Effect.Message.SnackBar -> {
                    snackHostState.showSnackbar(
                        message = effect.message,
                        actionLabel = effect.actionLabel,
                        withDismissAction = true
                    )
                }
            }
        }
    }

    val idx = SignupRoute.sequence.indexOf(currentRoute).coerceAtLeast(0)

    Scaffold(
        topBar = {
            SignUpTopBar(
                onPrevStep = {
                    if (!navController.popBackStack()) onBack()
                },
                title = "회원 가입",
                actions = {
                    Text(
                        text = "${idx + 1}/${SignupRoute.sequence.size} 단계",
                        style = AppTypography.body_regular_12,
                    )
                }
            )
        },
        bottomBar = {
            SignUpBottomBar(
                canNext = uiState.canNext,
                onNextStep = {
                    if (!uiState.canNext) return@SignUpBottomBar
                    onEvent(SignUpContract.Event.OnNextStepClicked)
                }
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackHostState,
                modifier = Modifier
                    .navigationBarsPadding()
                    .imePadding()
                    .padding(bottom = MiruniSpacing.xxl)
            )
        },
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = SignupRoute.PROFILE,
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = MiruniSpacing.screenHorizontal)
        ) {
            composable(SignupRoute.PROFILE) {
                SignUpProfileStep(
                    name = uiState.name.value,
                    birth = uiState.birth.value,
                    phone = uiState.phone.value,
                    email = uiState.email.value,
                    otp = uiState.otp.value,
                    password = uiState.password.value,
                    passwordCheck = uiState.passwordCheck.value,

                    onNameChange = { viewModel.setEvent(SignUpContract.Event.OnNameChanged(it)) },
                    onBirthChange = { viewModel.setEvent(SignUpContract.Event.OnBirthChanged(it)) },
                    onPhoneChange = { viewModel.setEvent(SignUpContract.Event.OnPhoneChanged(it)) },
                    onEmailChange = { viewModel.setEvent(SignUpContract.Event.OnEmailChanged(it)) },
                    onOtpChange = { viewModel.setEvent(SignUpContract.Event.OnOtpChanged(it)) },
                    onPasswordChange = { viewModel.setEvent(SignUpContract.Event.OnPasswordChanged(it)) },
                    onPasswordCheckChange = { viewModel.setEvent(SignUpContract.Event.OnPasswordCheckChanged(it)) },

                    onRequestOtp = { /* viewModel.setEvent(SignUpContract.Event.OnRequestOtpClicked) */ },
                    onVerifyOtp = { /* viewModel.setEvent(SignUpContract.Event.OnVerifyOtpClicked) */ },
                )
            }

            composable(SignupRoute.TERMS) {
                SignUpTermStep(
                    nickName = uiState.nickName.value,
                    agreeRealName = uiState.agreeRealName,
                    agreeTerms = uiState.agreeTerms,
                    agreePrivacy = uiState.agreePrivacy,
                    agreeMarketing = uiState.agreeMarketing,
                    onNickNameChange = { viewModel.setEvent(SignUpContract.Event.OnNickNameChanged(it)) },
                    onAgreeRealNameChange = { viewModel.setEvent(SignUpContract.Event.OnAgreeRealNameChanged(it)) },
                    onAgreeAllChange = { viewModel.setEvent(SignUpContract.Event.OnAgreeAllChanged(it)) },
                    onAgreeTermsChange = { viewModel.setEvent(SignUpContract.Event.OnAgreeTermsChanged(it)) },
                    onAgreePrivacyChange = { viewModel.setEvent(SignUpContract.Event.OnAgreePrivacyChanged(it)) },
                    onAgreeMarketingChange = { viewModel.setEvent(SignUpContract.Event.OnAgreeMarketingChanged(it)) },
                    onTermContentClick = { term -> viewModel.setEvent(SignUpContract.Event.OnSelectedTermChanged(term)) },
                )
            }

        }

        uiState.selectedTerm?.let { term ->
            TermContentDialog(
                term = term,
                onDismiss = { onEvent(SignUpContract.Event.OnSelectedTermChanged(null)) }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SignupNavigatorPreview(
    viewModel: SignupViewModel = hiltViewModel()
){
    SignupNavigator(
        onSignUpSuccess = {},
        onBack = {},
        viewModel = viewModel,
    )
}


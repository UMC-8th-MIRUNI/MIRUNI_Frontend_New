package com.miruni.feature.pwreset

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.miruni.core.designsystem.MiruniSize
import com.miruni.core.designsystem.MiruniTheme
import com.miruni.feature.pwreset.presentation.component.screen.PwResetCheckScreen
import com.miruni.feature.pwreset.presentation.component.screen.PwResetEmailScreen
import com.miruni.feature.pwreset.presentation.component.screen.PwResetNoticeScreen
import com.miruni.feature.pwreset.presentation.component.screen.PwResetSetPasswordScreen
import com.miruni.feature.pwreset.presentation.component.screen.PwResetSuccessScreen
import com.miruni.feature.pwreset.presentation.navigation.PwResetRoute

@Composable
fun PwResetNavigator(
    onLoginRestart: () -> Unit,
    viewModel: PwResetViewModel = hiltViewModel()
){
    val navController = rememberNavController()
    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry.value?.destination?.route
    val uiState = viewModel.viewState.collectAsStateWithLifecycle().value

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is PwResetContract.Effect.Navigation.ToHome -> {
                    onLoginRestart()
                }
                is PwResetContract.Effect.Navigation.ToRoute -> {
                    navController.navigate(effect.route.route){
                        launchSingleTop = true
                    }
                }
            }
        }
    }
    Column(
        modifier = Modifier.padding(top = MiruniSize.topbarHeight)
    ){
        PwResetHeader(
            modifier = Modifier.fillMaxWidth(),
            isLast = currentRoute == PwResetRoute.Success.route,
            onPrevClicked = {
                when (currentRoute) {
                    PwResetRoute.Email.route -> onLoginRestart()
                    else -> navController.popBackStack()
                }
            },
        )
        NavHost(
            navController = navController,
            startDestination = PwResetRoute.Email.route,
        ){
            composable(PwResetRoute.Email.route) {
                PwResetEmailScreen(
                    email = uiState.email.value,
                    canNext = uiState.canNext,
                    onEmailChanged = {
                        viewModel.setEvent(PwResetContract.Event.OnEmailChanged(it))
                    },
                    onNextClicked = {
                        viewModel.setEvent(PwResetContract.Event.OnNextClicked)
                    },
                    onLoginRestart = onLoginRestart
                )
            }
            composable(PwResetRoute.Notice.route) {
                PwResetNoticeScreen(
                    onNextClicked = {
                        viewModel.setEvent(PwResetContract.Event.OnNextClicked)
                    }
                )
            }
            composable(PwResetRoute.Check.route) {
                PwResetCheckScreen(
                    otpCode = uiState.otpCode.value,
                    email = uiState.email.value,
                    canNext = uiState.canNext,
                    onOtpCodeChanged = {
                        viewModel.setEvent(PwResetContract.Event.OnOtpCodeChanged(it))
                    },
                    onNextClicked = {
                        viewModel.setEvent(PwResetContract.Event.OnNextClicked)
                    }
                )
            }
            composable(PwResetRoute.SetPassword.route) {
                PwResetSetPasswordScreen(
                    password = uiState.password.value,
                    passwordCheck = uiState.passwordCheck.value,
                    passwordVisible = uiState.passwordVisible,
                    passwordCheckVisible = uiState.passwordCheckVisible,
                    canNext = uiState.canNext,
                    onPasswordChanged = {
                        viewModel.setEvent(PwResetContract.Event.OnPasswordChanged(it))
                    },
                    onPasswordCheckChanged = {
                        viewModel.setEvent(PwResetContract.Event.OnPasswordCheckChanged(it))
                    },
                    onTogglePasswordVisible = {
                        viewModel.setEvent(PwResetContract.Event.OnTogglePasswordVisible)
                    },
                    onTogglePasswordCheckVisible = {
                        viewModel.setEvent(PwResetContract.Event.OnTogglePasswordCheckVisible)
                    },
                    onLoginRestart = onLoginRestart,
                    onNextClicked = {
                        viewModel.setEvent(PwResetContract.Event.OnNextClicked)
                    }
                )
            }
            composable(PwResetRoute.Success.route) {
                PwResetSuccessScreen(
                    onNextClicked = {
                        viewModel.setEvent(PwResetContract.Event.OnNextClicked)
                    }
                )
            }
        }
    }
}

@Composable
private fun PwResetHeader(
    modifier : Modifier = Modifier,
    isLast : Boolean,
    onPrevClicked: () -> Unit,
){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if(!isLast){
            IconButton(
                onClick = onPrevClicked
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_chevron_right),
                    contentDescription = "뒤로 가기"
                )
            }
        }
    }


}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PwResetScreenPreview() {
    MiruniTheme {
        PwResetNavigator(
            onLoginRestart = {}
        )
    }
}
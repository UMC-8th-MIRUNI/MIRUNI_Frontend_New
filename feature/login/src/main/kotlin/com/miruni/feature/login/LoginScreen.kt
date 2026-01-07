package com.miruni.feature.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.miruni.feature.login.component.screen.LoginScreen
import com.miruni.feature.login.component.screen.NotificationScreen
import com.miruni.feature.login.navigation.LoginRoute
import kotlinx.coroutines.flow.collectLatest
import android.app.Activity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.auth.model.OAuthToken
import com.miruni.feature.login.component.screen.StartScreen
import com.miruni.feature.login.utils.helper.kakaoLogin
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume


@Composable
fun LoginNavigator(
    onLoginSuccess: () -> Unit,
    onSignUpClick: () -> Unit,
    onResetPasswordClick: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val uiState = viewModel.viewState.value
    val onEvent: (LoginContract.Event) -> Unit = viewModel::setEvent
    val navController = rememberNavController()
    val context = LocalContext.current
    val currentRoute = navController.currentBackStackEntry?.destination?.route


    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is LoginContract.Effect.Navigation.ToHome -> onLoginSuccess()
                is LoginContract.Effect.Navigation.ToSignUp -> onSignUpClick()
                is LoginContract.Effect.Navigation.ToResetPassword -> onResetPasswordClick()
                is LoginContract.Effect.Navigation.ToNotification -> navController.navigate(LoginRoute.Notification.route)
                is LoginContract.Effect.Navigation.ToStart -> navController.navigate(LoginRoute.Start.route)
                is LoginContract.Effect.Message.Toast -> {
                }
                is LoginContract.Effect.KakaoLogin -> {
                    val result = kakaoLogin(context)
                    result.onSuccess {
                        onEvent(LoginContract.Event.OnKakaoLoginSuccess(it))
                    }
                    result.onFailure {
                        onEvent(LoginContract.Event.OnKakaoLoginFail(it.message.toString()))
                    }
                }
            }
        }
    }
    NavHost(
        navController = navController,
        startDestination = LoginRoute.Start.route,
    ){
        composable(LoginRoute.Login.route){
            LoginScreen(
                uiState = uiState,
                onIdChange = { onEvent(LoginContract.Event.OnIdChanged(it)) },
                onPwChange = { onEvent(LoginContract.Event.OnPwChanged(it)) },
                onTogglePasswordVisible = { onEvent(LoginContract.Event.OnTogglePasswordVisible) },
                onAutoLoginChange = { onEvent(LoginContract.Event.OnAutoLoginChanged(it)) },
                onClearError = { onEvent(LoginContract.Event.OnClearError) },
                onLoginClick = { onEvent(LoginContract.Event.OnLoginClicked) },
                onSignUpClick = { onEvent(LoginContract.Event.OnSignUpClicked) },
                onResetPasswordClick = { onEvent(LoginContract.Event.OnResetPasswordClicked) },
                onGoogleLoginClick = { onEvent(LoginContract.Event.OnGoogleLoginClicked) },
                onKakaoLoginClick = { onEvent(LoginContract.Event.OnKakaoLoginClicked) },
            )
        }
        composable(LoginRoute.Notification.route){
            NotificationScreen(
                isDialogOpen = uiState.isDialogOpen,
                onOpenDialog = { onEvent(LoginContract.Event.OnOpenDialog) },
                onCloseDialog = { onEvent(LoginContract.Event.OnCloseDialog) },
                onNextClicked = { onEvent(LoginContract.Event.OnNotificationClicked) },
            )
        }
        composable(LoginRoute.Start.route){
            StartScreen(
                onStartedClicked = { onEvent(LoginContract.Event.OnStartedClicked) }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginNavigatorPreview() {
    LoginNavigator(
        onLoginSuccess = {},
        onSignUpClick = {},
        onResetPasswordClick = {},
    )
}





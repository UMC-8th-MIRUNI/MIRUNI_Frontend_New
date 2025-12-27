package com.miruni.feature.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.miruni.core.designsystem.AppTypography
import com.miruni.core.designsystem.Black
import com.miruni.core.designsystem.Gray
import com.miruni.core.designsystem.MainColor
import com.miruni.core.designsystem.MiruniTheme
import com.miruni.core.designsystem.White
import com.miruni.core.designsystem.Yellow
import com.miruni.feature.login.component.MiruniOutlinedTextField
import com.miruni.feature.login.component.screen.LoginScreen
import com.miruni.feature.login.component.screen.NotificationScreen
import com.miruni.feature.login.extension.noRippleClickable
import com.miruni.feature.login.model.TextInputField
import com.miruni.feature.login.navigation.LoginRoute
import kotlinx.coroutines.flow.collectLatest

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
            }
        }
    }
    NavHost(
        navController = navController,
        startDestination = LoginRoute.Login.route
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
                onGoogleLoginClick = { /*TODO */ },
                onKakaoLoginClick = { /* TODO */ },
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

        }
    }
}


package com.miruni.feature.pwreset

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.miruni.core.designsystem.MiruniTheme
import com.miruni.feature.pwreset.component.screen.PwResetCheckScreen
import com.miruni.feature.pwreset.component.screen.PwResetEmailScreen
import com.miruni.feature.pwreset.component.screen.PwResetNoticeScreen
import com.miruni.feature.pwreset.component.screen.PwResetSetPasswordScreen
import com.miruni.feature.pwreset.component.screen.PwResetSuccessScreen
import com.miruni.feature.pwreset.navigation.PwResetRoute

@Composable
fun PwResetNavigator(
    onExit: () -> Unit,
    onLoginRestart: () -> Unit,
){
    val pwResetNavController = rememberNavController()
    val backStackEntry = pwResetNavController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry.value?.destination?.route

    Column {
        PwResetHeader(
            modifier = Modifier.fillMaxWidth(),
            isLast = currentRoute == PwResetRoute.Success.route,
            onPrevClicked = {
                when(currentRoute){
                    PwResetRoute.Email.route -> onExit()
                    PwResetRoute.Notice.route -> pwResetNavController.navigate(PwResetRoute.Email.route)
                    PwResetRoute.Check.route -> pwResetNavController.navigate(PwResetRoute.Notice.route)
                    PwResetRoute.SetPassword.route -> pwResetNavController.navigate(PwResetRoute.Check.route)
                }
            },
        )
        NavHost(
            navController = pwResetNavController,
            startDestination = PwResetRoute.Email.route,
        ){
            composable(PwResetRoute.Email.route) {
                PwResetEmailScreen(
                    onNextClicked = {
                        pwResetNavController.navigate(PwResetRoute.Notice.route)
                    },
                    onLoginRestart = onLoginRestart
                )
            }
            composable(PwResetRoute.Notice.route) {
                PwResetNoticeScreen(
                    onNextClicked = {
                        pwResetNavController.navigate(PwResetRoute.Check.route)
                    }
                )
            }
            composable(PwResetRoute.Check.route) {
                PwResetCheckScreen(
                    email = "",
                    onNextClicked = {
                        pwResetNavController.navigate(PwResetRoute.SetPassword.route)
                    }
                )
            }
            composable(PwResetRoute.SetPassword.route) {
                PwResetSetPasswordScreen(
                    onLoginRestart = onLoginRestart,
                    onNextClicked = {
                        pwResetNavController.navigate(PwResetRoute.Success.route)
                    }
                )
            }
            composable(PwResetRoute.Success.route) {
                PwResetSuccessScreen(
                    onNextClicked = onLoginRestart
                )
            }
        }
    }
}

@Composable
fun PwResetHeader(
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
                onPrevClicked
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
            onExit = {},
            onLoginRestart = {}
        )
    }
}
package com.miruni.miruni_fe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.miruni.core.navigation.MiruniNavHost
import com.miruni.core.navigation.MiruniRoute
import com.miruni.designsystem.MiruniTheme
import com.miruni.feature.home.navigation.homeGraph
import com.miruni.feature.login.navigation.loginGraph
import com.miruni.feature.pwreset.navigation.passwordGraph
import com.miruni.feature.signup.navigation.signupGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiruniTheme(darkTheme = false) {
                val navController = rememberNavController()
                
                NavHost(
//                    navController = rememberNavController()
                    navController = navController,
                    startDestination = MiruniRoute.Login.route
                ) {
                    // Feature 모듈에서 제공하는 NavGraph 호출
                    loginGraph(
                        route = MiruniRoute.Login.route,
                        onLoginSuccess = { navController.navigate(MiruniRoute.Home.route) },
                        onSignUpClick = { navController.navigate(MiruniRoute.SignUp.route) },
                        onResetPasswordClick = { navController.navigate(MiruniRoute.ResetPassword.route) }
                    )

                    signupGraph(route = MiruniRoute.SignUp.route)
                    homeGraph(route = MiruniRoute.Home.route)
                    passwordGraph(route = MiruniRoute.ResetPassword.route)
                }
            }
        }
    }
}
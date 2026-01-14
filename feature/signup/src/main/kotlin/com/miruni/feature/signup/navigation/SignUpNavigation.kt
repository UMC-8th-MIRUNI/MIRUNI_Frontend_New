package com.miruni.feature.signup.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.miruni.core.navigation.MiruniRoute
import com.miruni.core.navigation.NavigationDestination
import com.miruni.feature.signup.SignupNavigator
import jakarta.inject.Inject

class SignUpNavigation @Inject constructor(
//    override val arguments: List<NamedNavArgument>
) : NavigationDestination {
    override val route: String = MiruniRoute.SignUp.route

    override fun register(
        builder: NavGraphBuilder,
        navController: NavHostController
    ) {
        builder.composable(route) {
            SignupNavigator(
                onSignUpSuccess = { navController.navigate(MiruniRoute.Login.route) },
                onBack = { navController.popBackStack() }
            )
        }
    }
}
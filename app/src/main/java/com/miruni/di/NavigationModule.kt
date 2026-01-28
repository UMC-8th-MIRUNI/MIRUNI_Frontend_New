package com.miruni.di

import com.miruni.core.navigation.NavigationDestination
import com.miruni.feature.aiplanner.navigation.AiPlannerNavigation
import com.miruni.feature.calendar.navigation.CalendarNavigation
import com.miruni.feature.home.navigation.HomeNavigation
import com.miruni.feature.login.presentation.component.navigation.LoginNavigation
import com.miruni.feature.mypage.navigation.MyPageNavigation
import com.miruni.feature.onboard.navigation.OnboardNavigation
import com.miruni.feature.pwreset.presentation.navigation.PwResetNavigation
import com.miruni.feature.signup.presentation.navigation.SignUpNavigation
import com.miruni.feature.splash.navigation.SplashNavigation
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
abstract class NavigationModule {

    @Binds
    @IntoSet
    abstract fun bindSplashNavigation(
        navigation: SplashNavigation
    ): NavigationDestination

    @Binds
    @IntoSet
    abstract fun bindOnboardingNavigation(
        navigation: OnboardNavigation
    ): NavigationDestination

    @Binds
    @IntoSet
    abstract fun bindHomeNavigation(
        navigation: HomeNavigation
    ): NavigationDestination

    @Binds
    @IntoSet
    abstract fun bindCalendarNavigation(
        navigation: CalendarNavigation
    ): NavigationDestination

    @Binds
    @IntoSet
    abstract fun bindSignupNavigation(
        navigation: SignUpNavigation
    ): NavigationDestination

    @Binds
    @IntoSet
    abstract fun bindLoginNavigation(
        navigation: LoginNavigation
    ): NavigationDestination

    @Binds
    @IntoSet
    abstract fun bindPwResetNavigation(
        navigation: PwResetNavigation
    ): NavigationDestination

    @Binds
    @IntoSet
    abstract fun bindMyPageNavigation(
        navigation: MyPageNavigation
    ): NavigationDestination

    @Binds
    @IntoSet
    abstract fun bindAiPlannerNavigation(
        navigation: AiPlannerNavigation
    ): NavigationDestination
}
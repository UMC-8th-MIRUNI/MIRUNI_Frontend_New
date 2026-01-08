package com.miruni.di

import com.miruni.core.navigation.NavigationDestination
import com.miruni.feature.calendar.navigation.CalendarNavigation
import com.miruni.feature.home.navigation.HomeNavigation
import com.miruni.feature.login.navigation.LoginNavigation
import com.miruni.feature.mypage.navigation.MyPageNavigation
import com.miruni.feature.pwreset.navigation.PwResetNavigation
import com.miruni.feature.signup.navigation.SignUpNavigation
import com.miruni.feature.splash.navigation.MiruniSplashNavigation
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
        navigation: MiruniSplashNavigation
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
}
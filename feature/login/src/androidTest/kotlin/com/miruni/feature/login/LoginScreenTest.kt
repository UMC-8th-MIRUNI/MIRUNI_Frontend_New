package com.miruni.feature.login

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@RunWith(AndroidJUnit4::class)
class LoginScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenSignUpButtonClicked_onSignUpClickIsCalled() {
        // Given
        val onSignUpClick: () -> Unit = mock()

        composeTestRule.setContent {
            LoginScreen(
                onLoginSuccess = {},
                onSignUpClick = onSignUpClick,
                onResetPasswordClick = {}
            )
        }

        // When
        composeTestRule.onNodeWithText("회원가입").performClick()

        // Then
        verify(onSignUpClick).invoke()
    }

    @Test
    fun whenLoginButtonClicked_onLoginSuccessIsCalled() {
        // Given
        val onLoginSuccess: () -> Unit = mock()

        composeTestRule.setContent {
            LoginScreen(
                onLoginSuccess = onLoginSuccess,
                onSignUpClick = {},
                onResetPasswordClick = {}
            )
        }

        // When
        composeTestRule.onNodeWithText("로그인").performClick()

        // Then
        verify(onLoginSuccess).invoke()
    }

    @Test
    fun whenPwResetClicked_onResetPasswordClickIsCalled() {
        // Given
        val onResetPasswordClick: () -> Unit = mock()

        composeTestRule.setContent {
            LoginScreen(
                onLoginSuccess = {},
                onSignUpClick = {},
                onResetPasswordClick = onResetPasswordClick
            )
        }

        // When
        composeTestRule.onNodeWithText("비밀번호 재설정").performClick()

        // Then
        verify(onResetPasswordClick).invoke()
    }
}
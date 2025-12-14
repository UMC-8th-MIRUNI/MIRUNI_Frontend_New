package com.miruni.signup

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.miruni.feature.signup.SignupScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify

@RunWith(AndroidJUnit4::class)
class SignupScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenSignUpButtonClicked_onSignUpClickIsCalled() {
        // Given
        val onSignUpClick: () -> Unit = mock()

        composeTestRule.setContent {
            SignupScreen(
                onSignUpSuccess = onSignUpClick
            )
        }

        // When
        composeTestRule.onNodeWithText("회원가입 성공").performClick()

        // Then
        verify(onSignUpClick).invoke()
    }
}
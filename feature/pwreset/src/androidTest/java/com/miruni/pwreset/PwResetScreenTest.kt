package com.miruni.pwreset

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.miruni.feature.pwreset.PwResetScreen

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Rule
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class PwResetScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenPwResetButtonClicked_onLoginClickIsCalled() {
        // Given
        val onPwResetClick: () -> Unit = mock()

        composeTestRule.setContent {
            PwResetScreen (
                onLoginRestart = onPwResetClick
            )
        }

        // When
        composeTestRule.onNodeWithText("로그인 재시도").performClick()

        // Then
        verify(onPwResetClick).invoke()
    }
}
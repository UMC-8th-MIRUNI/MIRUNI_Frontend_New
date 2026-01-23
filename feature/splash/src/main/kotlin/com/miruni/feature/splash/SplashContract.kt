package com.miruni.feature.splash

import com.miruni.core.common.ViewEvent
import com.miruni.core.common.ViewSideEffect
import com.miruni.core.common.ViewState

class SplashContract {
    sealed class Event : ViewEvent {
        data object Initialize : Event()
    }

    data class State(
        val isLoading: Boolean = false
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        sealed class Navigate : Effect() {
            data object ToAppOnboarding : Navigate()
            data object ToLogin : Navigate()
            data object ToHome : Navigate()
        }
    }
}
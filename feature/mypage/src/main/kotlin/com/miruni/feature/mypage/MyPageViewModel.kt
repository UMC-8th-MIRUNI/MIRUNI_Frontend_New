package com.miruni.feature.mypage

import com.miruni.core.common.BaseViewModel
import javax.inject.Inject

class MyPageViewModel @Inject constructor() : BaseViewModel<MyPageContract.Event, MyPageContract.State, MyPageContract.Effect>() {
    override fun setInitialState(): MyPageContract.State {
        TODO("Not yet implemented")
    }

    override fun handleEvents(event: MyPageContract.Event) {
        TODO("Not yet implemented")
    }

}
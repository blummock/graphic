package com.blummock.welcome.vm

import com.blummock.base.BaseViewModel

internal class WelcomeViewModel : BaseViewModel<WelcomeState, WelcomeEffect>(WelcomeState()) {

    fun setCount(count: String) {
        updateState {
            it.copy(pointsCount = count)
        }
    }

    fun goToGraphic() {
        kotlin.runCatching {
            state.value.pointsCount.toInt()
        }.onFailure {
            postErrorMessage("Incorrect input")
        }
    }
}
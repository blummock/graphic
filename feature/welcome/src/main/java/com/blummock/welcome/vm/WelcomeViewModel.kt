package com.blummock.welcome.vm

import com.blummock.base.BaseViewModel
import com.blummock.base.destinations.Destinations
import com.blummock.domain.provider.Provider
import com.blummock.domain.router.RouterArgs
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
internal class WelcomeViewModel @Inject constructor(
    provider: Provider
) : BaseViewModel<WelcomeState, WelcomeEffect>(WelcomeState(), provider) {

    fun setCount(count: String) {
        updateState {
            it.copy(pointsCount = count)
        }
    }

    fun goToGraphic() {
        kotlin.runCatching {
            val count = state.value.pointsCount.toInt()
            navigate(Destinations.GraphicDestination.buildRoute(RouterArgs.GraphicArgs(count)))
        }.onFailure {
            postErrorMessage("Incorrect input")
        }
    }
}
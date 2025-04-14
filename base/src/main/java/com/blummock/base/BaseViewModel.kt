package com.blummock.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blummock.domain.provider.Provider
import com.blummock.domain.router.Routes
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseViewModel<State, Effect : BaseEffect>(
    initialState: State,
    private val provider: Provider
) :
    ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    private val _effect = Channel<BaseEffect>(20, BufferOverflow.DROP_OLDEST)
    val effect = _effect.receiveAsFlow()

    private val mainScope = CoroutineScope(
        viewModelScope.coroutineContext + SupervisorJob() + CoroutineExceptionHandler { _, e ->
            provider.logger.error(e, "inside ViewModel ${e.message}")
            postErrorMessage(e.message ?: "Unknown error")
        }
    )

    fun sendEffect(effect: Effect) {
        launch {
            _effect.send(effect)
        }
    }

    protected fun navigate(routes: Routes) {
        launch {
            withContext(provider.dispatchers.main) {
                provider.router.navigate(routes)
            }
        }
    }

    protected fun launch(block: suspend CoroutineScope.() -> Unit) {
        mainScope.launch(block = block)
    }

    protected fun updateState(mutator: (State) -> State) {
        _state.update(mutator)
    }

    protected fun postErrorMessage(message: String) {
        _effect.trySend(BaseEffect.ErrorEffect(message))
    }

    fun navigateBack() {
        navigate(Routes.Back)
    }
}
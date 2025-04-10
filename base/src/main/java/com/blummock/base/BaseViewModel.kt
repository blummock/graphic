package com.blummock.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

abstract class BaseViewModel<State, Effect : BaseEffect>(initialState: State) : ViewModel() {

    private val state_ = MutableStateFlow(initialState)
    val state = state_.asStateFlow()

    private val effect_ = Channel<BaseEffect>(1)
    val effect = effect_.receiveAsFlow()

    protected fun updateState(mutator: (State) -> State) {
        state_.update(mutator)
    }

    fun postErrorMessage(message: String) {
        effect_.trySend(BaseEffect.ErrorEffect(message))
    }
}
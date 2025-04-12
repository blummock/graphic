package com.blummock.domain.dispatcher

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {

    val io: CoroutineDispatcher
    val main: CoroutineDispatcher
}
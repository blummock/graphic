package com.blummock.core.dispatcher

import com.blummock.domain.dispatcher.DispatcherProvider
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DispatcherProviderImpl @Inject constructor() : DispatcherProvider {
    override val io by lazy { Dispatchers.IO }
    override val main by lazy { Dispatchers.Main }
}
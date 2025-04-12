package com.blummock.core.dispatcher

import com.blummock.domain.dispatcher.DispatcherProvider
import jakarta.inject.Singleton
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@Singleton
class DispatcherProviderImpl @Inject constructor() : DispatcherProvider {
    override val io by lazy { Dispatchers.IO }
    override val main by lazy { Dispatchers.Main }
}
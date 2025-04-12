package com.blummock.domain.provider

import com.blummock.domain.dispatcher.DispatcherProvider
import com.blummock.domain.logger.Logger
import com.blummock.domain.router.Router

interface Provider {

    val logger: Logger
    val dispatchers: DispatcherProvider
    val router: Router
}
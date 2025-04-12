package com.blummock.core.provider

import com.blummock.domain.dispatcher.DispatcherProvider
import com.blummock.domain.logger.Logger
import com.blummock.domain.provider.Provider
import com.blummock.domain.router.Router
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProviderImpl @Inject constructor(
    override val logger: Logger,
    override val dispatchers: DispatcherProvider,
    override val router: Router
) : Provider
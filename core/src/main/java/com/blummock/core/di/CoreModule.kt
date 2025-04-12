package com.blummock.core.di

import com.blummock.core.dispatcher.DispatcherProviderImpl
import com.blummock.core.logger.LoggerImpl
import com.blummock.core.provider.ProviderImpl
import com.blummock.core.router.RouterImpl
import com.blummock.domain.dispatcher.DispatcherProvider
import com.blummock.domain.logger.Logger
import com.blummock.domain.provider.Provider
import com.blummock.domain.router.Router
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface CoreModule {

    @Binds
    fun provideLogger(impl: LoggerImpl): Logger

    @Binds
    fun provideProvider(impl: ProviderImpl): Provider

    @Binds
    fun provideRouter(impl: RouterImpl): Router

    @Binds
    fun provideDispatcherProvider(impl: DispatcherProviderImpl): DispatcherProvider
}
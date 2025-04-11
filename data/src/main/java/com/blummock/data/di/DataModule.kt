package com.blummock.data.di

import com.blummock.data.repository.PointsRepositoryImpl
import com.blummock.domain.repository.PointsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun providePointsRepository(impl: PointsRepositoryImpl): PointsRepository
}
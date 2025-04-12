package com.blummock.domain.router

import kotlinx.coroutines.flow.Flow

interface Router {

    suspend fun navigate(routes: Routes)
    fun observe(): Flow<Routes>
}
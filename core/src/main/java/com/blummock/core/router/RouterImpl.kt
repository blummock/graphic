package com.blummock.core.router

import com.blummock.domain.router.Router
import com.blummock.domain.router.Routes
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RouterImpl @Inject constructor() : Router {

    private val routerStore = Channel<Routes>(1)

    override suspend fun navigate(routes: Routes) {
        routerStore.send(routes)
    }

    override fun observe() = routerStore.receiveAsFlow()
}
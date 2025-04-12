package com.blummock.domain.router

interface Routes {
    data object Back : Routes
    open class Link(
        val uri: String,
    ) : Routes
}
package com.blummock.core

sealed interface Routes {
    data object Back : Routes
    sealed class Link(
        val uri: String,
    ) : Routes
}
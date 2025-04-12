package com.blummock.domain.router

sealed interface RouterArgs {
    data class GraphicArgs(
        val pointsCount: Int
    ) : RouterArgs

    data object Empty : RouterArgs
}
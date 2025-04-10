package com.blummock.router

import android.os.Bundle

private const val DOMAIN = "graphicapp://"

sealed interface Destinations<T : RouterArgs> {

    fun getArgs(bundle: Bundle): T

    fun buildRoute(args: T): Routes

    data object GraphicDestination : Destinations<RouterArgs.GraphicArgs> {
        override fun getArgs(bundle: Bundle): RouterArgs.GraphicArgs {
            val pointsCount = bundle.getInt("pointsCount")
            return RouterArgs.GraphicArgs(pointsCount)
        }

        override fun buildRoute(args: RouterArgs.GraphicArgs) = GraphicRoute(args.pointsCount)

        class GraphicRoute internal constructor(pointsCount: Int) : Routes.Link(
            "${DOMAIN}graphic/${pointsCount}"
        )
    }

    data object WelcomeDestination : Destinations<RouterArgs.Empty> {
        override fun getArgs(bundle: Bundle) = RouterArgs.Empty

        override fun buildRoute(args: RouterArgs.Empty) = GraphicRoute

        data object GraphicRoute : Routes.Link(
            "graphicapp://welcome"
        )
    }
}
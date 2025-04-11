package com.blummock.graphic.vm

import com.blummock.domain.entity.Point

internal data class GraphicState(
    val points: List<Point> = emptyList(),
)
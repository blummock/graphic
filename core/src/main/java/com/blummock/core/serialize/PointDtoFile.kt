package com.blummock.core.serialize

import kotlinx.serialization.Serializable

@Serializable
internal data class PointDtoFile(
    val x: Float,
    val y: Float,
)
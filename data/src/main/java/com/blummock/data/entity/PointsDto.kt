package com.blummock.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class PointsDto(
    val points: List<PointDto>
)

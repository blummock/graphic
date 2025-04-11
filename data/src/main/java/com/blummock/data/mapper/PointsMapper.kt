package com.blummock.data.mapper

import com.blummock.data.entity.PointsDto
import com.blummock.domain.entity.Point

fun PointsDto.toDomain() = points.map { Point(it.x, it.y) }
package com.blummock.domain.repository

import com.blummock.domain.entity.Point

interface PointsRepository {
    suspend fun getPoints(count: Int): List<Point>
}
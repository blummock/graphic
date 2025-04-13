package com.blummock.data.repository

import com.blummock.data.api.PointsApi
import com.blummock.domain.entity.Point
import com.blummock.domain.repository.PointsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PointsRepositoryImpl @Inject constructor(
    private val pointsApi: PointsApi
) : PointsRepository {
    override suspend fun getPoints(count: Int): List<Point> = withContext(Dispatchers.IO) {
//        pointsApi.getPoints(count).toDomain()
        pointsStub()
    }

    private fun pointsStub() = listOf(
        Point(-1.1f, -1f),
        Point(-2f, -2f),
        Point(-3f, -3f),
        Point(-4f, -0.1f),
        Point(-5f, -0.5f),
        Point(-6f, -6f),
        Point(-16f, -6f),
        Point(-26f, -6f),
        Point(-27f, -60f),
        Point(-28f, -6f),
        Point(-29f, -7f),
        Point(-30f, -6f),
        Point(1.1f, 1f),
        Point(2f, 2f),
        Point(3f, 3f),
        Point(4f, 0.1f),
        Point(5f, 0.5f),
        Point(6f, 6f),
        Point(16f, 6f),
        Point(26f, 6f),
        Point(27f, 60f),
        Point(28f, 6f),
        Point(29f, 7f),
        Point(30f, 6f),
    ).map { Point(it.x, it.y) }
}
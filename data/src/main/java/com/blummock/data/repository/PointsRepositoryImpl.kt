package com.blummock.data.repository

import com.blummock.data.api.PointsApi
import com.blummock.data.mapper.toDomain
import com.blummock.domain.entity.Point
import com.blummock.domain.repository.PointsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PointsRepositoryImpl @Inject constructor(
    private val pointsApi: PointsApi
) : PointsRepository {
    override suspend fun getPoints(count: Int): List<Point> = withContext(Dispatchers.IO) {
        pointsApi.getPoints(count).toDomain()
    }

    private fun pointsStub() = listOf(
        Point(1f, 1f),
        Point(2f, 2f),
        Point(3f, 3f),
        Point(4f, 4f),
        Point(5f, 5f),
        Point(6f, 6f),
    )
}
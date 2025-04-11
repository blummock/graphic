package com.blummock.data.repository

import com.blummock.domain.entity.Point
import com.blummock.domain.repository.PointsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PointsRepositoryImpl @Inject constructor(
) : PointsRepository {
    override suspend fun getPoints(): List<Point> = withContext(Dispatchers.IO) {
        delay(1000)
        listOf(
            Point(1f, 1f),
            Point(2f, 2f),
            Point(3f, 3f),
            Point(4f, 4f),
            Point(5f, 5f),
            Point(6f, 6f),
        )
    }
}
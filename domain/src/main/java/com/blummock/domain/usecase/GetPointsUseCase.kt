package com.blummock.domain.usecase

import com.blummock.domain.repository.PointsRepository
import javax.inject.Inject

class GetPointsUseCase @Inject constructor(
    private val pointsRepository: PointsRepository,
) {
    suspend operator fun invoke(count: Int) = pointsRepository.getPoints(count)
}
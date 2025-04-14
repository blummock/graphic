package com.blummock.domain.usecase

import com.blummock.domain.entity.Point
import com.blummock.domain.repository.FileSystemRepository
import javax.inject.Inject

class SaveGraphicUseCase @Inject constructor(
    private val fileSystemRepository: FileSystemRepository,
) {
    suspend operator fun invoke(points: List<Point>): String {
        return fileSystemRepository.saveGraphic(points)
    }
}
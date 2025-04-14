package com.blummock.domain.repository

import com.blummock.domain.entity.Point

interface FileSystemRepository {

    suspend fun saveGraphic(points: List<Point>): String
}
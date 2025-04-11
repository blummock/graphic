package com.blummock.data.api

import com.blummock.data.entity.PointsDto
import retrofit2.http.GET
import retrofit2.http.Query

interface PointsApi {

    @GET("/api/test/points")
    suspend fun getPoints(@Query("count") count: Int): PointsDto
}
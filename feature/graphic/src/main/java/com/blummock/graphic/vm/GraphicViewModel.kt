package com.blummock.graphic.vm

import android.graphics.PointF
import android.os.Bundle
import com.blummock.base.BaseViewModel
import com.blummock.base.destinations.Destinations
import com.blummock.domain.provider.Provider
import com.blummock.domain.usecase.GetPointsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class GraphicViewModel @Inject constructor(
    private val getPointsUseCase: GetPointsUseCase,
    provider: Provider,
) : BaseViewModel<GraphicState, GraphicEffect>(GraphicState(), provider) {

    fun init(args: Bundle) {
        launch {
            val pointsCount = Destinations.GraphicDestination.getArgs(args).pointsCount
            val points = getPointsUseCase.invoke(pointsCount).sortedBy { it.x }
                .map { PointF(it.x, it.y) }
            updateState {
                it.copy(points = points)
            }
        }
    }
}
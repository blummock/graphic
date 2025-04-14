package com.blummock.graphic.vm

import android.graphics.PointF
import android.os.Bundle
import com.blummock.base.BaseViewModel
import com.blummock.base.destinations.Destinations
import com.blummock.domain.entity.Point
import com.blummock.domain.provider.Provider
import com.blummock.domain.usecase.GetPointsUseCase
import com.blummock.domain.usecase.SaveGraphicUseCase
import dagger.Lazy
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class GraphicViewModel @Inject constructor(
    private val getPointsUseCase: GetPointsUseCase,
    private val saveGraphicUseCase: Lazy<SaveGraphicUseCase>,
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

    fun saveGraphic() {
        if (state.value.points.isNotEmpty()) {
            launch {
                val fileNme = saveGraphicUseCase.get().invoke(
                    state.value.points.map { Point(it.x, it.y) }
                )
                sendEffect(GraphicEffect.SaveGraphic(fileNme))
            }
        }
    }
}
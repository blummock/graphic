package com.blummock.graphic.vm

import androidx.lifecycle.viewModelScope
import com.blummock.base.BaseViewModel
import com.blummock.domain.usecase.GetPointsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class GraphicViewModel @Inject constructor(
    private val getPointsUseCase: GetPointsUseCase,
) : BaseViewModel<GraphicState, GraphicEffect>(GraphicState()) {


    fun init(pointsCount: Int) {
        viewModelScope.launch {
            val points = getPointsUseCase.invoke(pointsCount)
            updateState {
                it.copy(points = points)
            }
        }
    }
}
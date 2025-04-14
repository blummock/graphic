package com.blummock.graphic.vm

import com.blummock.base.BaseEffect

internal sealed interface GraphicEffect : BaseEffect {
    data class SaveGraphic(val fileName: String) : GraphicEffect
}
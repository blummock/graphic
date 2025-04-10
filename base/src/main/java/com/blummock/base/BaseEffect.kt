package com.blummock.base

interface BaseEffect {
    data class ErrorEffect(val message: String) : BaseEffect
}
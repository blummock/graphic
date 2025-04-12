package com.blummock.domain.logger

interface Logger {

    fun debug(msg: String)
    fun error(throwable: Throwable, msg: String)
    fun info(msg: String)
}
package com.blummock.core.logger

import android.util.Log
import com.blummock.domain.logger.Logger
import javax.inject.Inject
import javax.inject.Singleton

private const val loggerId = "CUSTOM_LOGGER"

@Singleton
class LoggerImpl @Inject constructor() : Logger {
    override fun debug(msg: String) {
        Log.d(loggerId, msg)
    }

    override fun error(throwable: Throwable, msg: String) {
        Log.e(loggerId, msg, throwable)
    }

    override fun info(msg: String) {
        Log.i(loggerId, msg)
    }
}
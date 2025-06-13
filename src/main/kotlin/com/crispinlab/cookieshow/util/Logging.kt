package com.crispinlab.cookieshow.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory

object Logging {
    fun <T : Any> getLogger(clazz: Class<T>): Logger = LoggerFactory.getLogger(clazz)

    fun <T> logFor(
        log: Logger,
        function: (MutableMap<String, Any>) -> T?
    ): T {
        val logInfo: MutableMap<String, Any> = mutableMapOf()

        val startedAt: Long = now()
        logInfo["startedAt"] = startedAt

        val result: T? = function.invoke(logInfo)

        val endedAt: Long = now()
        logInfo["endedAt"] = endedAt

        logInfo["timeTaken"] = measureTime(startedAt, endedAt)

        log.info(logInfo.toString())
        return result ?: throw IllegalArgumentException()
    }

    private fun now(): Long = System.currentTimeMillis()

    private fun measureTime(
        startedAt: Long,
        endedAt: Long
    ): Long = endedAt - startedAt
}

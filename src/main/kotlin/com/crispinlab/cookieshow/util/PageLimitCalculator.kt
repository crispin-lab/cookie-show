package com.crispinlab.cookieshow.util

object PageLimitCalculator {
    fun calculatePageLimit(
        page: Long,
        pageSize: Long,
        pageGroupSize: Int = 10
    ): Long {
        val groupNumber: Long = ((page - 1) / pageGroupSize) + 1
        val itemsPerGroup: Long = pageSize * pageGroupSize
        val lastItemIndex: Long = groupNumber * itemsPerGroup + 1
        return lastItemIndex
    }
}

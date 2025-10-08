package com.bookingproviderservice.common

import com.bookingproviderservice.model.dto.TimeStepType
import java.time.LocalDateTime

fun timeStepCreate(
    startDateTime: LocalDateTime,
    endDateTime: LocalDateTime,
    timeStepType: TimeStepType? = TimeStepType.HOUR
): List<LocalDateTime> {
    val times = mutableListOf<LocalDateTime>()
    times.add(startDateTime)

    var previousTimes = startDateTime
    while (previousTimes.isBefore(endDateTime)) {
        when (timeStepType) {
            TimeStepType.HALF_HOUR -> previousTimes = previousTimes.plusMinutes(30L)
            else -> previousTimes = previousTimes.plusHours(1L)
        }
        times += previousTimes
    }
    return times
}
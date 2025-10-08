package com.bookingproviderservice.common

import com.bookingproviderservice.model.dto.TimeStepType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import kotlin.test.assertEquals

class CommonTest {

    @Test
    fun timeStepCreateTest() {
        val startDate = LocalDateTime.of(2020, 1, 1, 11, 0)
        val endDate = startDate.plusHours(2)
        val result = timeStepCreate(startDate, endDate)
        assertEquals(3, result.count())
        assertThat(result).usingRecursiveFieldByFieldElementComparatorIgnoringFields()
            .isEqualTo(listOf(startDate, startDate.plusHours(1L), endDate))
    }

    @Test
    fun timeStepCreateHalfHourTest() {
        val startDate = LocalDateTime.of(2020, 1, 1, 11, 0)
        val endDate = startDate.plusHours(2)
        val result = timeStepCreate(startDate, endDate, TimeStepType.HALF_HOUR)
        assertEquals(5, result.count())
        assertThat(result).usingRecursiveFieldByFieldElementComparatorIgnoringFields()
            .isEqualTo(
                listOf(
                    startDate,
                    startDate.plusMinutes(30L),
                    startDate.plusMinutes(60L),
                    startDate.plusMinutes(90L),
                    endDate
                )
            )
    }
}
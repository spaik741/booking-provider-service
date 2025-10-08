package com.bookingproviderservice.model.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.validation.constraints.AssertTrue
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

data class ScheduleCreateRequest(
    @field:NotNull
    val startDate: LocalDateTime,
    @field:NotNull
    val endDate: LocalDateTime,
    val timeStep: TimeStepType?
) {
    @JsonIgnore
    @AssertTrue(message = "endDate cannot be equal or more than startDate")
    fun isValidRangeDates(): Boolean {
        return startDate.isBefore(endDate) && endDate.toLocalDate().isEqual(startDate.toLocalDate())
    }
}

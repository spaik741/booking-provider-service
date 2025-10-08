package com.bookingproviderservice.model.dto

import java.time.LocalDate
import java.time.LocalTime

data class ScheduleResponse(
    var companyName: String? = null,
    var date: LocalDate? = null,
    var times: List<LocalTime>? = null
)

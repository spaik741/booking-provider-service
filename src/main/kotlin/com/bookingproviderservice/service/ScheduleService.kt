package com.bookingproviderservice.service

import com.bookingproviderservice.model.dto.BookRecordResponse
import com.bookingproviderservice.model.dto.ScheduleCreateRequest
import com.bookingproviderservice.model.dto.ScheduleResponse
import java.time.LocalDateTime

interface ScheduleService {
    fun createSchedules(companyId: Long, scheduleCreateRequest: ScheduleCreateRequest): ScheduleResponse?
    fun bookRecord(companyName: String, dateTime: LocalDateTime, findNear: Boolean = false): BookRecordResponse
}
package com.bookingproviderservice.controller

import com.bookingproviderservice.model.dto.BookRecordResponse
import com.bookingproviderservice.model.dto.ScheduleCreateRequest
import com.bookingproviderservice.model.dto.ScheduleResponse
import com.bookingproviderservice.service.ScheduleService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/v1/provider/schedule")
class ScheduleController(
    private val scheduleService: ScheduleService
) {
    @PostMapping("/registration")
    fun createSchedules(
        @RequestParam(value = "companyId") companyId: Long, @RequestBody scheduleCreateRequest: ScheduleCreateRequest
    ): ResponseEntity<ScheduleResponse> {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(scheduleService.createSchedules(companyId, scheduleCreateRequest))
    }

    @PostMapping("/book")
    fun bookSchedule(
        @RequestParam(value = "companyName") companyName: String,
        @RequestParam(value = "dateTime") dateTime: LocalDateTime
    ): ResponseEntity<BookRecordResponse> {
        return ResponseEntity.ok(scheduleService.bookRecord(companyName, dateTime))
    }
}
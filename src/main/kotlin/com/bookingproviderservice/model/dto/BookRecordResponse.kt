package com.bookingproviderservice.model.dto

import java.time.LocalDateTime

data class BookRecordResponse(
    var isSuccess: Boolean,
    var dateTime: LocalDateTime? = null
)
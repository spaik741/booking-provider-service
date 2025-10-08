package com.bookingproviderservice.validator

import com.bookingproviderservice.model.dto.ScheduleCreateRequest
import org.springframework.stereotype.Component
import org.springframework.validation.Errors
import org.springframework.validation.Validator

@Component
class ScheduleCreateRequestValidator : Validator {
    override fun supports(clazz: Class<*>): Boolean {
        return ScheduleCreateRequest::class.java.isAssignableFrom(clazz)
    }

    override fun validate(target: Any, errors: Errors) {
        val scheduleCreateRequest = target as ScheduleCreateRequest
        val startDate = scheduleCreateRequest.startDate
        val endDate = scheduleCreateRequest.endDate
        if (!startDate.isBefore(endDate) && !endDate.toLocalDate().isEqual(startDate.toLocalDate())) {
            errors.rejectValue(
                "endDate",
                "date.invalid",
                "endDate can't be more or equal then startDate"
            )
        }

    }
}
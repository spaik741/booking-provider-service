package com.bookingproviderservice.repository

import com.bookingproviderservice.model.ScheduleEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface ScheduleRepository : JpaRepository<ScheduleEntity, Long> {
    fun findAllByCompanyIdAndIsFree(
        companyId: Long,
        isFree: Boolean = false
    ): MutableList<ScheduleEntity>
}
package com.bookingproviderservice.repository

import com.bookingproviderservice.model.ScheduleEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ScheduleRepository : JpaRepository<ScheduleEntity, Long> {
    fun findAllByCompanyIdAndIsFree(
        companyId: Long,
        isFree: Boolean = true
    ): MutableList<ScheduleEntity>
}
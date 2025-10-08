package com.bookingproviderservice.service.impl

import com.bookingproviderservice.common.timeStepCreate
import com.bookingproviderservice.mapper.ScheduleMapper
import com.bookingproviderservice.model.ScheduleEntity
import com.bookingproviderservice.model.dto.BookRecordResponse
import com.bookingproviderservice.model.dto.ScheduleCreateRequest
import com.bookingproviderservice.model.dto.ScheduleResponse
import com.bookingproviderservice.repository.CompanyRepository
import com.bookingproviderservice.repository.ScheduleRepository
import com.bookingproviderservice.service.ScheduleService
import com.bookingservice.exception.ProviderServiceException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ScheduleServiceImpl(
    private val scheduleRepository: ScheduleRepository,
    private val companyRepository: CompanyRepository,
    private val scheduleMapper: ScheduleMapper
) : ScheduleService {

    override fun createSchedules(companyId: Long, scheduleCreateRequest: ScheduleCreateRequest): ScheduleResponse? {
        val companyEntity = companyRepository.findById(companyId).orElseThrow {
            ProviderServiceException(HttpStatus.NOT_FOUND, "Company not found with id: $companyId")
        }

        val timeSteps = timeStepCreate(
            scheduleCreateRequest.startDate,
            scheduleCreateRequest.endDate,
            scheduleCreateRequest.timeStep
        )
        val scheduleEntities =
            timeSteps.map { time -> scheduleMapper.toEntity(companyId, time) }
        scheduleRepository.saveAll(scheduleEntities)
        return scheduleMapper.toResponse(companyEntity, timeSteps)
    }

    override fun bookRecord(companyName: String, dateTime: LocalDateTime, findNear: Boolean): BookRecordResponse {
        val companyEntity = companyRepository.findByName(companyName) ?: throw ProviderServiceException(
            HttpStatus.NOT_FOUND,
            "Company not found with name: $companyName"
        )
        val records = scheduleRepository.findAllByCompanyIdAndIsFree(companyEntity.id!!)
        val record = if (findNear) {
            records.firstOrNull { record -> !record.dateTime!!.isBefore(dateTime) }
        } else {
            records.firstOrNull { record -> record.dateTime!!.isEqual(dateTime) }
        }
        return reserveRecord(record)
    }

    private fun reserveRecord(record: ScheduleEntity?): BookRecordResponse {
        record?.isFree = false
        return if (record == null) {
            BookRecordResponse(false)
        } else {
            scheduleRepository.save(record)
            BookRecordResponse(true, record.dateTime)
        }
    }
}
package com.bookingproviderservice.mapper

import com.bookingproviderservice.model.CompanyEntity
import com.bookingproviderservice.model.ScheduleEntity
import com.bookingproviderservice.model.dto.ScheduleResponse
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Named
import java.time.LocalDateTime

@Mapper(componentModel = "spring")
abstract class ScheduleMapper {

    @Mapping(target = "companyName", source = "companyEntity.name")
    @Mapping(target = "date", expression = "java(times.get(0).toLocalDate())")
    @Mapping(target = "times", source = "times", qualifiedByName = ["convertToTimes"])
    abstract fun toResponse(companyEntity: CompanyEntity, times: List<LocalDateTime>): ScheduleResponse?

    @Mapping(target = "companyId", source = "companyId")
    @Mapping(target = "dateTime", source = "dateTime")
    @Mapping(target = "free", constant = "true")
    abstract fun toEntity(companyId: Long, dateTime: LocalDateTime): ScheduleEntity?

    @Named("convertToTimes")
    protected fun convertToTimes(times: List<LocalDateTime>) = times.map { time -> time.toLocalTime() }
}
package com.bookingproviderservice.mapper

import com.bookingproviderservice.model.CompanyEntity
import com.bookingproviderservice.model.dto.CompanyDto
import com.bookingproviderservice.model.dto.CompanyRegistrationRequest
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
abstract class CompanyMapper {
    abstract fun mapToEntity(source: CompanyRegistrationRequest): CompanyEntity?
    abstract fun mapToDto(source: CompanyEntity): CompanyDto?
}
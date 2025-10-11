package com.bookingproviderservice.mapper

import com.bookingproviderservice.model.CompanyEntity
import com.bookingproviderservice.model.dto.CompanyDto
import com.bookingproviderservice.model.dto.CompanyRegistrationRequest
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Named

@Mapper(componentModel = "spring")
abstract class CompanyMapper {
    @Mapping(target = "phone", source = "phone", qualifiedByName = ["replaceBlanks"])
    abstract fun mapToEntity(source: CompanyRegistrationRequest): CompanyEntity?
    abstract fun mapToDto(source: CompanyEntity): CompanyDto?

    @Named("replaceBlanks")
    protected fun replaceBlanks(str: String) = str.replace("\\s".toRegex(), "")
}
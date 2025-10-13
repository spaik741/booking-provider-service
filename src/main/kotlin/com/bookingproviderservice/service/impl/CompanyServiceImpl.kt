package com.bookingproviderservice.service.impl

import com.bookingproviderservice.repository.CompanyRepository
import com.bookingproviderservice.mapper.CompanyMapper
import com.bookingproviderservice.model.dto.CompanyDto
import com.bookingproviderservice.model.dto.CompanyRegistrationRequest
import com.bookingproviderservice.service.CompanyService
import com.bookingservice.exception.ProviderServiceException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrElse

@Service
class CompanyServiceImpl(
    private val companyRepository: CompanyRepository,
    private val companyMapper: CompanyMapper
) : CompanyService {
    override fun registrationCompany(registrationRequest: CompanyRegistrationRequest) {
        if (companyRepository.existsByNameOrPhone(registrationRequest.name!!, registrationRequest.phone!!)) {
            throw ProviderServiceException(HttpStatus.CONFLICT, "company exists already")
        }
        companyMapper.mapToEntity(registrationRequest)?.let { companyEntity ->
            companyRepository.save(companyEntity)
        } ?: throw ProviderServiceException(HttpStatus.CONFLICT, "company didn't save")
    }

    override fun findCompany(companyId: Long): CompanyDto {
        return companyRepository.findById(companyId)
            .getOrElse { throw ProviderServiceException(HttpStatus.NOT_FOUND, "company not found") }
            .let { companyEntity -> companyMapper.mapToDto(companyEntity)!! }
    }
}
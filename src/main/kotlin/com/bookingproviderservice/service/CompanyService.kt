package com.bookingproviderservice.service

import com.bookingproviderservice.model.dto.CompanyDto
import com.bookingproviderservice.model.dto.CompanyRegistrationRequest

interface CompanyService {
    fun registrationCompany(registrationRequest: CompanyRegistrationRequest)
    fun findCompany(companyId: Long): CompanyDto
}
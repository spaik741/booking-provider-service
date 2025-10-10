package com.bookingproviderservice.controller

import com.bookingproviderservice.model.dto.CompanyDto
import com.bookingproviderservice.model.dto.CompanyRegistrationRequest
import com.bookingproviderservice.service.CompanyService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/provider/company")
class CompanyController(
    private val companyService: CompanyService
) {

    @PostMapping("/registration")
    fun registrationCompany(@Valid @RequestBody registrationRequest: CompanyRegistrationRequest): ResponseEntity<Any> {
        companyService.registrationCompany(registrationRequest)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @GetMapping("/{companyId}")
    fun findCompany(@PathVariable companyId: Long): ResponseEntity<CompanyDto> {
        return ResponseEntity.ok(companyService.findCompany(companyId))
    }
}
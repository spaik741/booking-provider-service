package com.bookingproviderservice.model.dto

import com.bookingproviderservice.validator.ValidPhoneNumber
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class CompanyRegistrationRequest(
    @field:NotBlank
    var name: String? = null,
    var email: String? = null,
    var website: String? = null,
    @field:ValidPhoneNumber
    var phone: String? = null,
    @field:NotNull
    @field:Valid
    var address: AddressDto? = null
)
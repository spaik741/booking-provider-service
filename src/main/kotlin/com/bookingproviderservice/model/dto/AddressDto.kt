package com.bookingproviderservice.model.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class AddressDto(
    @field:NotBlank
    var street: String? = null,
    @field:NotBlank
    var city: String? = null,
    @field:NotNull
    var homeNumber: String? = null,
    @field:NotBlank
    var country: String? = null
)

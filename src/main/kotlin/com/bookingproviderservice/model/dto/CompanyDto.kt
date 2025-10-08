package com.bookingproviderservice.model.dto

data class CompanyDto(
    var name: String? = null,
    var email: String? = null,
    var website: String? = null,
    var phone: String? = null,
    var address: AddressDto? = null
)
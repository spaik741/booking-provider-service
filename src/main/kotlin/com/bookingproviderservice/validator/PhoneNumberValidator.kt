package com.bookingproviderservice.validator

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class PhoneNumberValidator : ConstraintValidator<ValidPhoneNumber, String> {
    private companion object {
        const val PHONE_PATTERN = "/^(\\+|)(7|8)( |)\\d{3}( |)\\d{3}( |)(\\d{2}( |)){2}\$/"
    }

    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        return value?.matches(PHONE_PATTERN.toRegex()) ?: false
    }
}
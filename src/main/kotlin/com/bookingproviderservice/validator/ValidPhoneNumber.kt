package com.bookingproviderservice.validator

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [PhoneNumberValidator::class])
annotation class ValidPhoneNumber(
    val message: String = "Неверный формат номера телефона",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)

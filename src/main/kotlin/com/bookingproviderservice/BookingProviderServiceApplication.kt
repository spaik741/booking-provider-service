package com.bookingproviderservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BookingProviderServiceApplication

fun main(args: Array<String>) {
    runApplication<BookingProviderServiceApplication>(*args)
}

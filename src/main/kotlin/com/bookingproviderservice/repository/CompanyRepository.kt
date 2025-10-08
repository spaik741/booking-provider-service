package com.bookingproviderservice.repository

import com.bookingproviderservice.model.CompanyEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CompanyRepository : JpaRepository<CompanyEntity, Long> {
    fun existsByNameOrPhone(name: String, phoneNumber: String): Boolean
    fun findByName(name: String): CompanyEntity?
}
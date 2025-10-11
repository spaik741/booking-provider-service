package com.bookingproviderservice.controller

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.MockMvc


abstract class BaseMvcTest {
    @Autowired
    protected lateinit var entityManager: EntityManager
    @Autowired
    protected lateinit var mockMvc: MockMvc
    @Autowired
    protected lateinit var objectMapper: ObjectMapper
}
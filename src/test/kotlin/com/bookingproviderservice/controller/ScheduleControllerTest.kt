package com.bookingproviderservice.controller

import com.bookingproviderservice.model.ScheduleEntity
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlGroup
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import kotlin.test.assertEquals

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@SpringBootTest
class ScheduleControllerTest : BaseMvcTest() {

    private val PATH = "/v1/provider/schedule"

    @Test
    @SqlGroup(
        Sql(scripts = arrayOf("classpath:insert-company.sql"), executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        Sql(scripts = arrayOf("classpath:clear-company.sql", "classpath:clear-schedule.sql"), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    )
    fun registrySchedule() {
        val createRequest = javaClass.getResource("/json/scheduleRegistrationRequest.json")!!.readText(Charsets.UTF_8)
        val expectedResponse = javaClass.getResource("/json/scheduleResponse.json")!!.readText(Charsets.UTF_8)
        val result = mockMvc.perform(
            post("$PATH/registration").param("companyId", "101").contentType(MediaType.APPLICATION_JSON)
                .content(createRequest)
        ).andReturn()
        assertEquals(201, result.response.status)
        val queryResult = scheduleEntities("101")
        assertEquals(3, queryResult.size)
        assertEquals(expectedResponse, result.response.contentAsString)
    }

    @Test
    fun registryScheduleNotExistCompany() {
        val createRequest = javaClass.getResource("/json/scheduleRegistrationRequest.json")!!.readText(Charsets.UTF_8)
        val result = mockMvc.perform(
            post("$PATH/registration").param("companyId", "999").contentType(MediaType.APPLICATION_JSON)
                .content(createRequest)
        ).andReturn()
        assertEquals(404, result.response.status)
        val queryResult = scheduleEntities("999")
        assertEquals(0, queryResult.size)
    }

    @Test
    @SqlGroup(
        Sql(
            scripts = arrayOf("classpath:insert-company.sql", "classpath:insert-schedule.sql"),
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
        ), Sql(
            scripts = arrayOf("classpath:clear-company.sql", "classpath:clear-schedule.sql"),
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
        )
    )
    fun bookSchedule() {
        val expectedResponse = javaClass.getResource("/json/bookRecordResponse.json")!!
            .readText(Charsets.UTF_8)
        val result = mockMvc.perform(
            post("$PATH/book")
                .param("companyName", "New Garage")
                .param("dateTime", "2025-10-14T14:00:00")
        ).andReturn()
        assertEquals(expectedResponse, result.response.contentAsString)
        val queryResult = scheduleEntities("101")
        assertEquals(1, queryResult.size)
        assertEquals(false, queryResult.first().isFree)
    }

    @Test
    @SqlGroup(
        Sql(
            scripts = arrayOf("classpath:insert-company.sql", "classpath:insert-schedule.sql"),
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
        ), Sql(
            scripts = arrayOf("classpath:clear-company.sql", "classpath:clear-schedule.sql"),
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
        )
    )
    fun bookScheduleWithoutTime() {
        val result = mockMvc.perform(
            post("$PATH/book")
                .param("companyName", "New Garage")
                .param("dateTime", "2025-10-14T17:00:00")
        ).andReturn()
        assertEquals("{\"isSuccess\":false,\"dateTime\":null}", result.response.contentAsString)
        val queryResult = scheduleEntities("101")
        assertEquals(1, queryResult.size)
        assertEquals(true, queryResult.first().isFree)
    }

    private fun scheduleEntities(companyId: String): List<ScheduleEntity> {
        val queryResult = entityManager.createQuery(
            "select s from ScheduleEntity s where companyId = :companyId", ScheduleEntity::class.java
        ).setParameter("companyId", companyId).resultList
        return queryResult
    }
}
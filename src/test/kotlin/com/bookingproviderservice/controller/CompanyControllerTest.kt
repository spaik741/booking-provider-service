package com.bookingproviderservice.controller

import com.bookingproviderservice.model.CompanyEntity
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.http.ProblemDetail
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlGroup
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import kotlin.test.assertEquals

@SqlGroup(
    Sql(scripts = arrayOf("classpath:insert-company.sql"), executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    Sql(scripts = arrayOf("classpath:clear-company.sql"), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@SpringBootTest
class CompanyControllerTest : BaseMvcTest() {

    private val PATH = "/v1/provider/company"

    @Test
    fun registryCompany() {
        val createRequest = javaClass.getResource("/json/companyRegistrationRequest.json")!!
            .readText(Charsets.UTF_8)
        val result = mockMvc.perform(
            post("$PATH/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createRequest)
        )
            .andReturn()
        assertEquals(201, result.response.status)
        val queryResult =
            entityManager.createQuery("select c from CompanyEntity c", CompanyEntity::class.java).resultList
        assertEquals(2, queryResult.size)
        assertEquals("Ноготочки у Иришки", queryResult.first { it.id == 1L }.name)
    }

    @Test
    fun registryCompanyNotValidRequest() {
        val createRequest = javaClass.getResource("/json/companyRegistrationRequestNotValidPhone.json")!!
            .readText(Charsets.UTF_8)
        val result = mockMvc.perform(
            post("$PATH/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createRequest)
        )

        val problemDetail =
            objectMapper.readValue(result.andReturn().response.contentAsByteArray, ProblemDetail::class.java)
        assertEquals("Invalid request content.", problemDetail.detail)
        assertEquals("/v1/provider/company/registration", problemDetail.instance.toString())
        result.andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun registryCompanyExistCompany() {
        val createRequest = javaClass.getResource("/json/companyRegistrationRequestExistCompany.json")!!
            .readText(Charsets.UTF_8)
        mockMvc.perform(
            post("$PATH/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createRequest)
        ).andExpect(MockMvcResultMatchers.status().isConflict)
    }

    @Test
    fun findCompany() {
        val response = javaClass.getResource("/json/companyDto.json")!!
            .readText(Charsets.UTF_8)
        val result = mockMvc.perform(
            get("$PATH/101")
        ).andReturn()
        assertEquals(response, result.response.contentAsString)
        assertEquals(200, result.response.status)
    }

    @Test
    fun findCompanyNotFound() {
        val result = mockMvc.perform(
            get("$PATH/666")
        ).andReturn()
        assertEquals(404, result.response.status)
    }
}
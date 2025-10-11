package com.bookingproviderservice.controller

import com.bookingproviderservice.model.CompanyEntity
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
}
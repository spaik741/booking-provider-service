package com.bookingproviderservice.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.oauth2ResourceServer { oauth2 -> oauth2.jwt(Customizer.withDefaults()) }

        http.securityMatcher { request ->
            val path = request.requestURI
            !path.startsWith("/v1/provider/schedule/book")
        }

        http.authorizeHttpRequests { c ->
            c.requestMatchers("/actuator/**").permitAll()
                .requestMatchers("/v1/provider/schedule/book**").permitAll()
                .requestMatchers("/v1/provider/schedule/registration").hasRole("COMPANY")
                .requestMatchers("/v1/provider/company/**").hasRole("COMPANY")
                .anyRequest().authenticated()
        }

        return http.build()
    }

    @Bean
    fun jwtAuthenticationConverter(): JwtAuthenticationConverter {
        val converter = JwtAuthenticationConverter()
        val jwtGrantedAuthoritiesConverter = JwtGrantedAuthoritiesConverter()
        converter.setPrincipalClaimName("preferred_username")
        converter.setJwtGrantedAuthoritiesConverter { jwt: Jwt ->
            val authorities = jwtGrantedAuthoritiesConverter.convert(jwt).orEmpty()
            val roles = jwt.getClaimAsMap("realm_access")["roles"] as MutableCollection<String>?
            val rolesAuth =
                roles?.filter { role -> role.startsWith("ROLE_") }?.map { SimpleGrantedAuthority(it) }.orEmpty()
            authorities.plus(rolesAuth)
        }
        return converter
    }
}
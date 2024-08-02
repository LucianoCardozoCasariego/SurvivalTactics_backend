package com.lcc.SurvivalTactics.configuration

import com.lcc.SurvivalTactics.models.RolesModel
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
class SecurityConfiguration(private val authenticationProvider: AuthenticationProvider) {

    @Bean
    fun securityFilterChain(httpSecurity: HttpSecurity, jwtAuthenticationFilter: JwtAuthenticationFilter): DefaultSecurityFilterChain {
        val routes = SecurityRoutes()
        return httpSecurity
            .csrf { conf -> conf.disable() }
            .authorizeHttpRequests { auth ->
                auth.requestMatchers(*routes.publicRoutes.toTypedArray()).permitAll()
                auth.requestMatchers(*routes.adminRoutes.toTypedArray()).hasRole(RolesModel.ADMIN.name)
                auth.anyRequest().authenticated()
            }
            .sessionManagement { session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()
    }
}



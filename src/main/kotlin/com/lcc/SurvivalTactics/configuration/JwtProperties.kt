package com.lcc.SurvivalTactics.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("jwt")
data class JwtProperties(
    val secret: String,
    val expiration: Long
)
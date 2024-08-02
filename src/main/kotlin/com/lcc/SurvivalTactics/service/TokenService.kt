package com.lcc.SurvivalTactics.service

import com.lcc.SurvivalTactics.configuration.JwtProperties
import com.lcc.SurvivalTactics.exceptions.TokenError
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*

@Service
class TokenService(jwpProperties: JwtProperties) {

    private val secretKey = Keys.hmacShaKeyFor(
        jwpProperties.secret.toByteArray()
    )

    fun generate(
        email: String,
        expirationDate: Date,
        aditionalClaims: Map<String, Any> = emptyMap()
    ): String {
        return Jwts.builder().claims()
            .subject(email)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(expirationDate)
            .add(aditionalClaims)
            .and()
            .signWith(secretKey)
            .compact()
    }

    fun extractEmail(token: String): String? {
        return getAllClaims(token).subject
    }

    fun isExpired(token: String): Boolean {
        return getAllClaims(token).expiration.before(Date(System.currentTimeMillis()))
    }

    fun isValid(token: String, userDetails: UserDetails): Boolean {
        val email = extractEmail(token)
        return userDetails.username == email && !isExpired(token)
    }

    fun getAllClaims(token: String): Claims {
        try {
            val parser = Jwts.parser()
                .verifyWith(secretKey)
                .build()

            return parser.parseSignedClaims(token).payload
        } catch (err: RuntimeException) {
            throw TokenError("error al leer el token")
        }
    }

    fun isTokenMalformed(token: String): Boolean {
        try {
            val parser = Jwts.parser()
                .verifyWith(secretKey)
                .build()
            parser.parseSignedClaims(token).payload
            return false
        } catch (err: RuntimeException) {
            return true
        }
    }
}
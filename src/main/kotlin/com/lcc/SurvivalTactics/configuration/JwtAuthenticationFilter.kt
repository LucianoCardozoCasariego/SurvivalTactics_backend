package com.lcc.SurvivalTactics.configuration

import com.lcc.SurvivalTactics.service.TokenService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.WebUtils

@Component
class JwtAuthenticationFilter(
    private val userDetailsService: UserDetailsService,
    private val tokenService: TokenService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val reqPath = request.requestURI
        val routes = SecurityRoutes()
        if (routes.publicRoutes.contains(reqPath)) {
            filterChain.doFilter(request, response)
            return
        }

        val cookieToken = WebUtils.getCookie(request, "JWT_TOKEN")

        if (cookieToken == null || tokenService.isTokenMalformed(cookieToken.value)) {
            filterChain.doFilter(request, response)
            return
        }

        val jwtToken = cookieToken.value
        val username = tokenService.extractEmail(jwtToken)

        if (username != null && SecurityContextHolder.getContext().authentication == null) {
            val foundUser = userDetailsService.loadUserByUsername(username)

            if (tokenService.isValid(jwtToken, foundUser)) {
                updateContext(foundUser, request)
                request.setAttribute("userLoged", foundUser)
            }

            filterChain.doFilter(request, response)
        }
    }

    private fun updateContext(foundUser: UserDetails, request: HttpServletRequest) {
        val authToken = UsernamePasswordAuthenticationToken(foundUser, null, foundUser.authorities)
        authToken.details = WebAuthenticationDetailsSource().buildDetails(request)

        SecurityContextHolder.getContext().authentication = authToken
    }
}
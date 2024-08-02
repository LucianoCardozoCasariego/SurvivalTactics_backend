package com.lcc.SurvivalTactics.controllers

import com.lcc.SurvivalTactics.controllers.dtos.request.LoginUserDto
import com.lcc.SurvivalTactics.controllers.dtos.request.RegisterUserDto
import com.lcc.SurvivalTactics.service.userServiceImpl
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("user")
class UserController(private val userServiceImpl: userServiceImpl) {

    @GetMapping("/hello")
    fun hello(): String {
        return "Esta ruta no esta protegida"
    }

    @GetMapping("/admin")
    fun admin(): String {
        return "Esta ruta es para damins"
    }

    @GetMapping("/hello-secure")
    fun hello_secure(request: HttpServletRequest): String {
        val userLogged = request.getAttribute("userLoged") as? UserDetails
        return "Esta ruta SI esta protegida" + userLogged?.username
    }

    @PostMapping("/register")
    fun registerUser(@Valid @RequestBody body: RegisterUserDto, response: HttpServletResponse): ResponseEntity<*> {
        val userToken = this.userServiceImpl.createUser(body.toModel())
        this.generateCookie(userToken!!.token, response)

        return ResponseEntity(userToken.user, HttpStatus.OK)
    }

    @PostMapping("/login")
    fun loginUser(@Valid @RequestBody body: LoginUserDto, response: HttpServletResponse): ResponseEntity<*> {
        val userToken = this.userServiceImpl.loginUser(body)
        this.generateCookie(userToken!!.token, response)

        return ResponseEntity(userToken.user, HttpStatus.OK)
    }

    private fun generateCookie(token: String, response: HttpServletResponse) {
        val cookie = Cookie("JWT_TOKEN", token)
        cookie.isHttpOnly = true
        cookie.path = "/"
        cookie.maxAge = 7 * 24 * 60 * 60

        response.addCookie(cookie)
    }
}
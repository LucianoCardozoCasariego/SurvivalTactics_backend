package com.lcc.SurvivalTactics.service

import com.lcc.SurvivalTactics.configuration.JwtProperties
import com.lcc.SurvivalTactics.controllers.dtos.request.LoginUserDto
import com.lcc.SurvivalTactics.controllers.dtos.response.UserDto
import com.lcc.SurvivalTactics.controllers.dtos.response.UserWitdTokenDto
import com.lcc.SurvivalTactics.exceptions.EmailTakenError
import com.lcc.SurvivalTactics.exceptions.NotFoundError
import com.lcc.SurvivalTactics.models.UserModel
import com.lcc.SurvivalTactics.repositories.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class userServiceImpl(
    private val userRepository: UserRepository,
    private var passEncoder: PasswordEncoder,
    private var authManager: AuthenticationManager,
    private var userDetailsService: CustpmUserDetailsService,
    private var tokenService: TokenService,
    private var jwtProperties: JwtProperties,
) {
    fun createUser(user: UserModel): UserWitdTokenDto? {
        var userFounded = this.userRepository.findByEmail(user.email)

        if (userFounded == null) {
            user.password = this.passEncoder.encode(user.password)
            val savedUser = this.userRepository.save(user)

            return UserWitdTokenDto(UserDto.fromModel(savedUser), createToken(savedUser.email))
        } else {
            throw EmailTakenError("Email already taken")
        }
    }

    fun loginUser(user: LoginUserDto): UserWitdTokenDto? {
        var userFounded = this.userRepository.findByEmail(user.email)

        if (userFounded != null) {
            val token = this.authentication(user.email, user.password)
            return UserWitdTokenDto(UserDto.fromModel(userFounded), token)
        } else {
            throw NotFoundError("El usuario con email ${user.email} no existe")
        }
    }


    fun findByID(id: Long): UserModel? {
        return this.userRepository.findByIdOrNull(id)
    }

    fun findAll(): List<UserModel> {
        return this.userRepository.findAll().toList()
    }


    private fun authentication(username: String, clave: String): String {
        authManager.authenticate(UsernamePasswordAuthenticationToken(username, clave))

        val user = userDetailsService.loadUserByUsername(username)
        return createToken(user.username)
    }

    private fun createToken(email: String): String {
        val accessToken = tokenService.generate(
            email = email,
            expirationDate = Date(System.currentTimeMillis() + jwtProperties.expiration)
        )
        return accessToken
    }
}
package com.lcc.SurvivalTactics.service

import com.lcc.SurvivalTactics.exceptions.NotFoundError
import com.lcc.SurvivalTactics.models.UserModel
import com.lcc.SurvivalTactics.repositories.UserRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class CustpmUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val userModel: UserModel = this.userRepository.findByEmail(username)
            ?: throw NotFoundError("El usuario con email ${username} no existe")

        var roles: Collection<GrantedAuthority> = setOf(SimpleGrantedAuthority("ROLE_" + userModel.roles.name))

        return User(
            userModel.email,
            userModel.password,
            true,
            true,
            true,
            true,
            roles
        )
    }
}
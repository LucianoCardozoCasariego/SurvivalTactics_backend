package com.lcc.SurvivalTactics.controllers.dtos.request

import com.lcc.SurvivalTactics.models.RolesModel
import com.lcc.SurvivalTactics.models.UserModel
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank


class RegisterUserDto {
    @Email
    @NotBlank
    lateinit var email: String

    @NotBlank
    lateinit var username: String

    @NotBlank
    lateinit var password: String

    fun toModel(): UserModel {
        val user = UserModel()
        user.email = this.email
        user.password = this.password
        user.username = this.username
        user.roles = RolesModel.USER
        return user
    }
}

class LoginUserDto{
    @Email
    @NotBlank
    lateinit var email: String

    @NotBlank
    lateinit var password: String
}
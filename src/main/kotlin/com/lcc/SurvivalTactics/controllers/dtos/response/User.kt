package com.lcc.SurvivalTactics.controllers.dtos.response

import com.lcc.SurvivalTactics.models.RolesEnum
import com.lcc.SurvivalTactics.models.UserModel

data class UserDto(
    var id: Long?,
    var email: String,
    var username: String?,
    var roles: RolesEnum,
) {

    companion object {
        fun fromModel(user: UserModel): UserDto {
            return UserDto(user.user_id, user.email, user.username, user.roles)
        }
    }
}

data class UserWitdTokenDto(
    var user: UserDto,
    var token: String,
) {
}
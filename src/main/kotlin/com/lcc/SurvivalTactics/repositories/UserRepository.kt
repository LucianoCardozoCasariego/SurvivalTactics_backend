package com.lcc.SurvivalTactics.repositories

import com.lcc.SurvivalTactics.models.UserModel
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: CrudRepository<UserModel, Long> {
    fun findByEmail(email: String): UserModel?
}
package com.lcc.SurvivalTactics.models

import jakarta.persistence.*

@Entity
@Table(name = "user")
class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val user_id: Long? = null
    lateinit var email: String
    lateinit var username: String
    lateinit var password: String

    @Enumerated(value = EnumType.STRING)
    lateinit var roles: RolesModel
}
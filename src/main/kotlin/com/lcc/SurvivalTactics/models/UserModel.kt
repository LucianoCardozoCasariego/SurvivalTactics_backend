package com.lcc.SurvivalTactics.models

import jakarta.persistence.*

@Entity
@Table(name = "user")
class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val user_id: Long? = null
    @Column(unique = true)
    lateinit var email: String
    @Column(unique = true)
    lateinit var username: String
    lateinit var password: String

    @Enumerated(value = EnumType.STRING)
    lateinit var roles: RolesEnum

    @OneToMany(mappedBy = "owner", cascade = [CascadeType.PERSIST], fetch = FetchType.LAZY)
    var cities: MutableList<CityModel> = mutableListOf()
}
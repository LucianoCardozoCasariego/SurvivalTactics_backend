package com.lcc.SurvivalTactics.models

import com.lcc.SurvivalTactics.models.buildings._Building
import jakarta.persistence.*

@Entity
@Table(name = "city")
class CityModel(var name: String) {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var city_id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var owner: UserModel? = null

    @OneToMany(mappedBy = "city", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var buildings: MutableList<_Building> = mutableListOf()
}
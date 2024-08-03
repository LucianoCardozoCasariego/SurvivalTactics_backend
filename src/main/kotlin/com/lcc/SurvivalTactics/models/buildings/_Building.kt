package com.lcc.SurvivalTactics.models.buildings

import com.lcc.SurvivalTactics.models.CityModel
import jakarta.persistence.*

@Entity
@Table(name = "building")
@Inheritance(strategy = InheritanceType.JOINED)
abstract class _Building(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val building_id: Long? = null,

    open val level_ComunATodas: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    var city: CityModel? = null
) {
    abstract fun increaseLevel()
    abstract fun reduceLevel()
}
package com.lcc.SurvivalTactics.models.buildings

import jakarta.persistence.Entity

@Entity
class Center(
    override val level_ComunATodas: Int,
    val specificFieldA: String = "Ejemplo"
): _Building(null,level_ComunATodas) {

    override fun increaseLevel() {
        TODO("Not yet implemented")
    }

    override fun reduceLevel() {
        TODO("Not yet implemented")
    }
}
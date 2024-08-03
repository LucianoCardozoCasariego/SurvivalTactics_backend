package com.lcc.SurvivalTactics.repositories

import com.lcc.SurvivalTactics.models.CityModel
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CityRepository : CrudRepository<CityModel, Long> {
}
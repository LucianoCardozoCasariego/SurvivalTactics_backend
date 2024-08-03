package com.lcc.SurvivalTactics.repositories

import com.lcc.SurvivalTactics.models.buildings.Center
import com.lcc.SurvivalTactics.models.buildings._Building
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

//@Repository
//interface BuildingsRepository: CrudRepository<_Building, Long> {
//}

@Repository
interface CenterRepository: CrudRepository<Center, Long> {
}
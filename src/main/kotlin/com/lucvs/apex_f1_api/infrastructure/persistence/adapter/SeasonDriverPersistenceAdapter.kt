package com.lucvs.apex_f1_api.infrastructure.persistence.adapter

import com.lucvs.apex_f1_api.application.port.out.LoadSeasonDriverPort
import com.lucvs.apex_f1_api.application.port.out.SaveSeasonDriverPort
import com.lucvs.apex_f1_api.domain.model.SeasonDriver
import com.lucvs.apex_f1_api.infrastructure.persistence.entity.key.SeasonDriverId
import com.lucvs.apex_f1_api.infrastructure.persistence.mapper.SeasonDriverMapper
import com.lucvs.apex_f1_api.infrastructure.persistence.repository.SeasonDriverRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class SeasonDriverPersistenceAdapter(
    private val seasonDriverRepository: SeasonDriverRepository,
    private val seasonDriverMapper: SeasonDriverMapper
) : SaveSeasonDriverPort, LoadSeasonDriverPort {

    @Transactional
    override fun save(seasonDrivers: List<SeasonDriver>) {
        val entitiesToSave = seasonDrivers.mapNotNull { domain ->
            val id = SeasonDriverId(year = domain.year, driverId = domain.driverId)

            if (!seasonDriverRepository.existsById(id)) {
                seasonDriverMapper.toEntity(domain)
            } else {
                null
            }
        }

        if (entitiesToSave.isNotEmpty()) {
            seasonDriverRepository.saveAll(entitiesToSave)
            println("[+] Saved ${entitiesToSave.size} new season_drivers to RDB.")
        }
    }

    @Transactional
    override fun loadSeasonDrivers(year: Int): List<SeasonDriver> {
        return seasonDriverRepository.findAllByYear(year)
            .map { seasonDriverMapper.toDomain(it) }
    }

    @Transactional
    override fun loadByDriverId(driverId: String): List<SeasonDriver> {
        return seasonDriverRepository.findAllByDriverId(driverId)
            .map { seasonDriverMapper.toDomain(it) }
    }
}
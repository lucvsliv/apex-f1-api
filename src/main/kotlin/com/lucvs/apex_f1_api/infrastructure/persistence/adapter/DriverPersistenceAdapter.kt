package com.lucvs.apex_f1_api.infrastructure.persistence.adapter

import com.lucvs.apex_f1_api.application.port.out.LoadDriverPort
import com.lucvs.apex_f1_api.domain.model.Driver
import com.lucvs.apex_f1_api.infrastructure.persistence.mapper.DriverMapper
import com.lucvs.apex_f1_api.infrastructure.persistence.repository.DriverRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class DriverPersistenceAdapter(
    private val driverRepository: DriverRepository,
    private val driverMapper: DriverMapper
) : LoadDriverPort {

    @Transactional(readOnly = true)
    override fun loadDrivers(): List<Driver> {
        return driverRepository.findAll()
            .map { driverMapper.toDomain(it) }
    }

    @Transactional(readOnly = true)
    override fun loadAllByIds(ids: List<String>): List<Driver> {
        return driverRepository.findAllById(ids)
            .map { driverMapper.toDomain(it) }
    }
}
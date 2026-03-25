package com.lucvs.apex_f1_api.infrastructure.persistence.adapter

import com.lucvs.apex_f1_api.application.port.out.LoadEntrantPort
import com.lucvs.apex_f1_api.domain.model.Entrant
import com.lucvs.apex_f1_api.infrastructure.persistence.mapper.EntrantMapper
import com.lucvs.apex_f1_api.infrastructure.persistence.repository.EntrantRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class EntrantPersistenceAdapter(
    private val entrantRepository: EntrantRepository,
    private val entrantMapper: EntrantMapper
) : LoadEntrantPort {

    @Transactional(readOnly = true)
    override fun loadAllByIds(ids: List<String>): List<Entrant> {
        return entrantRepository.findAllById(ids)
            .map { entrantMapper.toDomain(it) }
    }
}
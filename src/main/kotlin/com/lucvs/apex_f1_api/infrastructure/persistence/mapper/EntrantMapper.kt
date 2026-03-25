package com.lucvs.apex_f1_api.infrastructure.persistence.mapper

import com.lucvs.apex_f1_api.domain.model.Entrant
import com.lucvs.apex_f1_api.infrastructure.persistence.entity.EntrantEntity
import org.springframework.stereotype.Component

@Component
class EntrantMapper {
    fun toDomain(entity: EntrantEntity) = Entrant(entity.id, entity.name)
    fun toEntity(domain: Entrant) = EntrantEntity(domain.id, domain.name)
}
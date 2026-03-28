package com.lucvs.apex_f1_api.infrastructure.api.controller.admin

import com.lucvs.apex_f1_api.application.port.`in`.IngestF1DocumentUseCase
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/admin/docs")
class AdminDocumentController(
    private val ingestF1DocumentUseCase: IngestF1DocumentUseCase,
) {

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/ingest")
    fun ingestF1Regulations(): ResponseEntity<Map<String, String>> {
        ingestF1DocumentUseCase.ingestRegulations()

        return ResponseEntity.ok().build()
    }
}
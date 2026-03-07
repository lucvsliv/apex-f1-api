package com.lucvs.apex_f1_api.infrastructure.api

import com.lucvs.apex_f1_api.application.port.`in`.CreateSubscriptionUseCase
import com.lucvs.apex_f1_api.infrastructure.api.dto.SubscribeRequest
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/subscriptions")
class SubscriptionController(
    private val createSubscriptionUseCase: CreateSubscriptionUseCase
) {

    @PostMapping
    fun subscribe(
        @AuthenticationPrincipal principal: Any,
        @RequestBody request: SubscribeRequest
    ): ResponseEntity<Void> {
        val userId = principal.toString().toLong()

        createSubscriptionUseCase.subscribe(
            userId = userId,
            authKey = request.authKey,
            customerKey = request.customerKey,
            targetTier = request.targetTier
        )

        return ResponseEntity.ok().build()
    }
}
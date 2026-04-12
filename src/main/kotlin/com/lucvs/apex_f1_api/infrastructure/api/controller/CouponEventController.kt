package com.lucvs.apex_f1_api.infrastructure.api.controller

import com.lucvs.apex_f1_api.application.port.`in`.GetQueueStatusUseCase
import com.lucvs.apex_f1_api.application.port.`in`.JoinCouponEventUseCase
import com.lucvs.apex_f1_api.infrastructure.api.dto.QueueStatusResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/events/coupons")
class CouponEventController(
    private val joinCouponEventUseCase: JoinCouponEventUseCase,
    private val getQueueStatusUseCase: GetQueueStatusUseCase
) {

    @PostMapping("/{couponPolicyId}/queue")
    fun joinQueue(
        @PathVariable couponPolicyId: Long,
        @AuthenticationPrincipal userId: Long
    ): ResponseEntity<QueueStatusResponse> {
        val status = joinCouponEventUseCase.joinQueue(couponPolicyId, userId)
        return ResponseEntity.ok(QueueStatusResponse.from(status))
    }

    @GetMapping("/{couponPolicyId}/queue/status")
    fun getQueueStatus(
        @PathVariable couponPolicyId: Long,
        @AuthenticationPrincipal userId: Long
    ): ResponseEntity<QueueStatusResponse> {
        val status = getQueueStatusUseCase.getStatus(couponPolicyId, userId)
        return ResponseEntity.ok(QueueStatusResponse.from(status))
    }
}
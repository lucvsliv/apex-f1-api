package com.lucvs.apex_f1_api.infrastructure.client.toss

import com.lucvs.apex_f1_api.application.port.out.RequestBillingKeyPort
import com.lucvs.apex_f1_api.infrastructure.client.toss.dto.TossBillingKeyResponse
import java.util.Base64
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

@Component
class TossPaymentsAdapter(
        @Value("\${toss.payments.secret-key}") private val secretKey: String,
        private val restClient: RestClient
) : RequestBillingKeyPort {

    override fun issueBillingKey(authKey: String, customerKey: String): String {
        // 토스 페이먼츠 인증 헤더 새성
        val encodedAuth = Base64.getEncoder().encodeToString("$secretKey:".toByteArray())

        val requestBody = mapOf("authKey" to authKey, "customerKey" to customerKey)

        // 토스 페이먼츠 API 호출
        val response =
                restClient
                        .post()
                        .uri("https://api.tosspayments.com/v1/billing/authorizations/issue")
                        .header(HttpHeaders.AUTHORIZATION, "Basic $encodedAuth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(requestBody)
                        .retrieve()
                        .body(TossBillingKeyResponse::class.java)
                        ?: throw IllegalStateException("토스 페이먼츠 응답이 비어있습니다.")

        return response.billingKey
    }
}

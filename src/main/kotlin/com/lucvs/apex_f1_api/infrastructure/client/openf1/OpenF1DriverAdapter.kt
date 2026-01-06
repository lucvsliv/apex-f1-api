package com.lucvs.apex_f1_api.infrastructure.client.openf1

import com.lucvs.apex_f1_api.application.port.out.LoadDriverPort
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

@Component
class OpenF1DriverAdapter(
    private val restClient: RestClient,
    @Value("\${openf1.api.url:https://api.openf1.org/v1}") private val apiUrl: String
) : LoadDriverPort {


}
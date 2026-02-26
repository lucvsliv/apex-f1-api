package com.lucvs.apex_f1_api.infrastructure.security

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "jwt")
class JwtProperties {
    lateinit var secret: String
    var accessTokenExpiration: Long = 0
    var refreshTokenExpiration: Long = 0
}
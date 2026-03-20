package com.lucvs.apex_f1_api.infrastructure.config

import com.lucvs.apex_f1_api.infrastructure.api.interceptor.AiRateLimitInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(
    private val aiRateLimitInterceptor: AiRateLimitInterceptor
) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(aiRateLimitInterceptor)
            .addPathPatterns("/api/v1/agent/**")
    }
}
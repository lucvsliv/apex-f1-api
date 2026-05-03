package com.lucvs.apex_f1_api.application.port.`in`

import reactor.core.publisher.Flux

interface ChatWithAgentUseCase {
    fun chat(command: ChatCommand): Flux<String>
}
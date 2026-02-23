package com.lucvs.apex_f1_api.application.port.`in`

interface ChatWithAgentUseCase {
    /**
     * @param userId 요청한 사용자의 식별자 (추후 개인화된 히스토리 조회를 위해 필요)
     * @param message 사용자가 입력한 질문
     * @return AI Agent가 생성한 응답 텍스트
     */
    fun chat(userId: Long, message: String): String
}
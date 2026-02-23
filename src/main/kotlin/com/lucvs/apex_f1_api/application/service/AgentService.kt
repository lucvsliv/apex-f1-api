package com.lucvs.apex_f1_api.application.service

import com.lucvs.apex_f1_api.application.port.`in`.ChatWithAgentUseCase
import com.lucvs.apex_f1_api.application.port.out.GenerateLlmResponsePort
import com.lucvs.apex_f1_api.application.port.out.SearchVectorPort
import org.springframework.stereotype.Service

@Service
class AgentService(
    private val searchVectorPort: SearchVectorPort,
    private val generateLlmResponsePort: GenerateLlmResponsePort
) : ChatWithAgentUseCase {

    override fun chat(userId: Long, message: String): String {
        // 1. Vector DB 검색 (현재 드라이버 정보 위주)
        val contexts = searchVectorPort.searchSimilarContext(message)
        val contextString = contexts.joinToString("\n")

        // 2. 검색된 컨텍스트와 시스템 지시사항을 합쳐 프롬프트 생성
        val prompt = buildPrompt(message, contextString)

        // 3. LLM 호출 및 응답 반환
        return generateLlmResponsePort.generateResponse(prompt)
    }

    private fun buildPrompt(userMessage: String, contextString: String): String {
        return """
            당신은 Formula 1 전문가이자 'Apex Assistant'입니다.
            현재 시스템의 데이터베이스에는 주로 'F1 드라이버'와 관련된 정보가 구축되어 있습니다.
            
            사용자의 질문에 답변할 때, 반드시 아래 제공된 <검색된 F1 정보>를 기반으로 답변해 주세요.
            
            [중요 규칙]
            1. <검색된 F1 정보>에 사용자의 질문에 답할 수 있는 내용이 없다면, 절대 지어내지 마세요.
            2. 정보가 부족할 경우: "현재 Apex Assistant는 F1 드라이버 정보를 중심으로 학습되어 있습니다. 질문하신 정보는 곧 업데이트될 예정입니다."라고 답변하세요.
            3. 답변은 친절하고 전문적인 한국어로 작성하세요.

            <검색된 F1 정보>
            $contextString

            <사용자 질문>
            $userMessage
            
            <답변>
        """.trimIndent()
    }
}
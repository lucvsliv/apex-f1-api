package com.lucvs.apex_f1_api.infrastructure.ai.tool

import org.slf4j.LoggerFactory
import org.springframework.ai.vectorstore.SearchRequest
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Description
import java.util.function.Function

@Configuration
class F1RegulationRagToolProvider(
    private val vectorStore: VectorStore
) {

    private val log = LoggerFactory.getLogger(javaClass)

    data class RagRequest(val query: String)
    data class RagResponse(val documents: String)

    @Bean
    @Description(
        "Searches F1 racing regulations, penalty rules, safety car (VSC/SC) procedures, technical guidelines, and historical stories. " +
                "Input must be a search keyword or natural language question to find relevant text from the Vector DB."
    )
    fun searchF1RegulationTool(): Function<RagRequest, RagResponse> {
        return Function { request: RagRequest ->
            val query = request.query.trim()
            log.info("[*] Apex-AI searching Vector DB for: {}", query)

            try {
                // 1. Vector DB에서 유사도 높은 문서 청크 상위 3개 검색
                val searchResults = vectorStore.similaritySearch(
                    SearchRequest.builder()
                        .query(query)
                        .topK(3)
                        .build()
                )

                // 2. 검색 결과가 없을 경우
                if (searchResults.isEmpty()) {
                    log.info("[*] No relevant F1 regulation documents found for query: {}", query)
                    return@Function RagResponse("No relevant F1 regulation documents found for the given query.")
                }

                // 3. 메타데이터와 내용을 합체
                val combinedDocs = searchResults.joinToString("\n\n---\n\n") { doc ->
                    val source = doc.metadata["source"] ?: "Unknown Source"
                    val category = doc.metadata["category"] ?: "Uncategorized"

                    "[Source: $source ($category)]\n${doc.text}"
                }

                log.info("[*] Rag Search executed successfully. Returning {} document chunks.", searchResults.size)
                RagResponse(combinedDocs)
            } catch (e: Exception) {
                log.error("[!] Vector DB Search Error: {}", e.message)
                RagResponse("Vector DB Search Error: ${e.message}. Please try again.")
            }
        }
    }
}
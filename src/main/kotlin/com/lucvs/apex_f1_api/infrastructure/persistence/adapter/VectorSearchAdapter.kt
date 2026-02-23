package com.lucvs.apex_f1_api.infrastructure.persistence.adapter

import com.lucvs.apex_f1_api.application.port.out.SearchVectorPort
import org.springframework.ai.vectorstore.SearchRequest // 👈 반드시 이 패키지인지 확인해 주세요!
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.stereotype.Component

@Component
class VectorSearchAdapter(
    private val vectorStore: VectorStore
) : SearchVectorPort {

    override fun searchSimilarContext(query: String, topK: Int): List<String> {
        // 최신 Spring AI 문법 (Builder 패턴) 적용
        val searchRequest = SearchRequest.builder()
            .query(query)
            .topK(topK)
            .build()

        val documents = vectorStore.similaritySearch(searchRequest)

        return documents.map { it.formattedContent }
    }
}
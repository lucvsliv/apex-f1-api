package com.lucvs.apex_f1_api.application.port.out

interface SearchVectorPort {
    fun searchSimilarContext(query: String, topK: Int = 3): List<String>
}
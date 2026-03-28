package com.lucvs.apex_f1_api.application.service.ai

import com.lucvs.apex_f1_api.application.port.`in`.IngestF1DocumentUseCase
import org.slf4j.LoggerFactory
import org.springframework.ai.reader.pdf.PagePdfDocumentReader
import org.springframework.ai.transformer.splitter.TokenTextSplitter
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service

@Service
class F1DocumentIngestionService(
    private val vectorStore: VectorStore,
    @Value("classpath:/docs/fia_2026_f1_regulations_general.pdf") private val generalPdf: Resource,
    @Value("classpath:/docs/fia_2026_f1_regulations_sporting.pdf") private val sportingPdf: Resource,
    @Value("classpath:/docs/fia_2026_f1_regulations_technical.pdf") private val technicalPdf: Resource
) : IngestF1DocumentUseCase {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun ingestRegulations() {
        log.info("[*] F1 규정 PDF 파싱 및 Vector Store 적재 시작...")

        val targetDocuments = listOf(
            Pair(generalPdf, "General Provisions"),
            Pair(sportingPdf, "Sporting Regulations"),
            Pair(technicalPdf, "Technical Regulations")
        )

        val textSplitter = TokenTextSplitter()

        for ((pdfResource, categoryName) in targetDocuments) {
            if (!pdfResource.exists()) {
                log.warn("[!] 해당 PDF 문서를 찾을 수 없어 건너뜁니다.: ${pdfResource.filename}")
                continue
            }

            log.info("[*] [${categoryName}] 문서 읽는 중... (파일명: ${pdfResource.filename})")

            // 1. PDF 읽기 및 페이지 단위 Document 객체 생성
            val pdfReader = PagePdfDocumentReader(pdfResource)
            val documents = pdfReader.get()

            // 2. 각 문서 청크에 대하여 메타데이터 생성 - RAG 출처 표시 가능
            documents.forEach { doc ->
                doc.metadata["category"] = categoryName
                doc.metadata["source"] = pdfResource.filename ?: "unknown_pdf"
            }

            // 3. 청크 분할
            val splitDocuments = textSplitter.apply(documents)
            log.info("[+] [${categoryName}] ${splitDocuments.size}개의 청크로 분할되었습니다.")

            // 4. Vector DB에 저장
            log.info("[*] Vector DB에 저장하는 중...")
            vectorStore.add(splitDocuments)
        }

        log.info("[*] F1 규정 PDF에 대한 Vector Store 적재가 완료되었습니다.")
    }
}
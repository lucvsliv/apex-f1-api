# GEMINI.md

## Project Overview: Apex-F1
Apex-F1은 F1(Formula 1) 데이터를 실시간으로 동기화하고, LLM 기반의 AI 에이전트를 통해 사용자의 자연어 질문에 답하며, 구독 기반 서비스를 제공하는 백엔드 시스템입니다.

### Core Tech Stack
* Language: Kotlin
* Framework: Spring Boot (3.x)
* Architecture: Hexagonal Architecture (Ports and Adapters)
* AI: Spring AI, Vector Search, RAG (Retrieval-Augmented Generation)
* Data: Kafka (Events), Redis (Queue/Rate Limit), JPA (Persistence), SSE (Notification)
* Payment: Toss Payments

---

## Project Structure and Domain Logic

### 1. Architecture Principles
이 프로젝트는 계층 간 결합도를 낮추기 위해 헥사고날 아키텍처를 엄격히 따릅니다. 모든 코드는 기술 중심이 아닌 도메인 중심으로 구성됩니다.
* application/port/in: 외부에서 시스템으로 진입하는 통로 (UseCase 인터페이스)
* application/port/out: 시스템에서 외부(DB, API, AI)로 나가는 통로 (Port 인터페이스)
* application/service: UseCase를 구현하는 순수 비즈니스 로직 계층
* domain/model: 핵심 비즈니스 엔티티 및 도메인 객체 (Framework-agnostic)
* infrastructure: 외부 기술(Redis, Kafka, JPA, Spring AI)의 구체적인 구현체 (Adapter)

### 2. Key Modules and Responsibilities
* AI Agent (service/ai, infrastructure/ai)
    * ApexAiAgentService: LLM을 사용하여 사용자 질의에 응답.
    * F1RegulationRagToolProvider: resources/docs 내 FIA 규정 PDF를 기반으로 RAG 수행.
    * F1SqlToolProvider: DB 스키마 컨텍스트를 활용하여 실시간 통계 쿼리 실행.
* F1 Data Pipeline (service/etl, scheduler)
    * F1GlobalSyncService 및 EtlProcessor: 외부 API로부터 레이스 데이터를 수집 및 가공.
* Subscription and Payment (service/SubscriptionService, client/toss)
    * 정기 구독 모델(Subscription) 관리 및 토스페이먼츠 빌링 키 연동.

---

## Documentation & Task Conventions

### 1. Jira Ticket Convention
새로운 기능 구현이나 버그 수정을 위한 Jira 티켓을 작성할 때는 다음 형식을 따르며, 제목 앞에 반드시 **[BE]** 머리말을 붙입니다. (예: `[BE] 사용자 정보 수정 API 구현`)

## 📝 Summary (요약)
- [요약 내용 1]
- [요약 내용 2]

## 🛠 Tasks (할 일)
* Task A
* Task B
* Task C

## 🏗 Technical Specs
- spec 1
- spec 2
- spec 3

## ✅ Acceptance Criteria
- criteria 1
- criteria 2
- criteria 3

## 🔌 Related Issue
- Jira: APEX-XXXX
- Epic: [Epic 이름]

### 2. Git PR Description Convention
코드 리뷰를 위한 PR 본문은 다음 형식을 준수하여 작성하며, PR 생성 시 반드시 다음 사항을 준수합니다.
* **Assignee**: `lucvsliv` (본인)으로 설정
* **Label**: 레포지토리에 존재하는 레이블 중 해당 작업에 적절한 것들을 모두 선택하여 설정 (예: `type/feat`, `area/api` 등)

## 📌 Title
[type]: [APEX-XXXX] This is description

## 📝 Summary (요약)
* [요약 내용 1]
* [요약 내용 2]

## 🛠 Key Changes (변경 사항)
* 변경 사항 A
* 변경 사항 B
* 변경 사항 C

## 🏗 Architecture & Design Decisions (설계 주안점)
* 결정 사항 A
* 결정 사항 B
* 결정 사항 C

## ✅ Test Plan (테스트 계획)
* 테스트 시나리오 A
* 테스트 시나리오 B

## 🔌 Related Issue
* Jira: APEX-XXXX

---

## Development Guidelines for Gemini

### 1. Implementation Flow
새로운 기능을 구현할 때는 반드시 다음 순서를 준수합니다.
1. domain/model: 비즈니스 규칙을 담은 도메인 모델 정의.
2. application/port: 입력(UseCase) 및 출력(Port) 인터페이스 정의.
3. application/service: 포트를 사용하여 로직 구현.
4. infrastructure: 포트의 구현체(Adapter) 및 외부 연동 코드 작성.
5. infrastructure/api: Controller 및 DTO 정의.

### 2. Coding Standards
* Type Safety: Kotlin의 Null-safety를 활용하고 Immutable 데이터를 지향합니다.
* Data Mapping: Entity와 Model을 분리하며 Mapper를 통해 데이터를 변환합니다.

### 3. Workflow Completion
기능 구현이 완료되면 위에서 정의한 **Jira 티켓 양식**이나 **PR 본문 양식**에 맞춰 작업 내용을 요약하여 제공해야 합니다.
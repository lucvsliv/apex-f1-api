package com.lucvs.apex_f1_api.application.service.ai

import org.springframework.stereotype.Service

@Service
class SchemaContextService {

    /**
     * Apex-AI의 System Prompt에 주입될 데이터베이스 스키마 및 규칙 명세서
     */
    fun getDatabaseSchemaContext(): String {
        return """
            # Database Schema for APEX-F1
            You are an expert SQL Data Analyst for Formula 1. 
            Write safe, Read-Only PostgreSQL queries based strictly on the schema below.

            ## 1. Core Entities (기본 정보 및 누적 스탯)

            - `driver` (선수 정보 및 역대 통산 기록)
              - `id` (PK, ex: 'max-verstappen')
              - `name`, `full_name`, `abbreviation` (ex: 'VER'), `permanent_number`, 
              - `total_championship_wins`, `total_race_wins`, `total_podiums`, `total_points`, `total_pole_positions`

            - `constructor` (팀 정보 및 역대 통산 기록)
              - `id` (PK, ex: 'red-bull')
              - `name`, `full_name`
              - `total_championship_wins`, `total_race_wins`, `total_points`

            - `race` (개별 그랑프리 경기)
              - `id` (PK, INT)
              - `year` (시즌 연도), `round` (라운드 번호)
              - `official_name` (경기명), `date` (경기일)

            ## 2. Season Standings (시즌별 챔피언십 순위)

            - `season_driver_standing` (시즌별 드라이버 순위)
              - `year`, `driver_id` (FK)
              - `position_number` (최종 순위, 1=우승)
              - `points` (시즌 총 포인트), `championship_won` (BOOLEAN)

            - `season_constructor_standing` (시즌별 팀 순위)
              - `year`, `constructor_id` (FK)
              - `position_number`, `points`, `championship_won`

            ## 3. Race Data Views (개별 그랑프리 결과)
            *CRITICAL:* Do not query the raw `race_data` table. Always use the Views below.

            - `race_result` (결승전 결과)
              - `race_id` (FK to race.id), `driver_id` (FK), `constructor_id` (FK)
              - `position_number` (최종 도착 순위, INT)
              - `grid_position_number` (출발 순서, INT)
              - `points` (획득 포인트), `laps` (완주 랩 수)
              - `reason_retired` (리타이어 사유)
              - `fastest_lap`, `driver_of_the_day` (BOOLEAN)

            - `qualifying_result` (예선전 결과)
              - `race_id`, `driver_id`, `constructor_id`
              - `position_number` (예선 최종 순위)
              - `q1`, `q2`, `q3` (각 세션 기록, VARCHAR)

            - `fastest_lap` (경기 중 최고속 랩타임)
              - `race_id`, `driver_id`, `lap` (기록한 랩 수), `time` (랩타임 문자열)

            ## SQL Writing Rules
            1. Only use `SELECT` statements. Never use DML/DDL.
            2. IMPORTANT: `id` values MUST use kebab-case with hyphens (e.g., 'charles-leclerc', 'lando-norris'). NEVER use underscores.
            3. For string matching (like names or id), always use `ILIKE` for case-insensitivity (e.g., `WHERE driver.name ILIKE '%max%'`).
            4. To get total career stats, query the `driver` table directly rather than summing up all historical `race_result` rows, as `driver` holds pre-calculated totals.
            5. Join relations: `race_result.driver_id = driver.id`, `race_result.race_id = race.id`.
        """.trimIndent()
    }
}
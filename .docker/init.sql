-- pgvector 확장 기능을 활성화
CREATE EXTENSION IF NOT EXISTS vector;
-- hstore 확장 기능 (메타데이터 저장용)
CREATE EXTENSION IF NOT EXISTS hstore;
-- uuid 생성을 위한 확장 기능
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
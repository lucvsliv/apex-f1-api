plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.4.12"
	id("io.spring.dependency-management") version "1.1.7"
	kotlin("plugin.jpa") version "1.9.25"
}

group = "com.lucvs"
version = "0.0.1-SNAPSHOT"
description = "Apex F1 API"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

extra["springAiVersion"] = "1.0.3"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")

	// spring-ai
	implementation("org.springframework.ai:spring-ai-advisors-vector-store")
	implementation("org.springframework.ai:spring-ai-starter-model-openai")
	implementation("org.springframework.ai:spring-ai-starter-vector-store-pgvector")
	implementation("org.springframework.ai:spring-ai-pdf-document-reader")

	runtimeOnly("org.postgresql:postgresql")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	developmentOnly("org.springframework.ai:spring-ai-spring-boot-docker-compose")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testRuntimeOnly("com.h2database:h2")
	testImplementation("org.mockito.kotlin:mockito-kotlin:5.2.1")

	// postgres - vector/json
	implementation("io.hypersistence:hypersistence-utils-hibernate-63:3.7.3")

	// spring security
	implementation("org.springframework.boot:spring-boot-starter-security")

	// jwt
	implementation("io.jsonwebtoken:jjwt-api:0.12.5")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.5")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.5")

	// redis
	implementation("org.springframework.boot:spring-boot-starter-data-redis")

	// bucket4j
	implementation("com.bucket4j:bucket4j-core:8.10.0")
	implementation("com.bucket4j:bucket4j-redis:8.10.0")

	// kafka
	implementation("org.springframework.kafka:spring-kafka")

	// mail
	implementation("org.springframework.boot:spring-boot-starter-mail")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.ai:spring-ai-bom:${property("springAiVersion")}")
	}
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

allOpen {
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.MappedSuperclass")
	annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

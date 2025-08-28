plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.5.4"
	id("io.spring.dependency-management") version "1.1.7"
	id("com.google.devtools.ksp") version "1.9.25-1.0.20"
	kotlin("plugin.jpa") version "1.9.25"
}

group = "com.zunza"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-data-redis")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("io.jsonwebtoken:jjwt:0.12.6") // JWT
	implementation("io.github.oshai:kotlin-logging-jvm:7.0.3") // Logging
	implementation("io.github.openfeign.querydsl:querydsl-jpa:6.11") //OpenFeign QueryDSL
	ksp("io.github.openfeign.querydsl:querydsl-ksp-codegen:6.11")
	annotationProcessor("io.github.openfeign.querydsl:querydsl-apt:6.11:jakarta")

	runtimeOnly("com.mysql:mysql-connector-j")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testImplementation("org.springframework.security:spring-security-test")
//	testImplementation("io.kotest.extensions:kotest-extensions-spring:1.3.0") // kotest
	testImplementation("io.kotest:kotest-runner-junit5-jvm:5.9.1") // kotest
	testImplementation("com.ninja-squad:springmockk:4.0.2") // Mockk

	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
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

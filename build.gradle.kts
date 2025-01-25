plugins {
	java
	id("org.springframework.boot") version "3.4.2"
	id("io.spring.dependency-management") version "1.1.7"
}

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

allprojects {
	group = "com.teamhide"
	version = "0.0.1-SNAPSHOT"

	repositories {
		mavenCentral()
	}
}

subprojects {
	apply(plugin = "java")
	apply(plugin = "org.springframework.boot")
	apply(plugin = "io.spring.dependency-management")

	dependencies {
		compileOnly("org.projectlombok:lombok")
		annotationProcessor("org.projectlombok:lombok")
		testImplementation("org.springframework.boot:spring-boot-starter-test")
		testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}
}
project(":template-core") {
	dependencies {
		implementation("org.springframework.boot:spring-boot-starter-web")
	}
}

project(":template-api") {
	dependencies {
		implementation(project(":template-core"))
		implementation(project(":template-domain"))
		implementation("org.springframework.boot:spring-boot-starter-web")
		implementation("org.springframework.boot:spring-boot-starter-validation")
	}
}

project(":template-application") {
	dependencies {
		implementation(project(":template-core"))
		implementation(project(":template-domain"))
	}
}

project(":template-domain") {
	dependencies {
		implementation(project(":template-core"))
		implementation("org.springframework.boot:spring-boot-starter-data-jpa")
		implementation("com.querydsl:querydsl-apt:5.0.0:jakarta")
		annotationProcessor("jakarta.annotation:jakarta.annotation-api")
		annotationProcessor("jakarta.persistence:jakarta.persistence-api")
	}
}

project(":template-infra") {
	dependencies {
		implementation(project(":template-domain"))
		implementation(project(":template-core"))
		implementation(project(":template-application"))
	}
}

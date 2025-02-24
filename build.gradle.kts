plugins {
	java
	id("org.springframework.boot") version "3.4.2"
	id("io.spring.dependency-management") version "1.1.7"
	id("jacoco")
	id("com.diffplug.spotless") version "6.20.0"
	id("java-test-fixtures")
}

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

tasks.bootJar {
	mainClass = "com.teamhide.template.api.Application"
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
	apply(plugin = "jacoco")
	apply(plugin = "com.diffplug.spotless")
	apply(plugin = "java-test-fixtures")

	dependencies {
		implementation("org.springframework.cloud:spring-cloud-starter-contract-stub-runner:4.2.0")
		compileOnly("org.projectlombok:lombok")
		annotationProcessor("org.projectlombok:lombok")
		testImplementation("org.springframework.boot:spring-boot-starter-test")
		testImplementation("org.springframework.cloud:spring-cloud-contract-wiremock")
		testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	}

	spotless {
		java {
			removeUnusedImports()
			replaceRegex(
				"Remove wildcard imports",
				"import\\s+[^\\*\\s]+\\*;(\\r\\n|\\r|\\n)",
				"$1"
			)
			googleJavaFormat()
			indentWithTabs(2)
			indentWithSpaces(4)
			trimTrailingWhitespace()
			endWithNewline()
		}
	}

	tasks.jacocoTestReport {
		dependsOn(tasks.test)
		reports {
			html.required.set(true)
			xml.required.set(true)
			csv.required.set(false)
		}
		finalizedBy(tasks.jacocoTestCoverageVerification)
		classDirectories.setFrom(
			files(classDirectories.files.map {
				fileTree(it) {
					exclude(
						"**/*Application*",
						"**/Q*Entity*",
					)
				}
			})
		)
	}

	tasks.jacocoTestCoverageVerification {
		val queryDslClasses = ('A'..'Z').map { "*.Q${it}*" }
		violationRules {
			rule {
				element = "CLASS"
				limit {
					counter = "LINE"
					value = "COVEREDRATIO"
					minimum = "0.80".toBigDecimal()
				}
				classDirectories.setFrom(sourceSets.main.get().output.asFileTree)
				excludes = listOf(
					"com.teamhide.template.Application",
				) + queryDslClasses
			}
		}
	}

	val preSpotless by tasks.registering {
		dependsOn("spotlessCheck", "spotlessApply")
		finalizedBy(tasks.jacocoTestReport)
	}

	val testAll by tasks.registering {
		dependsOn("spotlessCheck", "test", "jacocoTestReport", "jacocoTestCoverageVerification")
		tasks["test"].mustRunAfter(tasks["spotlessCheck"])
		tasks["jacocoTestReport"].mustRunAfter(tasks["test"])
		tasks["jacocoTestCoverageVerification"].mustRunAfter(tasks["jacocoTestReport"])
	}

	tasks.withType<Test> {
		systemProperties["spring.profiles.active"] = "test"
		useJUnitPlatform()
	}
}

project(":template-core") {
	tasks.bootJar {
		enabled = false
	}
	tasks.jar {
		enabled = true
	}

	dependencies {
		implementation("org.springframework.boot:spring-boot-starter-web")
		testFixturesImplementation("org.springframework.boot:spring-boot-starter-test")
		testFixturesImplementation("org.junit.jupiter:junit-jupiter-api")
	}
}

project(":template-api") {
	tasks.bootJar {
		enabled = true
	}
	tasks.jar {
		enabled = true
	}

	dependencies {
		implementation(project(":template-core"))
		implementation(project(":template-domain"))
		implementation(project(":template-application"))
		implementation(project(":template-infra:persistence"))
		implementation(project(":support:migration"))
		implementation(project(":template-infra:clients:pg"))
		implementation("org.springframework.boot:spring-boot-starter-web")
		implementation("org.springframework.boot:spring-boot-starter-validation")

		testImplementation(testFixtures(project(":template-core")))
		testImplementation("org.springframework.boot:spring-boot-starter-jdbc")
		testImplementation("org.springframework.boot:spring-boot-starter-data-jpa")
	}
}

project(":template-application") {
	tasks.bootJar {
		enabled = false
	}
	tasks.jar {
		enabled = true
	}

	dependencies {
		implementation("org.springframework.boot:spring-boot-starter-web")
		implementation("org.springframework.boot:spring-boot-starter-data-jpa")
		implementation(project(":template-core"))
		implementation(project(":template-domain"))

		testImplementation(testFixtures(project(":template-core")))
	}
}

project(":template-domain") {
	tasks.bootJar {
		enabled = false
	}
	tasks.jar {
		enabled = true
	}

	dependencies {
		implementation(project(":template-core"))
		implementation("org.springframework.boot:spring-boot-starter-data-jpa")
		implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
		annotationProcessor("com.querydsl:querydsl-apt:5.0.0:jakarta")
		annotationProcessor("jakarta.annotation:jakarta.annotation-api")
		annotationProcessor("jakarta.persistence:jakarta.persistence-api")
	}
}

project(":template-infra") {
	subprojects {
		tasks.bootJar {
			enabled = false
		}
		tasks.jar {
			enabled = true
		}

		dependencies {
			implementation(project(":template-domain"))
			implementation(project(":template-core"))
			implementation(project(":template-application"))
			testImplementation(testFixtures(project(":template-core")))
		}
	}
}

project(":template-infra:persistence") {
	dependencies {
		implementation("org.springframework.boot:spring-boot-starter-data-jpa")
		implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
		runtimeOnly("com.mysql:mysql-connector-j")
		annotationProcessor("com.querydsl:querydsl-apt:5.0.0:jakarta")
		annotationProcessor("jakarta.annotation:jakarta.annotation-api")
		annotationProcessor("jakarta.persistence:jakarta.persistence-api")
	}
}

project(":template-infra:clients") {
	subprojects {
		tasks.bootJar {
			enabled = false
		}
		tasks.jar {
			enabled = true
		}
	}

	dependencies {
		testFixturesImplementation("org.springframework.boot:spring-boot-starter-test")
		testFixturesImplementation("org.springframework.cloud:spring-cloud-starter-contract-stub-runner:4.2.0")
		testFixturesImplementation("org.springframework.cloud:spring-cloud-contract-wiremock")
	}
}

project(":template-infra:clients:pg") {
	dependencies {
		implementation(project(":template-infra:clients"))
		implementation("org.springframework.boot:spring-boot-starter-web")
		testImplementation(testFixtures(project(":template-infra:clients")))
	}
}

project(":support") {
	subprojects {
		tasks.bootJar {
			enabled = false
		}
		tasks.jar {
			enabled = true
		}
	}
}

project(":support:logging") {
	dependencies {
		implementation("io.sentry:sentry-logback:7.12.1")
	}
}

project(":support:migration") {
	dependencies {
		implementation("org.flywaydb:flyway-core")
		implementation("org.flywaydb:flyway-mysql")
	}
}

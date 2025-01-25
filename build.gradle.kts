plugins {
	java
	id("org.springframework.boot") version "3.4.2"
	id("io.spring.dependency-management") version "1.1.7"
	id("jacoco")
	id("com.diffplug.spotless") version "6.20.0"
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
	apply(plugin = "jacoco")
	apply(plugin = "com.diffplug.spotless")

	dependencies {
		compileOnly("org.projectlombok:lombok")
		annotationProcessor("org.projectlombok:lombok")
		testImplementation("org.springframework.boot:spring-boot-starter-test")
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

project(":support:logging") {
	dependencies {
		implementation("io.sentry:sentry-logback:7.12.1")
	}
}

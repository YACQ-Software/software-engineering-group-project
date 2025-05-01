plugins {
    java
    id("com.github.node-gradle.node") version "5.0.0"
    id("org.springframework.boot") version "3.4.3"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.yacq.software"
version = "0.0.1-SNAPSHOT"

java {
    toolchain { languageVersion.set(JavaLanguageVersion.of(17)) }
}

repositories {
    mavenCentral()
}

dependencies {
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.6")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	implementation("org.springframework.boot:spring-boot-starter-data-rest")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("edu.stanford.nlp:stanford-corenlp:4.5.4")
	implementation("edu.stanford.nlp:stanford-corenlp:4.5.4:models") {
		exclude(group = "edu.stanford.nlp", module = "stanford-corenlp") // Avoid duplicate classes
	}
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testImplementation("org.junit.jupiter:junit-jupiter:5.10.0") // or latest
	testImplementation("org.mockito:mockito-core:5.12.0") // or latest
	testImplementation("org.mockito:mockito-junit-jupiter:5.12.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

// --- Node plugin setup (no yarnEnabled) ---
node {
    version.set("18.16.0")
    download.set(true)
}

// --- Yarn install ---
val yarnInstall by tasks.registering(
    com.github.gradle.node.yarn.task.YarnTask::class
) {
    dependsOn(tasks.named("nodeSetup"))
    workingDir.set(layout.projectDirectory.dir("frontend"))
    args.set(listOf("install"))
}

// --- Yarn build ---
val yarnBuild by tasks.registering(
    com.github.gradle.node.yarn.task.YarnTask::class
) {
    dependsOn(yarnInstall)
    workingDir.set(layout.projectDirectory.dir("frontend"))
    args.set(listOf("build"))
}

// --- Copy React build into Spring’s static folder ---
tasks.register<Copy>("copyFrontend") {
    dependsOn(yarnBuild)
    from(layout.projectDirectory.dir("frontend").dir("build"))
    into(layout.projectDirectory.dir("src/main/resources/static"))
}

// Ensure the copy runs before packaging
tasks.named("processResources") {
    dependsOn("copyFrontend")
}

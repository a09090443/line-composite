import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension

plugins {
    base
    id("org.springframework.boot") version "2.3.1.RELEASE" apply false
    id("io.spring.dependency-management") version "1.0.9.RELEASE" apply false
    id("org.jetbrains.kotlin.plugin.allopen") version "1.3.72"

    kotlin("jvm") version "1.3.72" apply false
    kotlin("plugin.spring") version "1.3.72" apply false
    kotlin("plugin.jpa") version "1.3.72" apply false
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

extra["springCloudVersion"] = "Hoxton.RELEASE"
extra["loremVersion"] = "2.1"
extra["validationApiVersion"] = "2.0.1.Final"
extra["slf4jApiVersion"] = "1.7.30"
extra["swaggerVersion"] = "2.9.2"
extra["log4jdbcVersion"] = "1.2"
extra["graphqlJavaVersion"] = "5.2.4"
extra["graphqlVersion"] = "5.0.2"
extra["commonIoVersion"] = "2.6"
extra["fastjsonVersion"] = "1.2.68"
extra["quartzVersion"] = "2.3.2"
extra["okhttpVersion"] = "4.8.0"
extra["hateoasVersion"] = "1.1.0.RELEASE"
extra["hibernateValidatorVersion"] = "6.1.5.Final"

allprojects {
    group = "com.zipe"
//    version = "1.0-SNAPSHOT"
    repositories {
        jcenter()
        mavenCentral()
    }

}

subprojects {

    apply(plugin = "java")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")

    println("Enabling Kotlin Spring plugin in project ${project.name}...")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "org.jetbrains.kotlin.plugin.jpa")

    println("Enabling Spring Boot Dependency Management in project ${project.name}...")
    apply(plugin = "io.spring.dependency-management")
    the<DependencyManagementExtension>().apply {
        imports {
            mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
        }
    }

    tasks.withType<Jar> {
        enabled = true
    }

    tasks.withType<KotlinCompile>().configureEach {
        println("Configuring $name in project ${project.name}...")
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs = listOf("-Xjsr305=strict")
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    dependencies {
        "testImplementation"("org.springframework.boot:spring-boot-starter-test")
        "testImplementation"("org.junit.jupiter:junit-jupiter-api")
        "testRuntimeOnly"("org.junit.jupiter:junit-jupiter-engine")
    }
}

dependencies {
    // Make the root project archives configuration depend on every subproject
    subprojects.forEach {
        archives(it)
    }
}

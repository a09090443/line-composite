plugins {
    kotlin("jvm")
}

dependencies {
    api(kotlin("stdlib-jdk8"))
    api(kotlin("reflect"))

    api("com.fasterxml.jackson.module:jackson-module-kotlin")

    api("org.springframework.boot:spring-boot-starter-tomcat")
    api("org.springframework:spring-orm")
    api("org.springframework.boot:spring-boot-starter-web")
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    api("org.springframework.boot:spring-boot-starter-actuator")
    api("org.springframework.boot:spring-boot-starter-thymeleaf")
    api("org.springframework.boot:spring-boot-starter-data-redis")
    api("org.springframework.boot:spring-boot-devtools")
    api("org.springframework.boot:spring-boot-starter-logging")

    api("org.springframework.cloud:spring-cloud-starter-config")
    api("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    api("org.springframework.cloud:spring-cloud-starter-oauth2")

    api("org.springframework.security:spring-security-core")
    api("org.springframework.security:spring-security-config")
    api("org.springframework.security:spring-security-web")

    api("org.springframework.hateoas:spring-hateoas:1.1.0.RELEASE")

    api("com.graphql-java:graphql-spring-boot-starter:${property("graphqlVersion")}")
    api("com.graphql-java:graphiql-spring-boot-starter:${property("graphqlVersion")}")
    api("com.graphql-java:graphql-java-tools:${property("graphqlJavaVersion")}")
    api("com.alibaba:fastjson:${property("fastjsonVersion")}")

    api("com.zaxxer:HikariCP")
    api("org.hibernate:hibernate-ehcache")
    api("org.hibernate:hibernate-validator:6.1.5.Final")
    api("mysql:mysql-connector-java")
    api("com.thedeanda:lorem:${property("loremVersion")}")
    api("javax.validation:validation-api:${property("validationApiVersion")}")
    api("commons-io:commons-io:${property("commonIoVersion")}")
    api("com.googlecode.log4jdbc:log4jdbc:${property("log4jdbcVersion")}")

    api("org.slf4j:slf4j-api:${property("slf4jApiVersion")}")
}

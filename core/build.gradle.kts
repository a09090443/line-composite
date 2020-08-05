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
    api("org.springframework.boot:spring-boot-starter-quartz")

    api("org.springframework.cloud:spring-cloud-starter-config")
    api("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    api("org.springframework.cloud:spring-cloud-starter-oauth2")

    api("org.springframework.security:spring-security-core")
    api("org.springframework.security:spring-security-config")
    api("org.springframework.security:spring-security-web")

    api("org.springframework.hateoas:spring-hateoas:${property("hateoasVersion")}")

    api("com.graphql-java:graphql-spring-boot-starter:${property("graphqlVersion")}")
    api("com.graphql-java:graphiql-spring-boot-starter:${property("graphqlVersion")}")
    api("com.graphql-java:graphql-java-tools:${property("graphqlJavaVersion")}")
    api("com.alibaba:fastjson:${property("fastjsonVersion")}")

    api("com.zaxxer:HikariCP")
    api("org.hibernate:hibernate-ehcache")
    api("org.hibernate:hibernate-validator:${property("hibernateValidatorVersion")}")
    api("mysql:mysql-connector-java")
    api("com.thedeanda:lorem:${property("loremVersion")}")
    api("javax.validation:validation-api:${property("validationApiVersion")}")
    api("commons-io:commons-io:${property("commonIoVersion")}")
    api("com.googlecode.log4jdbc:log4jdbc:${property("log4jdbcVersion")}")
    api("com.squareup.okhttp3:okhttp:${property("okhttpVersion")}")
    api("com.google.guava:guava:${property("guavaVersion")}")
    api("org.projectlombok:lombok:${property("lombokVersion")}")
    api("commons-codec:commons-codec:${property("codecVersion")}")
    api("com.google.code.gson:gson:${property("gsonVersion")}")
    api("org.apache.commons:commons-collections4:${property("collections4Version")}")
    api("org.apache.commons:commons-lang3:${property("lang3Version")}")
    api("com.vladmihalcea:hibernate-types-52:${property("hibernateTypesVersion")}")
    api("org.apache.commons:commons-pool2:${property("pool2Version")}")
    api("redis.clients:jedis:${property("jedisVersion")}")
//    api("org.quartz-scheduler:quartz:${property("quartzVersion")}")

    api("org.slf4j:slf4j-api:${property("slf4jApiVersion")}")
}

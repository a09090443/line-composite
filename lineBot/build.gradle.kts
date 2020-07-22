plugins {
    kotlin("jvm")
}

dependencies {
    api(project(":core"))
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.linecorp.bot:line-bot-spring-boot:3.6.0")
    implementation("com.google.guava:guava:29.0-jre")
    implementation("org.projectlombok:lombok:1.18.12")
    implementation("commons-codec:commons-codec:1.14")
    implementation("com.google.code.gson:gson:2.8.6")
    implementation("com.squareup.okhttp3:okhttp:4.7.2")
    implementation("org.apache.commons:commons-collections4:4.4")
    implementation("org.apache.commons:commons-lang3:3.10")
    implementation("com.vladmihalcea:hibernate-types-52:2.9.12")
    implementation("org.apache.commons:commons-pool2:2.8.0")
    implementation("redis.clients:jedis:3.3.0")
    implementation("org.jsoup:jsoup:1.13.1")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

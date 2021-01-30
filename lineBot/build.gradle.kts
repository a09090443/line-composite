plugins {
    kotlin("jvm")
}

dependencies {
    api(project(":core"))
}

dependencies {
    implementation("com.linecorp.bot:line-bot-spring-boot:4.3.0")
    implementation("org.jsoup:jsoup:1.13.1")
    implementation("com.squareup.okhttp3:okhttp:4.8.0")
    implementation("io.ebean:ebean:12.3.9")
}

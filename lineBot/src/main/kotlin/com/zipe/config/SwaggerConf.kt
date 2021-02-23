package com.zipe.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket

@Configuration
class SwaggerConf {
    @Bean
    fun createRestApi(): Docket {
        return Docket(DocumentationType.OAS_30)
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.zipe.controller"))
            .paths(PathSelectors.any()) //paths(PathSelectors.regex("/.*"))
            .build()
    }

    private fun apiInfo(): ApiInfo {
        return ApiInfoBuilder()
            .title("Line bot api 說明")
            .description("相關說明")
            .build()
    }
}

package com.vibent.vibentback.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

/**
 * Configures the REST Documentation using Swagger.
 * HTML format can be found at http://127.0.0.1:8080/swagger-ui.html  for development.
 * JSON format for real documentation is at http://127.0.0.1:8080/v2/api-docs
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.vibent.vibentback"))
                .build()
                .apiInfo(apiInfo());
    }

    //TODO set all info
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Vibent API")
                .description("Vibent API allows developers from all over the world to implement our service with theirs.")
                .termsOfServiceUrl("Somewhere")
                .contact(new Contact("Vibent", "www.vibent.com", "vibent@vibent.com"))
                .version("1.0")
                .build();
    }
}
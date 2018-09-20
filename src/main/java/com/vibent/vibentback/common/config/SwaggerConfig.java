package com.vibent.vibentback.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Configures the REST Documentation using Swagger.
 * HTML format can be found at http://127.0.0.1:8080/swagger-ui.html  for development.
 * JSON format for real documentation is at http://127.0.0.1:8080/v2/api-docs
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final String SECURITY_SCHEMA_OAUTH2 = "oauth2";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.vibent.vibentback"))
                .build()
                .apiInfo(apiInfo())
                .securitySchemes(Collections.singletonList(oauth()));
    }

    //TODO set all info
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Vibent API")
                .description("Vibent API allows developers from all over the world to implement our service with theirs. " +
                        "Note that for most updates, if a value is null or not present it will not be taken into account. For " +
                        "Strings, if they are empty they will set the field to null.")
                .termsOfServiceUrl("NOT_IMPLEMENTED")
                .contact(new Contact("Vibent", "www.vibent.com", "vibent@vibent.com"))
                .version("1.0")
                .build();
    }

    @Bean
    public SecurityScheme oauth() {
        return new OAuthBuilder()
                .name(SECURITY_SCHEMA_OAUTH2)
                .grantTypes(grantTypes())
                .build();
    }

    @Bean
    public SecurityConfiguration securityInfo() {
        return new SecurityConfiguration(null, null, null, "swagger-ui", "", ApiKeyVehicle.HEADER, "", "");
    }


    private List<GrantType> grantTypes() {
        GrantType grantType = new ClientCredentialsGrant("auth/login/api");
        return Collections.singletonList(grantType);
    }
}